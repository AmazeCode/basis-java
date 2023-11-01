package com.java.concurrency.sync;

/**
 * @description: synchronized关键字对this对象加锁
 * @author: AmazeCode
 * @date: 2023/11/1 22:27
 */
public class S02_T {

    private int count = 10;

    public void m () {
        // 该方式等同于方法上加锁
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
