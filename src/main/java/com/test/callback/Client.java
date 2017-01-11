package com.test.callback;

/**
 * Created by puyangsky on 2016/6/13.
 */
public class Client {
    Server server;
    public Client(Server server) {
        this.server = server;
    }
    public void sendMsg(final String msg, final Callback callback) {
        System.out.println("客户端正在发生消息：" + msg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.getMsg(callback, msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

interface Callback {
    void process(int status);
}
