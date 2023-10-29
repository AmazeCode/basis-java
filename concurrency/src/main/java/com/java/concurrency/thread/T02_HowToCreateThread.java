package com.java.concurrency.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @description: 如何创建线程
 * @author: AmazeCode
 * @date: 2023/10/29 20:08
 */
public class T02_HowToCreateThread {

    /**
     * @description: 1、方式一:继承Thread
     * @author: AmazeCode
     * @date: 2023/10/29 20:27
     */
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello World");
        }
    }

    /**
     * @description: 2、方式二：实现Runnable接口
     * @author: AmazeCode
     * @date: 2023/10/29 20:27
     */
    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    /**
     * @description: 3、方式二：实现Callable接口
     * @author: AmazeCode
     * @date: 2023/10/29 20:27
     */
    static class MyCall implements Callable<String> {
        @Override
        public String call() {
            System.out.println("Hello MyCall");
            return "success";
        }
    }


    /**
     * @param args
     * @description: 启动线程的5种方式
     * @return: void
     * @author: AmazeCode
     * @date: 2023/10/29 20:30
     */
    public static void main(String[] args) {
        // 方式一：继承Thread 直接start
        new MyThread().start();

        // 方式二：线程里面传递Runnable接口
        new Thread(new MyRun()).start();

        // 方式三：Lambda表达式
        new Thread(() -> {
            System.out.println("Hello Lambda!");
        }).start();

        // 方式四：Callable带返回值
        Thread t = new Thread(new FutureTask<String>(new MyCall()));
        t.start();

        // 方式五：线程池,生产环境不推荐这种方式
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            System.out.println("Hello ThreadPool");
        });
        service.shutdown();
    }
}
