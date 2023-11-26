package com.java.concurrency.basic;

import java.util.concurrent.CompletableFuture;

/**
 * @description: CompletableFuture 使用实例
 * @author: AmazeCode
 * @date: 2023/11/26 13:51
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {
        //syncBasic();
        syncMerge();
    }



    /**
     * @description: 异步基础实例
     * @param
     * @return: void
     * @author: AmazeCode
     * @date: 2023/11/26 14:00
     */
    private static void syncBasic() throws InterruptedException {
        // 1、创建异步执行任务
        CompletableFuture<Object> cf = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);
        // 2、如果执行成功
        cf.thenAccept((result) -> {
            System.out.println("price：" + result);
        });

        // 3、如果执行异常
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        Thread.sleep(200);
    }

    private static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed");
        }
        return 5 + Math.random() * 20;
    }

    /**
     * @description: 异步合并实例
     * @param
     * @return: void
     * @author: AmazeCode
     * @date: 2023/11/26 14:00
     */
    private static void syncMerge() throws InterruptedException {
        // 两个CompletableFuture执行异步查询
        CompletableFuture<Object> cfQuerySina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油","https://finance.sina.com.cn/code/");
        });

        CompletableFuture<Object> cfQueryFrom163  = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油","https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQuerySina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture
        CompletableFuture<Object> cfFetch  = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终执行结果
        cfFetch.thenAccept((result) -> {
            System.out.println("price：" + result);
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
