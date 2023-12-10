package com.java.spring.write.service;

import com.java.spring.write.config.ItBeanPostProcessor;
import com.java.spring.write.config.ItComponent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: AmazeCode
 * @date: 2023/12/6 21:07
 */
@ItComponent("summerBeanPostProcessor")
public class SummerBeanPostProcessor implements ItBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化处理前代理");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("itUserService")) {
            System.out.println("初始化处理后代理");
            Object proxyInstance = Proxy.newProxyInstance(SummerBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 执行原来bean的方法
                    return method.invoke(bean,args);
                }
            });
            // 返回代理对象
            return proxyInstance;
        }

        return bean;
    }
}
