package com.tommy.concurrent;

public class MonitorTask {
    private Thread thread;
    // 开始监控
    public void start() {
        thread = new Thread(() -> {
            while (true) {
                Thread curr = thread.currentThread();
                // 如果当前线程是中断状态
                if(curr.isInterrupted()) {
                    System.out.println("清理资源，料理后事");
                    break;
                }
                try {
                    // 监控执行工作...
                    System.out.println("监控执行ing");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 此处中断位被清除，变为false, 重新改为true,进入循环，处理线程后事
                    curr.interrupt();
                }
            }
        }, "监控线程");
        thread.start();
    }
    // 终止线程
    public void stop() {
        thread.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        MonitorTask monitorTask = new MonitorTask();
        monitorTask.start();
        Thread.sleep(1500);
        monitorTask.stop();

    }
}