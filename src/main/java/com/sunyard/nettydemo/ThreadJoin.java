package com.sunyard.nettydemo;

/**
 * Created by lww on 2018/9/30.
 */
public class ThreadJoin {
    public static void main(String[] args){
        Thread t=new Thread(){
            public void run(){
                try {
                    Thread.sleep(3000);
                    System.out.println("t is over...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
//        t.setDaemon(true);
        t.start();

        System.out.println("before join...");
        try {
            t.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after join...");
    }
}
