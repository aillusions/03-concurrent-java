package com.zalizniak;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppTest {

    @Test
    public void should() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            System.out.println("I'm from thread: " + Thread.currentThread().getName());
        });
        System.out.println("I'm from thread: " + Thread.currentThread().getName());
    }

}
