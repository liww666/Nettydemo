package com.sunyard.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lww on 2018/11/23.
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue queue=new LinkedBlockingQueue(5);
        Thread t1=new Thread(){
            public void run(){
                for(int i=0;i<10;i++){
                    queue.add(new Object());
                }
            }
        };
        t1.start();
        List list=new ArrayList(5);
        for(int i=0;i<10;i++){
            list.add(new Object());
        }
        System.out.println(list);
    }
}
