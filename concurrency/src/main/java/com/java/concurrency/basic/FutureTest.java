package com.java.concurrency.basic;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @description: 获取线程返回值的线程
 * @author: AmazeCode
 * @date: 2023/11/26 13:46
 */
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        // 1、定义任务
        Callable<String> task = new Task();
        // 2、提交任务并获得Future:
        Future<String> future = executor.submit(task);
        // 3、从Future获取异步执行返回的结果
        String result = future.get(); // 可能阻塞
        System.out.println(result);
    }


    /**
     * 定义实现Callable类
     */
    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            return longTimeCalculation();
        }

        private String longTimeCalculation() {
            return LocalDateTime.now().toString();
        }
    }
}
