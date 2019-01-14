package com.sunyard.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lww on 2018/11/23.
 */
public class DCLSingletonTest {
    public static void main(String[] args) {
        ExecutorService executor=new ThreadPoolExecutor(10,15,5,TimeUnit.MINUTES,new LinkedBlockingQueue<>(5));
        Runnable task=new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    if(DCLSingleton.getInstance().getNum()==0){

                        System.out.println("broken");
                    }
                }
            }
        };
        for(int i=0;i<10;i++){
            executor.submit(task);
        }
        executor.shutdown();
    }

    static class DCLSingleton{
        private  int num=0;
        private volatile static DCLSingleton instance;//加volatile可以使instance可以正常发布
        private DCLSingleton(){
            Thread.yield();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num=new Random().nextInt(1000)+1;
        }
        public static DCLSingleton getInstance(){
            Thread.yield();
            if(instance==null){
                Thread.yield();
                synchronized (DCLSingleton.class){
                    if(instance==null){
                        instance=new DCLSingleton();
                    }
                }
            }
            return instance;
        }
        public int getNum(){
            return this.num;
        }
    }

}
