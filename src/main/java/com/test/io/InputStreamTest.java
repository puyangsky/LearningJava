package com.test.io;

import java.io.*;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/18.
 */
public class InputStreamTest {
    public static void main(String[] args) throws IOException {
        File file = new File("D:/image/test.txt");

        String str = Calendar.getInstance().getTime().toString() + "\tHello I/O!";
        byte[] bytes= str.getBytes();
        //面向字节流
        OutputStream out = new FileOutputStream(file);
        out.write(bytes);
        System.out.println("length:" + bytes.toString().length());


        InputStream in = new FileInputStream(file);
        //面向字符流
        InputStreamReader reader = new InputStreamReader(in, "utf-8");
        BufferedReader bf = new BufferedReader(reader);
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = bf.readLine()) != null) {
            sb.append(s);
        }
        System.out.println("buffer: " + sb.toString());
    }
}
