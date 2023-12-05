package com.java.spring.write.config;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 定义上下文容器，功能仿照Spring框架ApplicationContext
 * @author: AmazeCode
 * @date: 2023/12/5 22:21
 */
public class ItApplicationContext {

    private Class aClass;

    /*
        存放Bean定义
     */
    private ConcurrentHashMap<String,ItBeanDefinition> itBeanDefinitions = new ConcurrentHashMap<>();

    /**
     * @description: 构造方法
     * @param aClass
     * @return:
     * @author: AmazeCode
     * @date: 2023/12/5 22:23
     */
    public ItApplicationContext(Class aClass) {
        this.aClass = aClass;

        // 1 扫描路径下的bean，并保存bean定义
        scanBean(aClass);

        // 创建单例Bean
    }

    /**
     * @description: 扫描路径下Bean，保存bean定义
     * @param aClass 指定扫描路径类对象
     * @return: void
     * @author: AmazeCode
     * @date: 2023/12/5 23:22
     */
    private void scanBean(Class aClass) {
        // 1 解析@ItComponentScan拿到扫描路径
        ItComponentScan itComponentScan = (ItComponentScan) aClass.getDeclaredAnnotation(ItComponentScan.class);
        // 1.1 获取扫描路径
        String scanPath = itComponentScan.value();
        // 1.2 根据扫描路径得到包下所有的类，类加载器 BootStrap-->jre/lib EXT --> ext/lib App-->classpath
        ClassLoader classLoader = ItApplicationContext.class.getClassLoader();
        // 1.3 获取资源绝对路径 将com.java.spring.write.service转换成com/java/spring/write/service获取url
        URL url = classLoader.getResource(scanPath.replace(".", "/"));
        File file = new File(url.getFile());
        if (file.isDirectory()) {
            // 1.4 查找文件夹下的所有文件
            File[] files = file.listFiles();
            for (File f : files) {
                // 1.5 获取文件绝对路径 D:\IdeaProgram\basis-java\springmvc\target\classes\com\java\spring\write\service\ItUserServiceImpl.class
                String filePath = f.getAbsolutePath();
                // 1.6 .class结尾的文件才进行处理
                if (filePath.endsWith(".class")) {
                    // 字符串截取转换==》 com.java.spring.write.service.xxx
                    String subPath = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".clas"));
                    String className = subPath.replace("\\",".");
                    try {
                        // 加载类
                        Class<?> clazz = classLoader.loadClass(className);
                        // 1.7 是否所有的类都需要spring加载? 答案是否定的，只需要加载特定注解标注的比如@ItComponentScan
                        if (clazz.isAnnotationPresent(ItComponent.class)) {
                            // 1.8 注解标识当前类是一个Bean，注解标注是否一档注入呢？不一定，根据作用域，单例还是原型
                            ItComponent itComponent = clazz.getDeclaredAnnotation(ItComponent.class);
                            String beanName = itComponent.value();
                            // 1.9 封装bean定义，并且保存
                            ItBeanDefinition beanDefinition = new ItBeanDefinition();
                            beanDefinition.setClazz(clazz);
                            if (clazz.isAnnotationPresent(ItScope.class)){
                                ItScope itScope = clazz.getDeclaredAnnotation(ItScope.class);
                                String scope = itScope.value();
                                beanDefinition.setScope(scope);
                            }else{
                                beanDefinition.setScope("singleton");
                            }
                            itBeanDefinitions.put(beanName,beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
