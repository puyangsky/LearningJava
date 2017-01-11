package com.test.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        int port = 20005;
        new Thread(new ClientThread("127.0.0.1", port), "Client Thread 001").start();
    }
}
