package com.test.Stream;

import java.io.*;

/**
 * Created by Administrator on 2016/8/18.
 */
public class InputStreamTest {
    public static void readByStream(String path) {
        File f = new File(path);
        try {
            InputStream in = new FileInputStream(f);
            int b;
            while ((b=in.read()) != -1) {
                char c = (char)b;
                System.out.printf("%c", c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readByReader(String path) {
        File f = new File(path);
        try {
            InputStream in = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String string;
            StringBuilder sb = new StringBuilder();
            while ((string = reader.readLine()) != null) {
                sb.append(string);
            }
            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "D:/test.txt";
//        readByReader(path);
//        readByStream(path);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path));
        out.write("abc");
        out.close();
    }
}
