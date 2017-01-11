package com.test.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/8/18.
 */
public class TransferTest {
    public static final int SIZE = 1024;
    public static void transfer() throws IOException {
        System.out.println("input from and to:");
//        String from = new Scanner(System.in).nextLine();
//        String to = new Scanner(System.in).nextLine();
        String from = "D:/test.txt";
        String to = "D:/test1.txt";

        FileChannel in = new FileInputStream(from).getChannel();
        FileChannel out = new FileOutputStream(to).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        while (in.read(buffer) != -1) {
            buffer.flip();
            System.out.println("buffer的当前大小：" + buffer.limit());
            System.out.println("buffer的容量：" + buffer.capacity());
            out.write(buffer);
            buffer.clear();
        }
        in.close();
        out.close();
    }
    public static void main(String[] args) throws IOException {
        transfer();
    }
}
