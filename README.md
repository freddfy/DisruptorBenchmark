Simple Disruptor Micro-Benchmark
===============================
By wrapping disruptor in an ExecutorService to facilitate micro-benchmarking against Java standard library.

The benchmark details can be found in ExecutorServiceBenchmark.

The following result is run on Macbook Pro, OS X with 2.4GHz Intel Core i5

### Executors.newSingleThreadExecutor() : 1438 evt/ms
### ExecutorServiceConcLinQ (by Java ConcurrentLinkedQueue): 2352 evt/ms
### ExecutorServiceDisruptor (by Disruptor) :  9708 evt/ms
