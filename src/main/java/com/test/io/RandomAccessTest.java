package com.test.io;

import java.io.*;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RandomAccessTest {
    public static void main(String[] args) {
        String filePath = "D:" + File.separator + "test.txt";
        File f = new File(filePath);
        try {
            RandomAccessFile rand = new RandomAccessFile(f, "rw");
            String str = "abc, hello, randomAccess!";
            //把byte[]中的字节写入文件
            rand.write(str.getBytes());
            System.out.println("写入文件完成...");
//            String read;
//            StringBuilder sb = new StringBuilder();
//            while ((read = rand.readLine()) != null) {
//                sb.append(read);
//            }
            RandomAccessFile read = new RandomAccessFile(f, "rw");
            String strRead;
            StringBuilder sb = new StringBuilder();
            while ((strRead = read.readLine()) != null) {
                sb.append(strRead);
            }
            System.out.println("读取文件内容：" + sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
