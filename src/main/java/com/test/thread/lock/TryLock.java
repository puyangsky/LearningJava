package com.test.thread.lock;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/9/7.
 */
public class TryLock {
    private ReentrantLock lock = new ReentrantLock();
    public void untimed() {
        boolean captured = false;

        try {
            captured = lock.tryLock();
            System.out.println("trylock()" + captured);
        }finally {
            if (captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("trylock TimeUnit.SECONDS " + captured);
        }finally {
            if (captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final TryLock tryLock = new TryLock();
        tryLock.untimed();
        tryLock.timed();

        new Thread() {
            {
                setDaemon(true);
            }
            public void run() {
                tryLock.lock.lock();
                System.out.println("acquired");
            }
        }.start();

        Thread.yield();
        Thread.sleep(1000);
        tryLock.untimed();
        tryLock.timed();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }, 1000);
    }
}
