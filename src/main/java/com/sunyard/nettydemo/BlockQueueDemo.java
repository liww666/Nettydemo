package com.sunyard.nettydemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lww on 2018/9/30.
 */
public class BlockQueueDemo {
    public static void main(String[] args){
        long start=System.currentTimeMillis();
        BlockingQueue queue=new ArrayBlockingQueue(5);
        ThreadPoolExecutor executor=new ThreadPoolExecutor(3,5,10,TimeUnit.MINUTES,new ArrayBlockingQueue<>(5));
        try{
            for(int i=0;i<3;i++){
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            queue.put(new Element());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }finally {
            executor.shutdown();
        }
        while (!executor.isTerminated()){

        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
