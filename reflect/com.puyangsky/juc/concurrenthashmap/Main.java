package juc.concurrenthashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by puyangsky on 2017/1/7.
 */
public class Main {
    public static void main(String[] args) {
        Map<Integer, String> map = new ConcurrentHashMap<Integer, String>();
    }
}
