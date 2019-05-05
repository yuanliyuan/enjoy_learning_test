package com.yl.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author yuanli
 * @create 2019-04-27 21:29
 **/
public class DbPool {

    //容器,存放连接
    private static LinkedList<Connection> pool = new LinkedList<>();

    public DbPool(int initialSize){
        if(initialSize > 0){
            for (int i = 0; i < initialSize; i++){
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    //释放连接,通知其他的等待连接的线程
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 获取连接
     * 在mills内无法获取连接,将会返回null
     * @param mills
     * @return
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
       //永不超时
        if(mills < 0){
            while (pool.isEmpty()){
                pool.wait();
            }
            return pool.removeFirst();
        }else{
            //超时时刻
            long future = System.currentTimeMillis()+mills;
            //等待时刻
            long remaining = future;
            while(pool.isEmpty() && remaining > 0){
                pool.wait(remaining);
                //唤醒一次,重新计算等待时长
                remaining = future - System.currentTimeMillis();
            }
            Connection connection = null;
            if(pool.isEmpty()){
                connection = pool.removeFirst();
            }
            return connection;
        }
    }
}
