package com.zalizniak.cjfuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class CompletableFutureTest {

    public CompletableFuture<String> sayHello(String arg) {
        return CompletableFuture.completedFuture("hello from future " + arg);
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
