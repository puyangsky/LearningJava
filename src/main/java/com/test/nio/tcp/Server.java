package com.test.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/3.
 */
public class Server {
    static int port = 20001;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        String string = "hello client";
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes());
        ByteBuffer in = ByteBuffer.allocate(1024);
        System.out.println("Server wait for connection...");
        while (true) {
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                TimeUnit.SECONDS.sleep(1);
            }else {
                //rewind只是将position调到0，不会改变Limit的值，而flip是将limit调整成position，再把position改成0
                System.out.println("Server get a connection...");

                sc.read(in);
                in.flip();
                byte[] bytes = new byte[in.remaining()];
                in.get(bytes);

                buffer.rewind();
                sc.write(buffer);
                System.out.println("From client:" + new String(in.array(), "UTF-8"));
            }
        }
    }
}
