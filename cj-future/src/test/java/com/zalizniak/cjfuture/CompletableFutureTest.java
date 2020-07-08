package com.zalizniak.cjfuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Test;

public class CompletableFutureTest {

    @Test
    public void testSupply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future
            = CompletableFuture.supplyAsync(() -> "Hello");

        Assert.assertEquals("Hello", future.get());
    }

    public CompletableFuture<String> sayHello(String arg) {
        // return CompletableFuture.completedFuture("hello from future " + arg);

        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hello from future " + arg;
            }
        });
    }

    @Test
    public void testHappy() throws ExecutionException, InterruptedException {

        CompletableFuture<String> say1 = sayHello("11111");
        CompletableFuture<String> say2 = sayHello("22222");

        CompletableFuture.allOf(say1, say2).join();

        Assert.assertEquals("hello from future 11111", say1.get());
        Assert.assertEquals("hello from future 22222", say2.get());
    }

}
