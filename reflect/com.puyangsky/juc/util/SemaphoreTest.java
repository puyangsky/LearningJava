package juc.util;

import java.util.concurrent.Semaphore;

/**
 * Created by puyangsky on 2017/1/7.
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0;i < 10;i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private Semaphore semaphore;
        private int num;

        public Worker(int num, Semaphore semaphore) {
            this.semaphore = semaphore;
            this.num = num;
        }

        public void run() {
            try {

                semaphore.acquire();
                System.out.println("工人" + (this.num + 1) + "占用一个机器在生产...");
                Thread.sleep(2000);

                System.out.println("工人" + (this.num + 1) + "释放一个机器...");
                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
