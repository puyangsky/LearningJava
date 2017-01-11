package com.leetcode;

import com.test.thread.sycronized.IntGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/15.
 */
public class L349 {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i=0;i<10;i++) {
            set.add(i);
        }
        List<Integer> list = new ArrayList<Integer>();
        list.addAll(set);


        Integer[] array = list.toArray(new Integer[list.size()]);
        for(int i:array) {
            System.out.println(i);
        }

    }
}
