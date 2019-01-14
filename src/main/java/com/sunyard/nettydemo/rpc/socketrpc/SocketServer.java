package com.sunyard.nettydemo.rpc.socketrpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lww on 2018/10/10.
 */
public class SocketServer {
    public static void main(String[] args) throws Exception{
         ConcurrentHashMap<String, Object> classMap = new ConcurrentHashMap<String,Object>();

        ThreadPoolExecutor executor=new ThreadPoolExecutor(3,5,10,TimeUnit.MINUTES,new LinkedBlockingQueue<>(5));
        ServerSocket serverSocket=new ServerSocket(8088);
        System.out.println("Server started...");
        while(true){
            final Socket socket=serverSocket.accept();
            try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ObjectOutputStream outputStream=null;
                    ObjectInputStream inputStream=null;
                    try {
                        outputStream=new ObjectOutputStream(socket.getOutputStream());
                        inputStream=new ObjectInputStream(socket.getInputStream());
                        String className=inputStream.readUTF();
                        String methodName=inputStream.readUTF();
                        Class<?>[] paramTypes=(Class<?>[])inputStream.readObject();
                        Object[] params=(Object[])inputStream.readObject();
                        Object instance=null;
                        if(!classMap.contains(className)){
                            instance=Class.forName("com.sunyard.nettydemo.rpc.HelloRpcImpl").newInstance();
                            classMap.put(className,instance);
                        }else{
                            instance=classMap.get(className);
                        }
                        Method method=instance.getClass().getMethod(methodName,paramTypes);
                        Object result=method.invoke(instance,params);
                        outputStream.writeObject(result);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try {
                            outputStream.close();
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            }catch (Exception e){
                e.printStackTrace();
                socket.close();
            }
        }
    }
}
