package com.test.thread;

import sun.misc.Lock;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/20.
 */
public class ThreadTest1 {
    private static int value = 0;
    private static AtomicInteger value1 = new AtomicInteger(0);
    private static int value2 = 0;
    private static Lock lock = new Lock();
    public static  void add() {
        value++;
        value1.incrementAndGet();
    }
    public static void add1() throws InterruptedException {
        lock.lock();
        value2++;
        lock.unlock();
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i=0;i<10000;i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    add();
                    try {
                        add1();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.shutdown();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(value);
        System.out.println(value1);
        System.out.println(value2);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        int temp = atomicInteger.getAndAdd(1);
        System.out.println("temp==>" + temp);
        System.out.println("now==>" + atomicInteger);
        temp = atomicInteger.incrementAndGet();
        System.out.println("temp==>" + temp);
    }
}

