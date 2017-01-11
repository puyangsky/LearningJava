package com.test.proxy.dynamicProxy.reflect;

import com.test.proxy.staticProxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/9/24.
 */
public class ReflectTest {
    public static void main(String[] args) {
        Actor actor = new Actor("大家好我是actor");
        Apple apple = new Apple();
        Agent agent = new Agent();
        Person person = (Person) agent.bind(actor);
        person.speak();
        System.out.println("====================================");
        Fruit fruit = (Fruit) agent.bind(apple);
        fruit.grow();
    }
}
class Agent implements InvocationHandler {
    private Object target;
    public Object bind(Object target) {
        this.target = target;
        System.out.println("//target：" + target.getClass().toString());
        System.out.println("//class: " + target.getClass().getClassLoader().toString());
        System.out.println("//interface: " + target.getClass().getInterfaces()[0].toString());
        //获取代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("事件开始···");
        result = method.invoke(target, args);
        System.out.println("事件结束···");
        return result;
    }
}