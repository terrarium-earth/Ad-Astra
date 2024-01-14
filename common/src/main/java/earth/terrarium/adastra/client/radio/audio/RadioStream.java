package earth.terrarium.adastra.client.radio.audio;

import earth.terrarium.adastra.common.utils.BufferUtils;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RadioStream extends InputStream {

    private static final int SIZE = 8;
    private static final int KB = 1024;
    private static final int BUFFER_SIZE = SIZE * KB;

    private final Deque<ByteBuffer> cache = new LinkedList<>(); //Doubly linked list because we only poll from the front and add to the back
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final AtomicBoolean waiting = new AtomicBoolean(false);
    private final CompletableFuture<?> getter;

    private ByteBuffer data;

    public RadioStream(IoSupplier<InputStream> inputStream) {
        AtomicBoolean setup = new AtomicBoolean(true);
        this.getter = CompletableFuture.runAsync(() -> {
            try (InputStream stream = inputStream.get()) {
                while (!this.closed.get()) {
                    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    IOUtils.read(Channels.newChannel(stream), buffer);
                    buffer.flip();

                    if (this.closed.get()) break;
                    if (buffer.hasRemaining()) {

                        wait(this.waiting);

                        this.cache.add(buffer);
                        if (this.cache.size() > SIZE) {
                            this.waiting.set(true);
                        }
                    }

                    if (this.cache.size() >= SIZE) {
                        setup.set(false);
                    }
                }
            } catch (ClosedException ignored) {
                //We don't care if it's closed.
            } catch (IOException e) {
                setup.set(false);
                throw new CompletionException(e);
            }
            setup.set(false);
        }, RadioHandler.DOWNLOAD_EXECUTOR);

        try {
            wait(setup);
        } catch (ClosedException ignored) {
            //We don't care if it's closed.
        }
    }

    private boolean next() {
        if (this.waiting.get() && this.cache.size() < SIZE) {
            this.waiting.set(false);
        }
        this.data = this.cache.poll();
        return this.data == null || this.cache.isEmpty();
    }

    @Override
    public int read() {
        return isNotReadable() ? -1 : this.data.get();
    }

    @Override
    public int read(byte @NotNull [] bytes, int offset, int length) {
        if (isNotReadable()) return -1;
        int count = 0;
        while (count < length && !isNotReadable()) {
            count += BufferUtils.read(this.data, bytes, offset + count, length, count);
        }
        return count;
    }

    @Override
    public long skip(long length) {
        if (isNotReadable()) return 0;
        int count = 0;
        while (count < length && !isNotReadable()) {
            count += BufferUtils.skip(this.data, (int) length, count);
        }
        return count;
    }

    @Override
    public void close() {
        this.closed.set(true);
        this.getter.join();
    }

    public boolean isNotReadable() {
        return (this.data == null || !this.data.hasRemaining()) && this.next();
    }

    private void wait(AtomicBoolean waiting) {
        while (!this.closed.get() && waiting.get()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }
        if (!this.closed.get()) return;
        throw new ClosedException();
    }

    private static class ClosedException extends RuntimeException {
        private ClosedException() {
            super("Stream closed");
        }
    }
}
