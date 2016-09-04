package com.test.nio.tcp.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/9/4.
 */
public class Client {
    public static final int PORT = 20000;
    public static final String HOST = "127.0.0.1";
    private volatile boolean stop = false;

    public static void main(String[] args) throws IOException {
        new Client().select();
    }

    public void select() throws IOException {
        // 创建选择器
        Selector selector = Selector.open();
        // 创建SocketChannel
        SocketChannel sc = SocketChannel.open();
        // 设置为非阻塞模式
        sc.configureBlocking(false);
        try {
            doConnect(selector, sc);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!stop) {
            int num = selector.select();
            if (num == 0) {
                continue;
            }
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                try {
                    handleKeys(selector, key);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (key != null) {
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                }
                // 因为已经处理了该键，所以把当前的key从已选择的集合中去除
                it.remove();
            }
        }

        if (selector != null) {
            selector.close();
        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
            if (!writeBuffer.hasRemaining()) {
                System.out.println("Send msg successfully");
            }
        }
    }

    private void handleKeys(Selector selector, SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            // 判断是否连接成功
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc, "Hello Server");
                } else {
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                ByteBuffer in = ByteBuffer.allocate(1024);
                // 将socketChannel中的数据读入到buffer中，返回当前字节的位置
                int readBytes = sc.read(in);
                if (readBytes > 0) {
                    // 把buffer的position指针指向buffer的开头
                    in.flip();
                    byte[] bytes = new byte[in.remaining()];
                    in.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The Client receive : " + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    // 读到0字节，忽略
                }
            }
        }
    }

    private void doConnect(Selector selector, SocketChannel sc) throws IOException {
        if (sc.connect(new InetSocketAddress(HOST, PORT))) {
            System.out.println("Client connect successfully...");
            // 如果直接连接成功，则注册读操作
            sc.register(selector, SelectionKey.OP_READ);
            doWrite(sc, "Hello server!");
        } else {
            // 如果没有连接成功，则注册连接操作
            sc.register(selector, SelectionKey.OP_CONNECT);
        }
    }
}
