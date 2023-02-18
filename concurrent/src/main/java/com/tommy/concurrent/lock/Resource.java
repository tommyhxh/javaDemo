package com.tommy.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {
    private boolean flag = false;
    private String name;
    private int count;
    //资源锁
    Lock lock = new ReentrantLock();
    //监视器类
    Condition produce_con = lock.newCondition();
    Condition customer_con = lock.newCondition();

    public void set(String name) {
        //获取锁
        lock.lock();
        try {
            while (flag){

            }
                //生产者线程等待
                try {
                    produce_con.await();
                } catch (Exception e) {
                }
            //直接唤醒消费者线程
            customer_con.signal();
            flag = true;
            count++;
            this.name = name;
            System.out.println(this.name + " " + this.count);
        } finally {
            //释放锁，不管try中代码是否报错，记得释放锁。
            lock.unlock();
        }
    }


    public void get() {
        //获取锁
        lock.lock();
        try {
            while (!flag){

            }
                //消费者线程等待
                try {
                    customer_con.await();
                } catch (Exception e) {
                }
            //直接唤醒生产者线程
            produce_con.signal();
            flag = false;
            System.out.println(this.name + " customer" + this.count);
        } finally {
            //释放锁资源
            lock.unlock();
        }

    }

}
