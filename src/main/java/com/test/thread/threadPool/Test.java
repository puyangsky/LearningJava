package com.test.thread.threadPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2016/9/10.
 */
public class Test {
    static CountDownLatch latch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        ThreadPool<Task> pool = new DefaultThreadPool<Task>(5);
        for (int i=0;i<10;i++) {
            pool.execute(new Task());
        }
        latch.await();
        pool.shutdown();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running...");
            latch.countDown();
        }
    }
}
