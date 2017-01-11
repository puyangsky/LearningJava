package com.test.proxy.staticProxy;

/**
 * Created by Administrator on 2016/9/24.
 */
public class Student implements Person{
    private String content;
    public Student(String content) {
        this.content = content;
    }

    @Override
    public void speak() {
        System.out.println(content);
    }

}
