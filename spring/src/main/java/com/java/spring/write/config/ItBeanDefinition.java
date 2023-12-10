package com.java.spring.write.config;

/**
 * @description: Bean定义实体
 * @author: AmazeCode
 * @date: 2023/12/5 23:11
 */
public class ItBeanDefinition {

    /**
     * 存放scope范围
     */
    private String scope;

    /**
     * bean对象的Class
     */
    private Class clazz;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
