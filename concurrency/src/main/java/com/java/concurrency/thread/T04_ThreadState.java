package com.java.concurrency.thread;

/**
 * @description: 线程状态
 * @author: AmazeCode
 * @date: 2023/10/30 22:55
 */
public class T04_ThreadState {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        // 怎么样得到这个线程的状态呢？就是通过getState()这个方法
        System.out.println(t.getState());

        // 到这start完了之后呢是Runnable的状态
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 然后join之后,结束了是一个Timenated状态
        System.out.println(t.getState());
    }
}
