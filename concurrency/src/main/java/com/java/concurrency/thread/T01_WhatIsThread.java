package com.java.concurrency.thread;

import java.util.concurrent.TimeUnit;

/**
 * @description: 什么是线程？
 * @author: AmazeCode
 * @time: 2023/10/29 15:29
 */
public class T01_WhatIsThread {

    private static class T1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("T1");
            }
        }
    }

    public static void main(String[] args) {
        new T1().start();

        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("main");
        }
    }
}
