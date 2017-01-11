package com.test.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/9/1.
 */
public class FileChannelTest {

    public static void readFile(String path) throws IOException {
        FileChannel fc = new FileInputStream(path).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        StringBuilder sb = new StringBuilder();
        while ((fc.read(buffer)) >= 0) {
            System.out.println(buffer.limit());
            //翻转指针
//            buffer.flip();
            buffer.rewind();
            System.out.println(buffer.limit());
            System.out.println(buffer.position());
            //remaining = limit - position
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            String string = new String(bytes, "UTF-8");
            sb.append(string);

            //清空buffer
            buffer.clear();
        }
        System.out.println(sb.toString());
    }

    public static void writeFile(String path, String string) throws IOException {
        FileChannel fc = new FileOutputStream(path).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        int current = 0;
        int len = string.getBytes().length;
        while (current < len) {
            for (int i=0;i<10;i++) {
                if (current+i>=len) break;
                buffer.put(string.getBytes()[current+i]);
            }
            current += buffer.position();

            buffer.flip();
            fc.write(buffer);
            buffer.clear();
        }
    }


    public static void main(String[] args) throws IOException {
        String in = "D:/in.txt";
        String out = "D:/out.txt";
//        readFile(in);
//        writeFile(out, "hello world");
        readFile(out);
    }
}
