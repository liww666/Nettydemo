package com.sunyard.concurrent;

/**
 * Volatile不能保证多个线程对共享变量同时写的线程安全
 * Created by lww on 2018/10/29.
 */
public class VolatileTest {
    static volatile int i=0;
    public static void main(String[] args) throws Exception{
        Thread t1=new Thread(){
            @Override
            public void run() {
                for(int j=0;j<1000;j++){
                    int k=i;
                    k++;
                    Thread.yield();
                    i=k;
                }
            }
        };
        Thread t2=new Thread(){
            @Override
            public void run() {
                for(int j=0;j<1000;j++){
                    int k=i;
                    k++;
                    Thread.yield();
                    i=k;
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("i="+i);
    }
}
