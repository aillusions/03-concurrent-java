package com.zalizniak;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppTest {

    @Test
    public void should1() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            System.out.println("I'm from thread: " + Thread.currentThread().getName());
        });
        System.out.println("I'm from thread: " + Thread.currentThread().getName());

        executorService.shutdown();
    }

    @Test
    public void should2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(8);
        Runnable task = () -> {
            System.out.println("I'm from thread: " + Thread.currentThread().getName());
            countDownLatch.countDown();
        };

        for (int i = 0; i < 8; i++) {
            executorService.submit(task);
        }

        countDownLatch.await();

        executorService.shutdown();
    }

}
