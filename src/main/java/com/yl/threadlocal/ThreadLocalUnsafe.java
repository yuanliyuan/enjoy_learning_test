package com.yl.threadlocal;

/**
 * @author yuanli
 * @create 2019-04-20 23:06
 **/
public class ThreadLocalUnsafe implements Runnable {

    private static   Number number;

    public static ThreadLocal<Number> value = new ThreadLocal<Number>(){};

    @Override
    public void run() {
        //每个线程计数加一
        System.out.println(Thread.currentThread().getName()+"start:"+number.getNum());
        number.setNum(number.getNum()+1);
        value.set(number);
        System.out.println(Thread.currentThread().getName()+"end:"+number.getNum());
      /*  try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println(Thread.currentThread().getName()+"="+value.get().getNum());
    }

  public static void main(String[] args) {
      number =  new Number(0);
      for (int i = 0; i < 5; i++) {
          new Thread(new ThreadLocalUnsafe()).start();
      }
  }

    private static class Number{
        private int num;

        Number(int num){
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
