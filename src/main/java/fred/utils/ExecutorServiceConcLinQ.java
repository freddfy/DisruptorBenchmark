package fred.utils;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Author:  Fred Deng
 */
public class ExecutorServiceConcLinQ extends AbstractExecutorService {

    private final Queue<Runnable> queue = new ConcurrentLinkedDeque<Runnable>();
    private final CountDownLatch terminated = new CountDownLatch(1);
    private final Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        Runnable runnable = queue.poll();
                        if (runnable == null) {
                            waitStrategy.doWait();
                        } else if (runnable == POISON) {
                            break;
                        } else {
                            runnable.run();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace(); //TODO: proper logging
                    }
                }
            }finally{
                terminated.countDown();
            }

        }
    };
    private final WaitStrategy waitStrategy;
    private volatile boolean isShuttingDown;

    public ExecutorServiceConcLinQ(WaitStrategy waitStrategy) {
        this.waitStrategy = waitStrategy;
        thread.start();
    }


    @Override
    public void shutdown() {
        execute(POISON);
        this.isShuttingDown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShutdown() {
        return isShuttingDown;
    }

    @Override
    public boolean isTerminated() {
        return terminated.getCount() == 0;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return terminated.await(timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        queue.add(command);
    }

    private static final Runnable POISON = new Runnable() {
        @Override
        public void run() {

        }
    };

    public static interface WaitStrategy{
        void doWait();
    }

    public static WaitStrategy YIELD_WAIT_STRATEGY = new WaitStrategy() {
        @Override
        public void doWait() {
            Thread.yield();
        }
    };

    public static WaitStrategy BUSY_SPIN_STRATEGY = new WaitStrategy() {
        @Override
        public void doWait() {
            //wait no time
        }
    };

}
