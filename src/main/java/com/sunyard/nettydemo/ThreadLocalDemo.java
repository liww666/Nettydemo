package com.sunyard.nettydemo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lww on 2018/9/30.
 */
public class ThreadLocalDemo {
    private static ThreadLocal<Object> tl=new ThreadLocal<Object>(){
        @Override
        protected Object initialValue() {
            Object obj=new Object();
            System.out.println("initialValue:"+obj);
            return obj;
        }
    };
    public static void main(String[] args){
        ThreadPoolExecutor executor=null;
        try{
            executor=new ThreadPoolExecutor(3,5,5,TimeUnit.MINUTES
            ,new LinkedBlockingQueue<>(5));
            for(int i=0;i<5;i++){
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        tl.set(new Object());
                        System.out.println(Thread.currentThread().getName()+":"+tl.get());
                    }
                });
            }
        }finally {
            executor.shutdown();
        }
        System.out.println(Thread.currentThread().getName()+":"+tl.get());
    }
}
