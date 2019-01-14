package com.sunyard.Proxy;

/**
 * Created by lww on 2018/11/12.
 */
public class InterImpl implements Inter {
    @Override
    public String sayHello(String name) {
        System.out.println("Hello "+name);
        return "Hello "+name;
    }
}
