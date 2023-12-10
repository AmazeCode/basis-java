package com.java.spring.write.config;

/**
 * @description: 后置处理器，AOP实现的关键
 * @author: AmazeCode
 * @time: 2023/12/6 21:04
 */
public interface ItBeanPostProcessor {

    /**
     * @description: 初始化前处理
     * @param bean
     * @param beanName
     * @return: java.lang.Object
     * @author: AmazeCode
     * @date: 2023/12/6 21:06
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * @description: 初始化后处理
     * @param bean
     * @param beanName
     * @return: java.lang.Object
     * @author: AmazeCode
     * @date: 2023/12/6 21:06
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}
