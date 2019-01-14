package com.sunyard.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by lww on 2018/11/22.
 */
public class ReadWriteLockDemo {
    private static Integer i=0;
    public static void main(String[] args) throws Exception{
        CountDownLatch latch=new CountDownLatch(12);
        ReadWriteLock lock=new ReentrantReadWriteLock();
        Runnable readTask=new Runnable() {
            @Override
            public void run() {
                try{
                    lock.readLock().lock();
                    Thread.sleep(1000);
                    System.out.println(i);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.readLock().unlock();
                }

            }
        };
        Runnable writeTask=new Runnable() {
            @Override
            public void run() {
                try{
                    lock.writeLock().lock();
                    System.out.println("writing...");
                    Thread.sleep(3000);

                    i=1;
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }
            }
        };
        long start=System.currentTimeMillis();
        for(int i=0;i<10;i++){
            new Thread(readTask).start();
        }
        for(int i=0;i<2;i++){
            new Thread(writeTask).start();
        }
        latch.await();
        System.out.println("use time:"+(System.currentTimeMillis()-start));
    }
}
