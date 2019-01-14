package com.sunyard.concurrent;

/**
 * Created by lww on 2018/11/5.
 */
public class SyncCodeBlock {
    public int i;

    public void syncTask(){
        //同步代码库
        synchronized (this){
            i++;
        }
    }
}
