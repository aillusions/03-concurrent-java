package com.zalizniak.cjparallelstream;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CjParallelStreamApplicationTests {

    /**
     * By: 8 threads: 1443 ms.
     * By: 4 threads: 1639 ms.
     * By: 1 threads: 4260 ms.
     */
    @Test
    public void contextLoads() throws ExecutionException, InterruptedException {

        log.info("By: " + 8 + " threads: " + processAndGetTime(8) + " ms.");
        log.info("By: " + 4 + " threads: " + processAndGetTime(4) + " ms.");
        log.info("By: " + 1 + " threads: " + processAndGetTime(1) + " ms.");
    }

    public long processAndGetTime(int threads) throws ExecutionException, InterruptedException {

        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                2_000_000,
                0.00000000001);

        AtomicInteger counter = new AtomicInteger();

        IntStream basicStream = IntStream.range(0, 2_000_000);

        IntStream parallelStream = basicStream.parallel();
        ForkJoinPool customThreadPool = new ForkJoinPool(threads);

        long start = System.currentTimeMillis();
        customThreadPool.submit(
                () -> parallelStream.forEach(idx -> {

                    filter.put(idx);

                    int curr = counter.incrementAndGet();

                    if (curr % 100_000 == 0) {
                        log.debug(Thread.currentThread().getName() + " found: " + curr + " with indx: " + idx);
                    }

                })).get();

        return (System.currentTimeMillis() - start);
    }
}
