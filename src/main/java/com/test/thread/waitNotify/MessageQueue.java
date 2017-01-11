package com.test.thread.waitNotify;

import java.util.*;

/**
 * Created by puyangsky on 2016/12/13.
 */
public class MessageQueue {
    private static final Queue<Message> queue = new LinkedList<Message>();


    public static void main(String[] args) {

        Producer producer = new Producer(queue);
        for (int i=0;i<10;i++) {
            Message message = new Message(i, "hello message queue");
            producer.put(message);
        }
        Thread p = new Thread(producer, "producer");
        Thread c = new Thread(new Consumer(queue), "consumer");
        p.start();
        c.start();
    }

}

class Message {
    private int id;
    private String msg;

    public Message(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }
    public String toString() {
        return String.format("%d\t%s", this.id, this.msg);
    }
}


class Producer implements Runnable{
    private Queue<Message> queue;
    private List<Message> messages = new ArrayList<Message>();

    public Producer(Queue queue) {
        if (queue != null) {
            this.queue = queue;
        }
    }

    public void put(Message msg) {
        if (msg != null)
            messages.add(msg);
    }

    @Override
    public void run() {
        synchronized (queue) {
            for (Message msg : messages) {
                if (queue.offer(msg)) {
                    System.out.println("+++++生产者插入消息成功，消息：" + msg.toString());
                }else {
                    System.out.println(">>>>>生产者插入消息失败，队列已满");
                }
            }
        }
    }
}

class Consumer implements Runnable {
    protected Queue<Message> queue;
    public Consumer(Queue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        synchronized (queue) {
            for (int i=0;i<15;i++) {
                Message msg;
                if ((msg = queue.poll()) != null) {
                    System.out.println("+++++消费者消费成功，消息为：" + msg.toString());
                }else {
                    System.out.println(">>>>>消息队列为空！");
                }
            }
        }
    }
}

