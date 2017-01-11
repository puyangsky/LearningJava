package com.test.callback;

/**
 * Created by puyangsky on 2016/6/13.
 */
public class Server {
    public void getMsg(Callback callback, String msg) throws InterruptedException {
        System.out.println("服务端获得消息：" + msg);
        System.out.println("服务端正在处理...");
        Thread.sleep(2000);
        System.out.println("服务端处理成功，返回状态为200");
        callback.process(200);
    }
}
