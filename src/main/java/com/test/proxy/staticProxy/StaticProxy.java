package com.test.proxy.staticProxy;

/**
 * Created by Administrator on 2016/9/24.
 */
public class StaticProxy {
    public static void main(String[] args) {
        Actor actor = new Actor("I am a famous actor!");
        Agent agent = new Agent(actor, "Hello I am an agent.", "That's all!");
        agent.speak();
    }
}





