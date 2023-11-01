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
            考虑下这里写synchronized (this) 是否可以？ 答案：不可以会报错,原因在于static属于类，先于任何对象存在，this指向的是对象，
            也就是说this还无法指向对象引用，静态方法就已经存在了，在运行静态方法时，无法获取到当前对象引用，从而导致出现异常。所以在静态方法中无法使用this关键字
         */
        synchronized (S04_T.class) {
            count--;
        }
    }
}
