package earth.terrarium.adastra.client.radio.audio;

import earth.terrarium.adastra.common.utils.BufferUtils;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.*;
import net.minecraft.client.sounds.AudioStream;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.Arrays;

public class Mp3AudioStream extends InputStream implements AudioStream {

    private final ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES * Obuffer.OBUFFERSIZE).order(ByteOrder.LITTLE_ENDIAN);
    private final Decoder decoder = new Decoder();

    private final InputStream input;
    private final Bitstream bitstream;

    private SampleBuffer output;
    private AudioFormat format;

    public Mp3AudioStream(InputStream input) throws IOException {
        this.input = input;
        this.bitstream = new Bitstream(input);
        if (this.readFrame()) {
            throw new IOException("Could not find header.");
        }
    }

    /**
     * This is a copy of {@link Converter#convert(InputStream, String, Converter.ProgressListener, Decoder.Params)}
     * but using a buffer to periodically read the stream instead of reading the whole stream at once.
     *
     * @return true if the end of the stream was reached, false otherwise.
     */
    private boolean readFrame() throws IOException {
        this.buffer.clear();
        try {
            Header header = bitstream.readFrame();
            if (header == null) return true;

            if (this.output == null) {
                this.output = new SampleBuffer(header.sample_frequency(), header.mode() == Header.SINGLE_CHANNEL ? 1 : 2);
                this.decoder.setOutputBuffer(this.output);
                this.format = new AudioFormat(header.frequency(), 16, this.output.getChannelCount(), true, false);
            }

            if (decoder.decodeFrame(header, bitstream) != this.output) {
                throw new IllegalStateException("Output buffers are different.");
            }
        } catch (Exception e) {
            throw new IOException(e);
        }

        for (short value : this.output.getBuffer()) {
            this.buffer.putShort(value);
        }
        this.buffer.flip();

        this.bitstream.closeFrame();
        return false;
    }

    @Override
    public int read() throws IOException {
        return !this.buffer.hasRemaining() && this.readFrame() ? -1 : this.buffer.get();
    }

    @Override
    public int read(byte @NotNull [] bytes, int offset, int length) throws IOException {
        int count = 0;

        while (count < length) {
            if (!this.buffer.hasRemaining() && this.readFrame()) return -1;
            count += BufferUtils.read(this.buffer, bytes, offset + count, length, count);
        }
        return count;
    }

    @Override
    public int available() throws IOException {
        return this.input.available();
    }

    @Override
    public void close() throws IOException {
        try {
            this.bitstream.close();
        } catch (BitstreamException e) {
            throw new IOException(e);
        }
    }

    @Override
    public @NotNull AudioFormat getFormat() {
        return this.format;
    }

    /**
     * This is taken from Paulscode Sound System CodecIBXM and modified.
     */
    @Override
    public @NotNull ByteBuffer read(int size) throws IOException {
        byte[] data = new byte[size];
        int count = IOUtils.read(this, data, 0, size);

        ByteBuffer dest = ByteBuffer.allocateDirect(count);
        dest.order(ByteOrder.nativeOrder());
        ByteBuffer src = ByteBuffer.wrap(size != count ? Arrays.copyOf(data, count) : data);
        src.order(ByteOrder.LITTLE_ENDIAN);

        ShortBuffer destShort = dest.asShortBuffer();
        ShortBuffer srcShort = src.asShortBuffer();
        while (srcShort.hasRemaining()) {
            destShort.put(srcShort.get());
        }

        dest.rewind();
        return dest;
    }
}
