package com.test.thread.join;

/**
 * Created by Administrator on 2016/9/9.
 */
public class JoinTest {
    static class A implements Runnable {
        private Thread t;
        public A(Thread t) {
            this.t = t;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running...");
            System.out.println(t.getName() + " is joining...");
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is running again...");
        }
    }

    static class B implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread tb = new Thread(new B(), "B");
        Thread ta = new Thread(new A(tb), "A");
        ta.start();
        tb.start();
    }
}
