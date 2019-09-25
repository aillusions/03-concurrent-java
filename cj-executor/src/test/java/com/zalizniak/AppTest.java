package com.zalizniak;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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

        executorService.shutdown();
    }

    @Test
    public void shouldEnqueueCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<String> future = executorService.submit(() -> {
            Thread.sleep(100);
            return "123";
        });

        future.cancel(true);
        System.out.println("" + future.get());

        executorService.shutdown();
    }

    @Test
    public void raceCondition() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Counter counter = new Counter();

        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> counter.increment());
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final count is : " + counter.getCount());
    }

    @Test
    public void forkJoin() throws InterruptedException {
        Node node1 = new Node(1L);
        Node node2 = new Node(2L);

        Node root = new Node(3L, node1, node2);

        System.out.println("Tree sum: " + /*new ForkJoinPool()*/ForkJoinPool.commonPool().invoke(new ValueSumCounter(root)));

        Thread.sleep(1000);
    }

    private static class Counter {
        int count = 0;

        public void increment() {
            count = count + 1;
        }

        public int getCount() {
            return count;
        }
    }

    public class ValueSumCounter extends RecursiveTask<Long> {
        private final Node node;

        public ValueSumCounter(Node node) {
            this.node = node;
        }

        @Override
        protected Long compute() {
            long sum = node.getValue();

            List<ValueSumCounter> subTasks = new LinkedList<>();

            for (Node child : node.getChildren()) {
                ValueSumCounter task = new ValueSumCounter(child);
                task.fork(); // запустим асинхронно
                System.out.println("Forked");
                subTasks.add(task);
            }

            for (ValueSumCounter task : subTasks) {
                sum += task.join(); // дождёмся выполнения задачи и прибавим результат
                System.out.println("Joined");
            }

            return sum;
        }
    }

    public static class Node {

        private List<Node> children;
        private Long value;

        public Node(Long value, Node... children) {
            this.children = Arrays.asList(children);
            this.value = value;
        }

        public Collection<Node> getChildren() {
            return children;
        }

        public long getValue() {
            return value;
        }
    }
}
