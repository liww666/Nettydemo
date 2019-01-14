package com.sunyard.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lww on 2018/11/22.
 */
public class MyContainer {
    private List<Object> list=new LinkedList<>();
    private int capacity=100;
    private Lock lock=new ReentrantLock();
    private Condition notFull=lock.newCondition();
    private Condition notEmpty=lock.newCondition();
    public void add(Object obj){
        try{
            lock.lock();
            while(list.size()>=capacity){
                notFull.await();
            }
            list.add(obj);
            System.out.println(Thread.currentThread()+" add,list.size="+list.size());
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void remove(){
        try{
            lock.lock();
            while(list.size()<=0){
                notEmpty.await();
            }
            list.remove(0);
            System.out.println(Thread.currentThread()+" removed,list.size="+list.size());
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(20);
        MyContainer container=new MyContainer();
        ThreadPoolExecutor executor=new ThreadPoolExecutor(20,25,5,TimeUnit.MINUTES,new LinkedBlockingQueue<>(10));
        for(int i=0;i<10;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<100;j++){
                        container.add(new Object());
                    }
                    latch.countDown();
                }
            });
        }
        for(int i=0;i<10;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<100;j++){
                        container.remove();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println("The end!");
        executor.shutdown();
    }
}
