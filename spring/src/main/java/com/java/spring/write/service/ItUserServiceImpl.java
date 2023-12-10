package com.java.spring.write.service;

import com.java.spring.write.config.ItAutowired;
import com.java.spring.write.config.ItBeanNameAware;
import com.java.spring.write.config.ItComponent;
import com.java.spring.write.config.ItInitializingBean;

/**
 * @description:
 * @author: AmazeCode
 * @date: 2023/12/5 22:30
 */
@ItComponent("itUserService")
//@ItScope("prototype")
public class ItUserServiceImpl implements ItUserService, ItBeanNameAware, ItInitializingBean {

    private String itBeanName;

    private String init;

    @ItAutowired
    private ItOrderService itOrderService;

    @Override
    public void setBeanName(String name) {
        itBeanName = name;
    }

    @Override
    public void afterPropertiesSet() {
        init = "初始化。。。。";
        System.out.println("初始化-----");
    }

    @Override
    public void test() {
        System.out.println(itBeanName);
        //System.out.println(init);
    }
}
