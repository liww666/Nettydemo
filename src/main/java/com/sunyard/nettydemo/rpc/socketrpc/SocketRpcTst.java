package com.sunyard.nettydemo.rpc.socketrpc;

import com.sunyard.nettydemo.rpc.HelloRpc;

/**
 * Created by lww on 2018/10/10.
 */
public class SocketRpcTst {
    public static void main(String[] args){
        HelloRpc helloRpc=RpcProxy.creat(HelloRpc.class);
        System.out.println(helloRpc.hello("lww"));
    }
}
