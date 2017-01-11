package com.test.thread.sycronized;

import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/6.
 */
public class EvenGenerator extends IntGenerator {
    private int currentValue = 0;

    @Override
    public int next() throws InterruptedException {
        ++currentValue;
        TimeUnit.MICROSECONDS.sleep(1000);
        Thread.yield();
        ++currentValue;
//        TimeUnit.MICROSECONDS.sleep(100);
        return currentValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
