package com.yl.vola;

import com.yl.tools.SleepTools;

/**
 * @author yuanli
 * @create 2019-04-21 10:25
 * volatile 提供的可见性
 **/
public class VolatileCase {

   // public static boolean flag;

   /* public static volatile boolean flag;

    private static class ThreadTest implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            while (flag){
                System.out.println("主线程改变flag的值,看子线程是否知道");
            }
        }
    }

  public static void main(String[] args) throws InterruptedException {


        new Thread(new ThreadTest()).start();
        Thread.sleep(20);
        flag = true;

        System.out.println("main end");

  }*/

   // private volatile static boolean ready;
   private volatile   static  boolean ready;
    private  static int number;

    //
    private static class PrintThread extends Thread{
        @Override
        public void run() {
            System.out.println("PrintThread is running.......");
            while(!ready){
                //无限循环
                System.out.println("number = "+number);
            }

        }
    }

    public static void main(String[] args) {
        new PrintThread().start();
        SleepTools.second(1);
        number = 51;
        ready = true;
        SleepTools.second(5);
        System.out.println("main is ended!");
    }
}
