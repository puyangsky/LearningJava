package com.test.thread.sycronized;

/**
 * Created by Administrator on 2016/9/6.
 */
public abstract class IntGenerator {
    private volatile boolean canceled = false;
    public abstract int next() throws InterruptedException;
    public void cancel(){
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
