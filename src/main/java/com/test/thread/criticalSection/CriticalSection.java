package com.test.thread.criticalSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/9/7.
 */
public class CriticalSection {
    static void test(PairManager pm1, PairManager pm2) {
        ExecutorService executor = Executors.newCachedThreadPool();
        PairManipulator
                pma1 = new PairManipulator(pm1),
                pma2 = new PairManipulator(pm2);
        PairChecker
                pc1 = new PairChecker(pm1),
                pc2 = new PairChecker(pm2);
        executor.execute(pma1);
        executor.execute(pma2);
        executor.execute(pc1);
        executor.execute(pc2);
        try {
            TimeUnit.MICROSECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pm1:" + pma1 + "\npm2:" + pma2);
        System.exit(0);
    }

    public static void main(String[] args) {
//        PairManager pm1 = new PairManager1(),
//                pm2 = new PairManager1();
//        test(pm1, pm2);
        Test t = new Test();
        System.out.println(t.toString());
    }
}

class Test {
    public Test() {}
//    public String toString() {
//        return "Test";
//    }
}

class Pair {
    private int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Pair() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void incrementX() {
        x++;
    }

    public void incrementY() {
        y++;
    }
    public String toString() {
        return "x:" + getX() + "\ty:" + getY();
    }
    public class PairValueNotEqualException extends RuntimeException {
        public PairValueNotEqualException() {
            super("Pairs value not equal " + Pair.this);
        }
    }

    public void checkState() {
        if (x != y) {
            throw new PairValueNotEqualException();
        }
    }
}

abstract class PairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storager = Collections.synchronizedList(new ArrayList<Pair>());
    public synchronized Pair getPair() {
        return new Pair(p.getX(), p.getY());
    }
    protected void store(Pair p) {
        storager.add(p);
        try {
            TimeUnit.MICROSECONDS.sleep(50);
        }catch (InterruptedException e) {

        }
    }

    public abstract void increment();
}

class PairManager1 extends PairManager{

    @Override
    public void increment() {
        Pair temp;
        synchronized (this) {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}

class PairManipulator implements Runnable {
    private PairManager pm;
    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }
    @Override
    public void run() {
        while (true) {
            pm.increment();
        }
    }
    public String toString() {
        return "Pair:" + pm.getPair() + "checkCounter=" + pm.checkCounter.get();
    }
}
class PairChecker implements Runnable {
    private PairManager pm;
    public PairChecker(PairManager pm) {
        this.pm = pm;
    }
    @Override
    public void run() {
        while (true) {
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}