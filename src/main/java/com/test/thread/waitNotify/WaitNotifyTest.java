package com.test.thread.waitNotify;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/9.
 */
public class WaitNotifyTest {
    private static final Object lock = new Object();
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Wait(), "WaitThread").start();
        Thread.sleep(2000);
        new Thread(new Notify(), "NotifyThread").start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(Thread.currentThread() + " flag is true at" +
                       new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + " flag is false at" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()) );
            }
        }
    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " get the lock at" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.notifyAll();
                flag = false;
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread() + " get the lock again at" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
}
