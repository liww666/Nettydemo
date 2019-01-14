package com.sunyard.nettydemo.rpc;

/**
 * Created by lww on 2018/10/10.
 */
public class HelloRpcImpl implements HelloRpc {
    @Override
    public String hello(String name) {
        return "Hello "+name;
    }
}
