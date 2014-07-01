Simple Disruptor Micro-Benchmark
===============================
By wrapping disruptor in an ExecutorService to facilitate micro-benchmarking against Java standard library.

The benchmark details can be found in [ExecutorServiceBenchmark](https://github.com/freddfy/DisruptorBenchmark/blob/master/src/main/java/fred/utils/ExecutorServiceBenchmark.java).

### Result run on OS X, 2.4GHz Intel Core i5

[Executors.newSingleThreadExecutor()](https://github.com/freddfy/DisruptorBenchmark/blob/master/src/main/java/fred/utils/ExecutorServiceSingleBenchmarkMain.java): 1438 evt/ms
[ExecutorServiceConcLinQ](https://github.com/freddfy/DisruptorBenchmark/blob/master/src/main/java/fred/utils/ExecutorServiceConcLinQBenchmarkMain.java) (by ConcurrentLinkedQueue): 2352 evt/ms
[ExecutorServiceDisruptor](https://github.com/freddfy/DisruptorBenchmark/blob/master/src/main/java/fred/utils/ExecutorServiceDisruptorBenchmarkMain.java) (by Disruptor): 9708 evt/ms
