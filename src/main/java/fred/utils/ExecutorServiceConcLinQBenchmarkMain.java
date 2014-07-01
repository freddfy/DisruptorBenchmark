package fred.utils;

/**
 * Author:  Fred Deng
 */
public class ExecutorServiceConcLinQBenchmarkMain {
    public static void main(String[] args) throws Exception{
        new ExecutorServiceBenchmark(new ExecutorServiceConcLinQ(ExecutorServiceConcLinQ.YIELD_WAIT_STRATEGY))
                .benchmark();
    }
}
