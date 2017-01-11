package com.test.tcp.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ClientThread implements Runnable {
    private String ip;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private boolean stop;

    public ClientThread(String ip, int port){
        this.ip = ip == null ? "127.0.0.1" : ip;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void stopClient() {
        this.stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                int num = selector.select();
                if (num == 0) {
                    continue;
                }
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    handleKeys(key);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleKeys(SelectionKey key) {

    }
}
