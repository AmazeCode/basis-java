package com.java.spring.write.config;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 定义上下文容器，功能仿照Spring框架ApplicationContext
 * @author: AmazeCode
 * @date: 2023/12/5 22:21
 */
public class ItApplicationContext {

    private Class aClass;

    /*
        单例池
     */
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();
    /*
        存放Bean定义
     */
    private ConcurrentHashMap<String,ItBeanDefinition> itBeanDefinitions = new ConcurrentHashMap<>();
    /*
        存放后置处理器
     */
    private List<ItBeanPostProcessor> beanPostProcessors = new ArrayList<>();

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

        // 2、创建单例Bean
        for (Map.Entry<String, ItBeanDefinition> map : itBeanDefinitions.entrySet()) {
            String beanName = map.getKey();
            ItBeanDefinition itBeanDefinition = map.getValue();
            if (itBeanDefinition.getScope().equals("singleton")) {
                Object instance = createBean(beanName,itBeanDefinition);
                // 填充单例池
                singletonObjects.put(beanName,instance);
            }
        }
    }

    /**
     * @description: 创建Bean
     * @param beanName bean名称
     * @param itBeanDefinition bean定义
     * @return: java.lang.Object
     * @author: AmazeCode
     * @date: 2023/12/6 20:29
     */
    private Object createBean(String beanName, ItBeanDefinition itBeanDefinition) {
        Class clazz = itBeanDefinition.getClazz();

        try {
            // 1、实例化对象
            Object instance = clazz.newInstance();
            // 2、依赖注入 属性上存在@ItAutowired注解的属性需要被注入
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(ItAutowired.class)) {
                    // 获取属性名称，可通过属性名称获取Bean
                    String fieldName = declaredField.getName();
                    // 通过getBean获取需要的Bean，getBean中会进行单例判断，避免逻辑重复
                    Object bean = getBean(fieldName);
                    ItAutowired itAutowired = declaredField.getAnnotation(ItAutowired.class);
                    if (bean == null && itAutowired.required()) {
                        // Autowired真实逻辑
                        throw new RuntimeException();
                    }
                    // 允许属性赋值
                    declaredField.setAccessible(true);
                    // 给instance对象的declaredField属性赋值
                    declaredField.set(instance,bean);
                }
            }

            // 3、Aware回调
            if (instance instanceof ItBeanNameAware) {
                // 实现了ItBeanNameAware就去调用setBeanName方法,传入beanName
                ((ItBeanNameAware)instance).setBeanName(beanName);
            }

            //初始化前
            for (ItBeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 4、初始化
            if (instance instanceof ItInitializingBean) {
                ((ItInitializingBean)instance).afterPropertiesSet();
            }

            // 初始化后
            for (ItBeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description: 获取Bean对象
     * @param beanName Bean名称
     * @return: java.lang.Object
     * @author: AmazeCode
     * @date: 2023/12/6 20:41
     */
    public Object getBean(String beanName) {
        // 1、判断Bean是否在BeanDefinition中存在
        if (itBeanDefinitions.containsKey(beanName)) {
            // 获取bean定义
            ItBeanDefinition beanDefinition = itBeanDefinitions.get(beanName);
            // 2、判断是否是单例
            if (beanDefinition.getScope().equals("singleton")) {
                // 2.1 从单例池中获取
                return singletonObjects.get(beanName);
            }else {
                // 2.2 非单例bean,默认重新生成
                Object bean = createBean(beanName, beanDefinition);
                return bean;
            }
        }else {
            // 3、不存在Bean定义，直接抛出异常
            throw new RuntimeException();
        }
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
                            // 1.7.1 判断是否实现ItBeanPostProcessor接口,存放BeanPostProcessor
                            if (ItBeanPostProcessor.class.isAssignableFrom(clazz)) {
                                ItBeanPostProcessor beanPostProcessor = (ItBeanPostProcessor)clazz.getDeclaredConstructor().newInstance();
                                // 保存beanPostProcessor
                                beanPostProcessors.add(beanPostProcessor);
                            }

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
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
