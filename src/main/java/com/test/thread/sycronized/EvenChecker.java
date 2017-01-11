package com.test.thread.sycronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/9/6.
 */
public class EvenChecker implements Runnable{

    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator g, int ident) {
        id = ident;
        generator = g;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = 0;
            try {
                val = generator.next();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (val % 2 != 0) {
                System.out.println("ID: " + id + "\t" + val + " is not even");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator g, int count) {
        System.out.println("test....");
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<count;i++) {
            executorService.submit(new EvenChecker(g, i));
        }
        executorService.shutdown();
    }

    public static void test(IntGenerator g) {
        test(g, 10);
    }
}
