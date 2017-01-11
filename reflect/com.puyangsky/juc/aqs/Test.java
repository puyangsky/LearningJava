package juc.aqs;

import net.sf.cglib.core.ReflectUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by puyangsky on 2016/12/30.
 */
public class Test  {
    private static int index = 0;
    private static int index1 = 0;
    private static AtomicInteger integer = new AtomicInteger(0);
    private final static Mutex lock = new Mutex();
    static class T implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                index++;
            }finally {
                lock.unlock();
            }

            integer.incrementAndGet();

            index1++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<100000;i++) {

            T t = new T();
            Thread thread = new Thread(t);
            thread.start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(">>>>Atomic: " + integer);
        System.out.println(">>>>Lock: " + index);
        System.out.println(">>>>None Lock: " + index1);

    }
}

