package earth.terrarium.adastra.client.utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Debouncer {

    private static final ScheduledExecutorService SCHEDULER = new ScheduledThreadPoolExecutor(4);

    private ScheduledFuture<?> future;

    public void debounce(Runnable runnable, long delay) {
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        future = SCHEDULER.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }
}
