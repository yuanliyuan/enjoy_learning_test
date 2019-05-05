package com.yl.fork_join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author yuanli
 * @create 2019-04-21 17:10
 **/
public class TestForkJoinPool {

    private static final Integer MAX = 500;

    static class MyForkJoinTask extends RecursiveTask<Integer>{

        //子任务开始计算
        private Integer startValue;

        private Integer endValue;

        MyForkJoinTask(Integer startValue, Integer endValue){
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        protected Integer compute() {

            if(endValue - startValue < MAX){
                System.out.println("开始计算的部分:startValue ="+ startValue + "endValue" + endValue);
                Integer totalValue = 0;
                for(int index = this.startValue; index <= this.endValue;index ++){
                    totalValue += index;
                }
                return totalValue;
            }else{
                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue,(startValue + endValue) /2);
                subTask1.fork();
                MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) /2 + 1, endValue);
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }
        }
    }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
      ForkJoinPool pool = new ForkJoinPool();
      ForkJoinTask<Integer> taskFutrue = pool.submit(new MyForkJoinTask(1,1001));
      Integer result = taskFutrue.get();
      System.out.println("result = " + result);
  }
}
