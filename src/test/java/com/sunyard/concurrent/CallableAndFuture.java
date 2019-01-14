package com.sunyard.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by lww on 2018/11/22.
 */
public class CallableAndFuture {
    public static void main(String[] args) throws Exception{
        Callable callable=new Callable(){

            @Override
            public Object call() throws Exception {
                Thread.sleep(3000);
                return new Object();
            }
        };
        FutureTask task=new FutureTask<>(callable);
        Thread t=new Thread(task);
        t.start();
        System.out.println(task.get());
    }
}
