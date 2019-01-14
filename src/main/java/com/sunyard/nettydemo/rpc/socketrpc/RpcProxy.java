package com.sunyard.nettydemo.rpc.socketrpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * Created by lww on 2018/10/10.
 */
public class RpcProxy {
    public static <T> T creat(Class clazz){
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket=new Socket("localhost",8088);
                        ObjectInputStream inputStream=null;
                        ObjectOutputStream outputStream=null;
                        try{
                            outputStream=new ObjectOutputStream(socket.getOutputStream());
                            outputStream.writeUTF(clazz.getName());
                            outputStream.writeUTF(method.getName());
                            outputStream.writeObject(method.getParameterTypes());
                            outputStream.writeObject(args);
                            inputStream=new ObjectInputStream(socket.getInputStream());
                            Object result=inputStream.readObject();
                            if(result instanceof Throwable){
                                throw (Throwable)result;
                            }else{
                                return result;
                            }
                        }finally {
                            inputStream.close();
                            outputStream.close();
                            socket.close();
                        }

                    }
                });
    }
}
