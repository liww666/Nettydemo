package com.sunyard.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by lww on 2018/11/23.
 */
public class FutureAndCallable {
    public static void main(String[] args) throws Exception{
        FutureTask<Integer> futureTask_1=new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum_1=0;
                for(int i=0;i<10000;i++){
                    sum_1+=i;
                    Thread.sleep(1);
                }
                return sum_1;
            }
        });
        FutureTask<Integer> futureTask_2=new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum_2=0;
                for(int i=10000;i<20000;i++){
                    sum_2+=i;
                    Thread.sleep(1);
                }
                return sum_2;
            }
        });
        long start=System.currentTimeMillis();
        new Thread(futureTask_1).start();
        new Thread(futureTask_2).start();
        int sum=futureTask_1.get()+futureTask_2.get();
        long end=System.currentTimeMillis();
        System.out.println("sum="+sum+",time:"+(end-start));
        sum=0;
        for(int i=0;i<20000;i++){
            sum+=i;
            Thread.sleep(1);
        }
        System.out.println("sum="+sum+",time:"+(System.currentTimeMillis()-end));


    }
}
