package com.sunyard.Proxy;

/**
 * Created by lww on 2018/11/12.
 */
public class ProxyUtilsTest {
    public static void main(String[] args) {
        Inter inter=new InterImpl();
        ProxyUtils.generateClassFile(inter.getClass(),"InterProxy");
    }
}
