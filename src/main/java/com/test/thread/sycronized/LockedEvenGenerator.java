package com.test.thread.sycronized;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/9/6.
 */
public class LockedEvenGenerator extends IntGenerator {
    private int currentValue = 0;
    Lock lock = new ReentrantLock();
    @Override
    public int next() throws InterruptedException {
        lock.lock();
        try {
            ++currentValue;
            Thread.yield();
            ++currentValue;
            return currentValue;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new LockedEvenGenerator());
    }
}
