package com.test.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/3.
 */
public class Client {
    public static String ADDR = "127.0.0.1";
    public static int PORT = 20001;
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress(ADDR, PORT));
        sc.configureBlocking(false);
        String string = "hello server";
        ByteBuffer out = ByteBuffer.wrap(string.getBytes());
        ByteBuffer in = ByteBuffer.allocate(1024);
        out.rewind();
        sc.write(out);
        TimeUnit.SECONDS.sleep(1);
        sc.read(in);
        in.flip();
        byte[] bytes = new byte[in.remaining()];
        in.get(bytes);
        System.out.println("From server: " + new String(bytes, "UTF-8"));
        sc.close();
    }
}
