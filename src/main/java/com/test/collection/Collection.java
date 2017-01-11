package com.test.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by puyangsky on 2016/12/13.
 */
public class Collection {
    public static void main(String[] args) {
        String a = "a";
        boolean b = a.equals("a");
//        System.out.println(b);
        String str1 = "编程";
        String str2 = "编程";
        String str3 = new String("编程");
        String str4 = str3.intern();

//        System.out.print(str1==str2);  //两直接量相比  true
//        System.out.print(str1==str3);  //直接量和对象相比 false
//        System.out.print(str1==str4);  //经过intern处理的对象与直接量相比 true


        Object o = new Object();
        str3.hashCode();

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        Vector<Integer> vector = new Vector<Integer>();
        arrayList.add(1);
        System.out.println(arrayList.size());
        for (int i=0;i<10;i++) {
            arrayList.add(i);
        }
        System.out.println(arrayList.size());
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();

        test();
    }

    public static int test() {
        try {
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("hello");
        }
        return 1;
    }
}
