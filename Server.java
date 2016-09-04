package com.test.nio.tcp.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/9/4.
 */
public class Server {
    public static final int PORT = 20000;
    public static void main(String[] args) throws IOException {
        new Server().select();
    }

    public void select() throws IOException {
        //创建选择器
        Selector selector = Selector.open();
        //创建serverChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //设置为非阻塞模式
        ssc.configureBlocking(false);
        //绑定监听的地址
        ssc.socket().bind(new InetSocketAddress(PORT), 1024);
        //将serverChannel注册到选择器上，监听accept事件，返回选择键
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server begin listening on " + ssc.socket().getLocalSocketAddress().toString());
        while (true) {
            //此次选择过程准备就绪的通道数量
            int num = selector.select();
            if (num == 0) {
                //若没有准备好的就继续循环
                continue;
            }
            //返回已就绪的键集合
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                handleKeys(selector, key);
                //因为已经处理了该键，所以把当前的key从已选择的集合中去除
                it.remove();
            }
        }
    }

    public void handleKeys(Selector selector, SelectionKey key) throws IOException {
        if (key.isValid()) {
            //当一个serverChannel为accept状态时，注册其socketChannel为可读取状态
            if (key.isAcceptable()) {
                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                SocketChannel channel = serverChannel.accept();
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            }
            //如果channel是可读取状态，则读取其中的数据
            if (key.isReadable()) {
                System.out.println("Server get a connection...");
                //只有SocketChannel才能读写数据，所以如果是可读取状态，只能是SocketChannel
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer in = ByteBuffer.allocate(1024);
                try {
                    //将socketChannel中的数据读入到buffer中，返回当前字节的位置
                    int readBytes = sc.read(in);
                    if (readBytes > 0) {
                        //把buffer的position指针指向buffer的开头
                        in.flip();
                        byte[] bytes = new byte[in.remaining()];
                        in.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        System.out.println("The server receive : " + body);
                        //把response输出到socket中
                        doWrite(sc, "Hello client");
                    } else if (readBytes < 0) {
                        //对端链路关闭
                        key.cancel();
                        sc.close();
                    }
                }catch (IOException e) {
                    System.err.println("远程主机强迫关闭了一个现有的连接");
                    key.cancel();
                    if (key.channel() != null) {
                        key.channel().close();
                    }
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
