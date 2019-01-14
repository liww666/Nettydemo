package com.sunyard.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lww on 2018/11/22.
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch=new CountDownLatch(1);
        new Thread("t1"){
            public void run(){
                try {
                    latch.await();
                    System.out.println("t1 wake up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread("t2"){
            public void run(){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main wake up");
    }
}
