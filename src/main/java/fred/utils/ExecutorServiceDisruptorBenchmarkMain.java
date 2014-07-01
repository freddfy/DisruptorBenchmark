package fred.utils;

import java.util.concurrent.ExecutorService;

/**
 * Author:  Fred Deng
 */
public class ExecutorServiceDisruptorBenchmarkMain {


    public static void main(String[] args) throws Exception{
        new ExecutorServiceBenchmark(new ExecutorServiceDisruptor(1024, ExecutorServiceDisruptor.YIELD_WAIT_STRATEGY))
                .benchmark();

    }


}
