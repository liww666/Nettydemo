package com.sunyard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by lww on 2018/10/22.
 */
public class Test1 {
    public static void main(String[] args) {
        long start1=System.currentTimeMillis();
        for(int i=0;i<100000000;i++){
            int j=i&7;
        }
        long end1=System.currentTimeMillis();
        System.out.println("& time used:"+(end1-start1));
        new BeeperControl().beepForAnHour();
//        long start2=System.currentTimeMillis();
//        for(int i=0;i<1000000000;i++){
//            int j=i%7;
//        }
//        long end2=System.currentTimeMillis();
//        System.out.println("% time used:"+(end2-start2));
    }
}
class BeeperControl {
   private final ScheduledExecutorService scheduler =
     Executors.newScheduledThreadPool(1);

            public void beepForAnHour() {
    final Runnable beeper = new Runnable() {
      public void run() { System.out.println("beep"); }
    };
    final ScheduledFuture<?> beeperHandle =
      scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
     scheduler.schedule(new Runnable() {
     public void run() { beeperHandle.cancel(true); }
     }, 20, SECONDS);
   }
  }
