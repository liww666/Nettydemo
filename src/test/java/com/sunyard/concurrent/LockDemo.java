package com.sunyard.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lww on 2018/11/22.
 */
public class LockDemo {
    public static void main(String[] args)throws Exception {
        Lock  lock=new ReentrantLock();
        Thread t1=new Thread(){
            public void run(){
                try {
                    lock.lock();
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    System.out.println("t1 is interrupted!");
                } finally {
                    lock.unlock();
                }
            }
        };
        t1.start();
        Thread.sleep(100);
        Thread t2=new Thread(){
            public void run(){
                try{
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("t2 is interrupted!");
                } finally {

                }
            }
        };
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        Thread.sleep(100);
        t1.interrupt();
    }
}
