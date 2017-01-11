package com.test.thread;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/6.
 */
public class YeildTest {
    public static int v = 0;
    static class Task implements Runnable {
        @Override
        public void run() {
            v++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

//        for(int i=0;i<100;i++) {
//            Thread t = new Thread(new Task());
//            t.start();
//        }
//        System.out.println(v);

        Sleep
                a = new Sleep("a", 10000),
                b = new Sleep("b", 10000);
        Joiner
                c = new Joiner("c", a),
                d = new Joiner("d", b);
        a.interrupt();
    }
}

class Sleep extends Thread {
    private int duration;
    public Sleep(String name, int time) {
        super(name);
        duration = time;
        start();
    }

    public void run () {
        System.out.println(new Date().getTime());
        try {
            System.out.println(getName() + " started");
                sleep(duration);
        }catch (InterruptedException e) {
            System.out.println(getName() + " is interrupted");
            return;
        }
        System.out.println(getName() + " has awakened");
        System.out.println(new Date().getTime());
    }
}

class Joiner extends Thread {
    private Sleep sleep;
    public Joiner(String name, Sleep sleep) {
        super(name);
        this.sleep = sleep;
        start();
    }
    public void run() {
        try {
            System.out.println(getName() + " started");
            sleep.join();
        }catch (InterruptedException e) {
            System.out.println(getName() + " Interrupted");
        }

        System.out.println(getName() + " completed");
    }
}