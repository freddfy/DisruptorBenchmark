package fred.utils;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.List;
import java.util.concurrent.*;

/**
 * Author:  Fred Deng
 */
public class ExecutorServiceDisruptor extends AbstractExecutorService {

    public static final WaitStrategy BUSY_SPIN_STRATEGY = new BusySpinWaitStrategy();
    public static final WaitStrategy YIELD_WAIT_STRATEGY = new YieldingWaitStrategy();
    public static final WaitStrategy BLOCK_WAIT_STRATEGY = new BlockingWaitStrategy();

    private static final EventTranslatorOneArg<RunnableHolder, Runnable> TRANSLATOR =
            new EventTranslatorOneArg<RunnableHolder, Runnable>() {
                @Override
                public void translateTo(RunnableHolder event, long sequence, Runnable runnable) {
                    event.setRunnable(runnable);
                }
            };

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final CountDownLatch terminateLatch = new CountDownLatch(1);
    private final Disruptor<RunnableHolder> disruptor;
    private final RingBuffer<RunnableHolder> buffer;

    public ExecutorServiceDisruptor(int bufferSize, WaitStrategy strategy) {
        this.disruptor = new Disruptor<RunnableHolder>(
                new EventFactory<RunnableHolder>() {
                    @Override
                    public RunnableHolder newInstance() {
                        return new RunnableHolder();
                    }
                },
                bufferSize,
                executor,
                ProducerType.MULTI,
                strategy

        );
        disruptor.handleEventsWith(new RunnableHandler());
        disruptor.start();

        this.buffer = disruptor.getRingBuffer();

    }

    @Override
    public void shutdown() {
        disruptor.shutdown();  //disruptor shut down is blocking
        executor.shutdown();
        terminateLatch.countDown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return terminateLatch.getCount() == 0 && executor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit) && terminateLatch.await(timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        buffer.publishEvent(TRANSLATOR, command);
    }
}
