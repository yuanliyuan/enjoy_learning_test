package com.yl.threadlocal;

/**
 * @author yuanli
 * @create 2019-04-20 21:07
 **/
public class NoUserThreadLocal {

    private static  Integer i = new Integer(1);

    public static class TestThread implements  Runnable{
        int id;
        TestThread(int id){
            this.id = id;
        }
        @Override
        public void run() {
           System.out.println(Thread.currentThread().getName()+"start:" + i);
           i = i + id;
           System.out.println(Thread.currentThread().getName()+"end:"+i);
        }
    }

  public static void main(String[] args) {
        Thread[] runs = new Thread[3];

        for(int i = 0; i < runs.length; i++){
            runs[i] = new Thread(new TestThread(i));
        }
        for (int i = 0; i < runs.length; i++){
            runs[i].start();
        }

  }
}
