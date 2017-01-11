package com.test.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ServerThread implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public ServerThread(int port) {
        try {
            //创建选择器
            selector = Selector.open();
            //创建serverChannel
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //将serverChannel注册到选择器上，监听accept事件，返回选择键
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                //每隔1秒被唤醒一次，轮询查看是否有就绪状态的channel
                selector.select(1000);
                //返回就绪状态的channel的selectionkey集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //当一个channel为accept状态时，注册为读取状态
            if (key.isAcceptable()) {
                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                SocketChannel channel = serverChannel.accept();
                serverChannel.configureBlocking(false);

                serverChannel.register(selector, SelectionKey.OP_READ);
            }
            //如果channel是可读取状态，则读取其中的数据
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //将socketChannel中的数据读入到buffer中，返回当前字节的位置
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    //把buffer的position指针指向buffer的开头
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The server receive : " + body);
                    //把response输出到socket中
                    doWrite(sc, "Hello client");
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }
    private void doWrite(SocketChannel sc, String response) throws IOException {
        if (response == null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }

}
