package fred.utils;

import java.util.concurrent.Executors;

/**
 * Author:  Fred Deng
 */
public class ExecutorServiceSingleBenchmarkMain {
    public static void main(String[] args) throws Exception{
        new ExecutorServiceBenchmark(Executors.newSingleThreadExecutor())
                .benchmark();
    }
}
