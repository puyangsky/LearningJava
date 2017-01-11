package com.test;

import sun.util.resources.cldr.aa.CalendarData_aa_DJ;

/**
 * Created by Administrator on 2016/6/10.
 */


public class Test1 {
    interface Increment {
        void increment();
    }
//    private static class Callback1 implements Increment {
//        private int i = 0;
//        @Override
//        public void increment() {
//            i++;
//            System.out.println(i);
//        }
//    }

    void test(Increment increment) {
        increment.increment();
    }
    public static void main(String[] args) {
        Test1 test1 = new Test1();

        test1.test(new Increment() {
            @Override
            public void increment() {

            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.getId();
        System.err.print("a");
        System.gc();
//        System.load
    }
}


