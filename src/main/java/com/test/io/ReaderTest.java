package com.test.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ReaderTest {
    public static String read(String path) throws IOException {
        //装饰器模式，BufferedReader包装了FileReader
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(s + "\n");
        }
        reader.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(read("D:/test.txt"));
    }
}
