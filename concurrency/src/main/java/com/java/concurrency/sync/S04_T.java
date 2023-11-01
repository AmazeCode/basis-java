package com.java.concurrency.sync;

/**
 * @description: synchronized关键字对静态方法加锁
 * @author: AmazeCode
 * @date: 2023/11/1 22:29
 */
public class S04_T {

    private static int count = 10;

    public synchronized static void m () {// 这里等同于synchronized(S04_T.class),静态加锁锁的时类对象
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }

    public static void main(String[] args) {
        /*
            考虑下这里写synchronized (this) 是否可以？ 答案：不可以会报错
         */
        synchronized (S04_T.class) {
            count--;
        }
    }
}
