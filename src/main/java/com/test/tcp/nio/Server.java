package com.test.tcp.nio;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Server {
    public static void main(String[] args) {
        //多selector模式
        ServerThread serverThread = new ServerThread(20005);
        new Thread(serverThread, "Server Thread 001").start();
    }
}
