package com.java.spring.write;

import com.java.spring.write.config.ItApplicationContext;
import com.java.spring.write.service.ItUserService;

/**
 * @description: 手写Spring验证
 * @author: AmazeCode
 * @date: 2023/12/5 22:19
 */
public class WriteSpringTest {

    /*
        手写实现spring框架，Bean的创建过程，包括依赖注入和AOP
     */
    public static void main(String[] args) {
        ItApplicationContext itApplicationContext = new ItApplicationContext(AppConfig.class);
        ItUserService userService = (ItUserService)itApplicationContext.getBean("itUserService");
        userService.test();
    }
}
