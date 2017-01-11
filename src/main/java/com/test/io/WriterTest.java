package com.test.io;

import java.io.*;

/**
 * Created by Administrator on 2016/8/18.
 */
public class WriterTest {
    public static void main(String[] args) throws IOException{
        String inFile = "D:/in.txt";
        String outFile = "D:/out.txt";
        BufferedReader in = new BufferedReader(new StringReader(ReaderTest.read(inFile)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
        String s;
        int line = 1;
        while ((s = in.readLine()) != null) {
            out.println(line++ + ":" + s);
        }
        in.close();
        out.close();
        System.out.println(ReaderTest.read(outFile));
    }
}
