package com.sunyard.concurrent;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lww on 2018/10/26.
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    public PrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue=queue;
    }
    public void run(){
        try{
            BigInteger p=BigInteger.ONE;
            while (!(Thread.currentThread().isInterrupted())){
                queue.put(p=p.nextProbablePrime());
                Thread.sleep(50);
            }
        }catch (InterruptedException e){
            System.out.println("task is canceled");
        }
    }
    public void cancel(){interrupt();}

    public BlockingQueue<BigInteger> getQueue(){
        return queue;
    }

    public static void main(String[] args) throws InterruptedException {
        PrimeProducer producer=new PrimeProducer(new LinkedBlockingQueue<>());
        producer.start();
        Thread.sleep(500);
        producer.cancel();
        System.out.println(producer.getQueue());
    }
}
