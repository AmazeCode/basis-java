package com.java.concurrency.sync;

/**
 * @description: synchronized关键字对方法加锁
 * @author: AmazeCode
 * @date: 2023/11/1 22:29
 */
public class S03_T {

    private int count = 10;

    public synchronized void m () {// 该方式等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }
}
