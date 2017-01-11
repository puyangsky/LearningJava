package juc.blockingqueue;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by puyangsky on 2017/1/6.
 */
public class QueueTest {

    static class Producer implements Runnable {
        private BlockingQueue<Integer> queue;
        Random random = new Random();
        public Producer(BlockingQueue<Integer> queue, String name) {
            this.queue = queue;
            Thread.currentThread().setName(name);
        }

        @Override
        public void run() {
            try {
                int i = random.nextInt(100);
                queue.put(i);
                System.out.println("<<<<<<<<<" + Thread.currentThread().getName() + " Producer produces: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {
        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue, String name) {
            this.queue = queue;
            Thread.currentThread().setName(name);
        }

        @Override
        public void run() {
            try {
                int i = queue.take();
                System.out.println(">>>>>" + Thread.currentThread().getName() + " Consumer consumes: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static BlockingQueue<Integer> queue;
    public static void main(String[] args) {
        queue = new LinkedBlockingQueue<Integer>(10);
        for (int i=0;i<10;i++){
            Thread p = new Thread(new Producer(queue, i + "-Producer-Thread"));
            p.start();
        }
        for (int i=0;i<20;i++) {
            Thread c = new Thread(new Consumer(queue, i + "-"));
            c.start();
        }
    }

}
