package com.test.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/18.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
//        File file = new File("D:" + File.separator + "test.txt");
//        File file = new File("D:" + File.separator);
//        file.createNewFile();
//        if (file.exists()) {
//            file.delete();
//        }else {
//            System.err.println("file not found");
//        }
        FileTest.listFile("D:" + File.separator);

    }

    public static void listFile(String directoryName) {
        File file = new File(directoryName);
        String [] fileList = file.list();
        System.out.println("Directory: " + directoryName);
        for (String f : fileList) {
            if (f.startsWith("$")) {
                continue;
            }
            File newF = new File(directoryName + f + File.separator);
            if (newF.isDirectory()) {
                listFile(directoryName + f + File.separator);
            }
            if (newF.isFile()) {
                System.out.println("\t" + f);
            }
        }
    }
}
