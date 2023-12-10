package com.java.spring.write.config;



/**
 * @description: BeanNameAware回调
 * @author: AmazeCode
 * @time: 2023/12/6 20:50
 */
public interface ItBeanNameAware {

    /**
     * @description: 设置Bean名称
     * @param name bean名称
     * @return: void
     * @author: AmazeCode
     * @date: 2023/12/6 20:51
     */
    void setBeanName(String name);
}
