package com.sunyard.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lww on 2018/11/22.
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier barrier=new CyclicBarrier(5);
        Random random=new Random();
        for(int i=0;i<5;i++){
            new Thread(){
                public void run(){
                    System.out.println(Thread.currentThread()+"step1 start...");
                    try {
                        Thread.sleep(random.nextInt(3000));
                        System.out.println(Thread.currentThread()+"step1 done!");
                        barrier.await();
                        System.out.println(Thread.currentThread()+"step2 start...");
                        Thread.sleep(random.nextInt(3000));
                        System.out.println(Thread.currentThread()+"step2 done!");
                        barrier.await();
                        System.out.println(Thread.currentThread().getName()+System.currentTimeMillis()+" over!");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
