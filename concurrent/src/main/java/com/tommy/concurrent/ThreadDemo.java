package com.tommy.concurrent;

public class ThreadDemo {

    public static Object object = new Object();

    public static void main(String[] args) {
        try {
            test1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程： " + Thread.currentThread().getName() + "正在执行");
                System.out.println("线程 " + Thread.currentThread().getName() + "即将wait()");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程 " + Thread.currentThread().getName() + "得到了锁");
        }
    }

    public static void test() {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        System.out.println(thread1.isInterrupted());
        thread1.interrupt();
        System.out.println(thread1.isInterrupted());

        try {
            thread1.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程： " + Thread.currentThread().getName() + "正在执行");
                object.notify();
                System.out.println("线程：" + Thread.currentThread().getName() + "调用了object.notify()方法");
            }
            System.out.println("线程： " + Thread.currentThread().getName() + "释放了锁");
        }
    }

    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        System.out.println(t1.isInterrupted());
        Thread.sleep(500);
        t1.interrupt();
        Thread.sleep(100);
        System.out.println(t1.isInterrupted());
    }
}
