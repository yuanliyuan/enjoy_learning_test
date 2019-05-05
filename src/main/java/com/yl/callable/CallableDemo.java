package com.yl.callable;

import java.util.concurrent.*;

/**
 * @author yuanli
 * @create 2019-04-23 21:03
 **/
public class CallableDemo {

    static class SumTask implements Callable<Long>{

        @Override
        public Long call() throws Exception {
            long sum = 0;
            for(int i =0; i < 9000; i++){
                sum += i;
            }
            return sum;
        }
    }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //
      FutureTask<Long> futureTask = new FutureTask<Long>(new SumTask());
      Executor executor = Executors.newSingleThreadExecutor();
      executor.execute(futureTask);
      System.out.println(futureTask.get());
  }
}
