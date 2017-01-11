package com.test.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by puyangsky on 2016/12/25.
 */
public class CollectionList {
    public static void set() {
        Set<Integer> hashSet = new HashSet<Integer>();
        Set<Integer> linkedHashSet = new LinkedHashSet<Integer>();
        Set<Integer> treeSet = new TreeSet<Integer>();
        Set<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<Integer>();


        for(int i=0;i<10;i++) {
            hashSet.add(i);
        }

        Iterator<Integer> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for (int j : hashSet) {
            System.out.println(j);
        }
    }


    public static void list() {
        List<String> l = new ArrayList<String>();
        List<String> l1 = new LinkedList<String>();
        List<String> l2 = new CopyOnWriteArrayList<String>();
        List<String> vector = new Vector<String>();
        List<String> stack = new Stack<String>();
    }


    public static void queue() {
        Queue<Integer> queue = new PriorityQueue<Integer>();
    }

    public static void map() {
        Map<Integer, String> hashMap = new HashMap<Integer, String>();
        Map<Integer, String> map = new Hashtable<Integer, String>();
        Map<Integer, String> map1 = Collections.synchronizedMap(hashMap);
        Map<Integer, String> map2 = new ConcurrentHashMap<Integer, String>();
        Map<Integer, String> map3 = new TreeMap<Integer, String>();
    }


    public static void main(String[] args) {
        set();
    }
}
