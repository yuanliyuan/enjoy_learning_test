package com.yl.syn;

/**
 * @author yuanli
 * @create 2019-04-20 20:19
 **/
public class IntegerSynTest {


  public static void main(String[] args) {
      Worker worker =new Worker(1);
      for(int j = 0; j <= 5; j++){
        new Thread(worker).start();
      }
  }

  public static class Worker implements  Runnable{

      private Integer i;

      Worker(int i){
          this.i = i;
      }
      @Override
      public void run() {
          synchronized (i){
              System.out.println("---" + i);
              i++;
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  }
}
