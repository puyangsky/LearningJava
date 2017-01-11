package com.test.thread.waitNotify;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ProductorComsumer {
    private static volatile int COUNT = 10;
    private static Object lock = new Object();
    static class Producer implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                while (true) {
                    if (COUNT < 10) {
                        System.out.println(Thread.currentThread().getName() + " Producer is producing... COUNT = " + COUNT);
                        COUNT++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " Producer is notifying...COUNT = " + COUNT);
                    lock.notifyAll();
                }
            }
        }
    }
    static class Consumer implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                if (COUNT > 0) {
                    System.out.println(Thread.currentThread().getName() + " Consumer is consuming...COUNT = " + COUNT);
                    COUNT--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    System.out.println(Thread.currentThread().getName() + " Consumer is waiting...COUNT = " + COUNT);
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++) {
            new Thread(new Consumer(), "Consumer-" + i).start();
        }
        new Thread(new Producer(), "Producer").start();
    }
}
