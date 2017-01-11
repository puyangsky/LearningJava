package com.test.callback;

/**
 * Created by Administrator on 2016/6/13.
 */
public class CallbackTest {
    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(server);
        client.sendMsg("hello", new Callback() {
            @Override
            public void process(int status) {
                System.out.println("处理成功，返回状态为：" + status);
            }
        });
    }
}

