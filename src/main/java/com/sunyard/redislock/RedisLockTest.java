package com.sunyard.redislock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lww on 2018/10/23.
 */
public class RedisLockTest {
    private Jedis jedis=new Jedis("localhost",6379);
    public boolean lock(String lockId){
        while(true){
            long rlt=jedis.setnx(lockId,"1");
            if(rlt>0){
                System.out.println(Thread.currentThread().getName()+" get lock :"+lockId);
                return true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }


    }
    public boolean unlock(String lockId){
        long rlt=jedis.del(lockId);
        if(rlt>0){
            System.out.println(Thread.currentThread().getName()+" unlock "+lockId);
            return true;
        }
        System.out.println(Thread.currentThread().getName()+" unlock fail");
        return false;
    }

    public static void main(String[] args) {
        String lockId="test1";
        Runnable task=new Runnable() {
            @Override
            public void run() {
                RedisLockTest lock=new RedisLockTest();
                lock.lock(lockId);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock(lockId);
            }
        };

        ExecutorService executor=new ThreadPoolExecutor(3,5,5,TimeUnit.MINUTES,new LinkedBlockingQueue<>(3));
        for(int i=0;i<3;i++){
            executor.submit(task);
        }
    }
}
