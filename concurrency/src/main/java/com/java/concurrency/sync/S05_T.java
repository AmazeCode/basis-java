package com.java.concurrency.sync;

/**
 * @description: 分析一下这个程序的输出
 * @author: AmazeCode
 * @date: 2023/11/9 23:03
 */
public class S05_T implements Runnable{

    private /*volatile*/ int count = 100;

    /**
     * @description: 使用同步能够保证线程和count数一致，加synchronized之后就没必要再加volatile了，因为synchronized既保证了原子性又保证了可见性
     * @param
     * @return: void
     * @author: AmazeCode
     * @date: 2023/11/9 23:18
     */
    public /*synchronized*/ void run(){
        count--;
        System.out.println(Thread.currentThread().getName() + " count =" + count);
    }

    public static void main(String[] args) {
        S05_T t = new S05_T();
        for (int i = 0; i < 100; i++) {
            new Thread(t,"THREAD" + i).start();
        }
    }
}
