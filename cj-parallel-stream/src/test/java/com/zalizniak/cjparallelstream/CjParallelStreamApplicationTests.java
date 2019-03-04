package com.zalizniak.cjparallelstream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CjParallelStreamApplicationTests {

    @Test
    public void contextLoads() throws ExecutionException, InterruptedException, IOException {

        long processingTime8Threads = processAndGetTime(8);
        System.out.println("Processed by: " + 8 + " threads  in " + processingTime8Threads + " ms.");
    }

    public long processAndGetTime(int threads) throws ExecutionException, InterruptedException {
        AtomicInteger counter = new AtomicInteger();

        IntStream basicStream = IntStream.range(0, 100_000_000);

        IntStream parallelStream = basicStream.parallel();
        ForkJoinPool customThreadPool = new ForkJoinPool(threads);

        long start = System.currentTimeMillis();
        customThreadPool.submit(
                () -> parallelStream.forEach(idx -> {

                    int curr = counter.incrementAndGet();

                    if (curr % 100_000 == 0) {
                        System.out.println(Thread.currentThread().getName() + " found: " + curr + " with indx: " + idx);
                    }

                })).get();

        return (System.currentTimeMillis() - start);
    }
}
