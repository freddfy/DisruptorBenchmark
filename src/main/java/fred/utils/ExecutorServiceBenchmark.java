package fred.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Author:  Fred Deng
 */
public class ExecutorServiceBenchmark {
    private static final int WARMUP = 100000;
    private static final int TEST = 1000000;
    private static final int TERMINATE_TIME = 120;
    private final ExecutorService es;
    private final BenchmarkUtil util = new BenchmarkUtil();

    public ExecutorServiceBenchmark(ExecutorService es) {
        this.es = es;
    }

    public void benchmark() throws Exception {
        for (int i = 0; i < WARMUP; i++) {
            es.submit(util.getRunnable());
        }

        long start = util.now();

        for (int i = 0; i < TEST; i++) {
            es.submit(util.getRunnable());
        }

        es.shutdown();

        if(!es.awaitTermination(TERMINATE_TIME, TimeUnit.SECONDS)) {
            throw new TimeoutException("Timeout waiting for shut down");
        }

        long end = util.now();

        System.out.println("Throughput evt/ms: " + TEST/(end - start));
    }
}
