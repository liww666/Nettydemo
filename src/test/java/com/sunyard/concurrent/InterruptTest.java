package com.sunyard.concurrent;

/**
 * Created by lww on 2018/10/26.
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(){
            public void run(){
                try {
                    Thread.sleep(5000);//被中断时不仅会抛出异常，还会清除中断状态
                } catch (InterruptedException e) {
                    System.out.println("isInterrupted:"+this.isInterrupted());
                }
                while (!this.isInterrupted()){
                    System.out.println("t is running...");
                }
            }
        };
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        System.out.println(t.isInterrupted());
    }
}
