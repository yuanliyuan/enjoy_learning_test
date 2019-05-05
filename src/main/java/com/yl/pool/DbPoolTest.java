package com.yl.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuanli
 * @create 2019-04-27 21:51
 */
public class DbPoolTest {

  static DbPool pool = new DbPool(10);
  // 控制器:控制main线程将会等待所有work结束后才能继续执行
  static CountDownLatch end;

  public static void main(String[] args) throws InterruptedException {
    //线程数量
    int threadCount = 50;
    end = new CountDownLatch(threadCount);
    //每个线程的操作次数
    int count = 20;
    //计数器,统计可以拿到的线程
    AtomicInteger got = new AtomicInteger();
    //计数器,统计没有拿到的线程
    AtomicInteger notGot = new AtomicInteger();
    for (int i = 0; i < threadCount; i++) {
      Thread thread = new Thread(new Worker(count, got, notGot), "worker_" + i);
      thread.start();
    }
    end.await();//main线程在此处等待
    System.out.println("总共尝试连接:" + (threadCount * count));
    System.out.println("拿到的连接次数" + got);
    System.out.println("没能拿到的次数" + notGot);
  }

  static class Worker implements Runnable {

    int cout;
    AtomicInteger got;
    AtomicInteger notGot;

    public Worker(int cout, AtomicInteger got, AtomicInteger notGot) {
      this.cout = cout;
      this.got = got;
      this.notGot = notGot;
    }

    @Override
    public void run() {
      while (cout > 0) {
        try {
          // 从线程池中获取连接,如果100ms内无法获取到,将会返回null
          // 分别统计获取的数量got和未获取的数量notGot
          Connection connection = pool.fetchConnection(1000);
          if (connection != null) {
            try {
              connection.createStatement();
              connection.commit();
            } finally {
              pool.releaseConnection(connection);
              got.incrementAndGet();
            }
          } else {
            notGot.incrementAndGet();
            System.out.println(Thread.currentThread().getName() + "等待时长");
          }
        } catch (Exception e) {

        } finally {
          cout--;
        }
      }
      end.countDown();
    }
  }
}
