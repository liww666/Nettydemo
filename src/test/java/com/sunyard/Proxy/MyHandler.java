package com.sunyard.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by lww on 2018/11/12.
 */
public class MyHandler implements InvocationHandler {
    private Object origin;

    public MyHandler(Object origin) {
        this.origin = origin;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---------before-----------");
        Object obj=method.invoke(origin,args);
        System.out.println("---------after-----------");
        return obj;
    }
}
