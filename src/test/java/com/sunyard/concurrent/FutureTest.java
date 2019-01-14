package com.sunyard.concurrent;

import java.util.concurrent.*;

/**
 * Created by lww on 2018/10/26.
 */
public class FutureTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executor=new ThreadPoolExecutor(3,5,5,TimeUnit.MINUTES,new LinkedBlockingQueue<>(3));
        Future future=executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(5000);
                return new Object();
            }
        });
        Object obj=future.get(3,TimeUnit.SECONDS);
        System.out.println(obj);
        executor.shutdown();
    }
}
