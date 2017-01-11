package com.test.thread.sycronized;

/**
 * Created by Administrator on 2016/9/7.
 */
public class SyncTest {
    private Object sync = new Object();
    public synchronized void f() {
//        synchronized (sync) {
            for (int i=0;i<5;i++) {
                System.out.println(i + "-f()");
                Thread.yield();
            }
//        }
    }

    public void g() {
        synchronized (sync) {
            for (int i=0;i<5;i++) {
                System.out.println(i + "-g()");
                Thread.yield();
            }
        }
    }

    public synchronized void p() {
        for (int i=0;i<5;i++) {
            System.out.println(i + "-p()");
            Thread.yield();
        }
    }
    public static void main(String[] args) {
        final SyncTest test = new SyncTest();
        new Thread() {
            public void run() {
                test.f();
            }
        }.start();
//        new Thread() {
//            public void run() {
//                test.p();
//            }
//        }.start();
        test.g();
    }
}
