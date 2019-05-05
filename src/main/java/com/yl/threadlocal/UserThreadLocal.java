package com.yl.threadlocal;

/**
 * @author yuanli
 * @create 2019-04-20 20:58
 **/
public class UserThreadLocal {

    private static ThreadLocal<Integer> intLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

  public static void main(String[] args) {
    Thread[] runs = new Thread[3];
    for (int i = 0; i < runs.length; i++){
        runs[i] = new Thread(new TestThread(i));
    }
    for(int i = 0; i < runs.length; i++){
        runs[i].start();
    }
  }

    public static class TestThread implements  Runnable{
        int id;
        TestThread(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"start");
            Integer s = intLocal.get();
            s += id;
            intLocal.set(s);
            System.out.println(Thread.currentThread().getName()+":" + intLocal.get());
        }
    }
}
