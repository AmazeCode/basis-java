package com.java.concurrency.sync;

/**
 * @description: synchronized关键字对某个对象加锁
 * @author: AmazeCode
 * @date: 2023/11/1 22:24
 */
public class S01_T {

    private int count = 10;
    private Object o = new Object();

    public void m() {
        // 任何线程执行下面的代码，必须先拿到o的锁
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
