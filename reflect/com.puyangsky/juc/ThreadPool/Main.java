package juc.ThreadPool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by puyangsky on 2017/1/6.
 */
public class Main {
    private static int index = 0;

    public static void main(String[] args) {
//        ExecutorService executor =
//                new ThreadPoolExecutor(5, 10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//        ExecutorService service = Executors.newFixedThreadPool(10);
//        for (int i=0;i<100;++i) {
//            executor.execute(new Task());
//            executor.submit(new Task());
//        }
        //executor.shutdown();

        Queue<String> queue = new PriorityBlockingQueue<String>(5);
        boolean a = queue.add("a");
        queue.add("a");
        queue.add("a");
        queue.add("a");
        queue.add("a");
        queue.add("a");
        boolean b = queue.offer("b");
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            index++;
            System.out.println(index);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
