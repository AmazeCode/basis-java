package com.java.concurrency.sync;

/**
 * @description:
 * @author: AmazeCode
 * @date: 2023/11/9 23:21
 */
public class S07_T {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start ......");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " m2 ");
    }

    public static void main(String[] args) {
        S07_T t = new S07_T();

        /*new Thread(() -> t.m1(),"t1").start();
        new Thread(() -> t.m2(),"t2").start();*/

        new Thread(t::m1,"t1").start();
        new Thread(t::m2,"t2").start();

        // 1.8之前的写法
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        });*/
    }
}
