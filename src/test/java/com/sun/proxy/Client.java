package com.sun.proxy;

import com.sunyard.Proxy.Inter;
import com.sunyard.Proxy.InterImpl;
import com.sunyard.Proxy.MyHandler;

import java.lang.reflect.Proxy;

/**
 * Created by lww on 2018/11/12.
 */
public class Client {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Inter inter=new InterImpl();
        Inter interProxy=(Inter) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),inter.getClass().getInterfaces(),new MyHandler(inter));
        String result=interProxy.sayHello("proxy");
        System.out.println("result:"+result.toString());
    }
}
