package com.sunyard.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lww on 2018/10/23.
 */
public class HideIterator {
    public static void main(String[] args) {
        iterateConcurrentMap();
    }
    public static void iterateConcurrentMap(){
        ConcurrentHashMap map=new ConcurrentHashMap();
        map.put(1,"1");
        new Thread(){
            public void run(){
                int i=2;
                while (true){
                    map.put(i++,"2");
                    Thread.yield();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        for(;;){
            System.out.println("map:"+map);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void iterateList(){
        List<Integer> list=new CopyOnWriteArrayList<>();
        for(int i=0;i<10;i++){
            list.add(i);
        }
        new Thread(){
            public void run(){
                while (true){
                    list.add(10);
                    Thread.yield();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        for(;;){
            //list.toString()会隐式迭代
            System.out.println("list:"+list);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
