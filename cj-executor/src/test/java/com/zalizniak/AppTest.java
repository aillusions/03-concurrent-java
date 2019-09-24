package com.zalizniak;

import org.junit.Test;

import java.util.concurrent.*;


public class AppTest {

    @Test
    public void shouldExecute1Task() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            System.out.println("I'm from thread: " + Thread.currentThread().getName());
        });
        System.out.println("I'm from thread: " + Thread.currentThread().getName());

        executorService.shutdown();
    }

    @Test
    public void shouldExecuteMultipleTasks() throws InterruptedException {
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

    @Test
    public void shouldEnqueueTooMany() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 10_000_000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(100_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void shouldEnqueueCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<String> future = executorService.submit(() -> {
            return "123";
        });

        System.out.println("" + future.get());
    }
}
