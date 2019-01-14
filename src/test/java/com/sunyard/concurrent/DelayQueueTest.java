package com.sunyard.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by lww on 2018/11/23.
 */
public class DelayQueueTest {
    public static void main(String[] args) throws Exception{
        DelayQueue queue=new DelayQueue();
        queue.offer(new DelayTask(2000,"task1"));
        queue.offer(new DelayTask(1000,"task2"));
        queue.offer(new DelayTask(3000,"task3"));
        Delayed task=queue.poll();
        System.out.println(queue);
        if(task==null){
            System.out.println("no expired task!");
        }else{
            System.out.println("process:"+((DelayTask)task).getName());
        }
        Thread.sleep(1500);
        task=queue.poll();
        if(task==null){
            System.out.println("no expired task!");
        }else{
            System.out.println("process:"+((DelayTask)task).getName());
        }
        System.out.println(queue.poll());
        System.out.println(queue.take());

    }

    private static class DelayTask implements Delayed{
        private long delayTime;
        private String name;

        public long getDelayTime() {
            return delayTime;
        }

        public String getName() {
            return name;
        }

        public DelayTask(long delayTime, String name) {
            this.delayTime = System.currentTimeMillis()+delayTime;
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long difference = delayTime - System.currentTimeMillis();
            return unit.convert(difference, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayTask other=(DelayTask)o;
            if(this.delayTime<other.delayTime){
                return -1;
            }
            if(this.delayTime>other.delayTime){
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "DelayTask{" +
                    "delayTime=" + delayTime +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
