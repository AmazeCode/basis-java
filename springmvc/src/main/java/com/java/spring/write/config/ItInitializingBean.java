package com.java.spring.write.config;

/**
 * @description: 初始化接口
 * @author: AmazeCode
 * @time: 2023/12/6 20:58
 */
public interface ItInitializingBean {

    /**
     * @description: 初始化执行方法
     * @param
     * @return: void
     * @author: AmazeCode
     * @date: 2023/12/6 20:59
     */
    void afterPropertiesSet();
}
