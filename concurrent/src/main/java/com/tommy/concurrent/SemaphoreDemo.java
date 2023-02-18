package com.tommy.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("成功获取令牌" +Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("释放令牌"+Thread.currentThread().getName());
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();
    }
}
