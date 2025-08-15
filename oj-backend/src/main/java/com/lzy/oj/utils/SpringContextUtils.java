package com.lzy.oj.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文获取工具
 *

 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    // 定义一个静态变量，用于存储 Spring 上下文
    private static ApplicationContext applicationContext;

    // 实现 ApplicationContextAware 接口，重写 setApplicationContext 方法，将传入的 Spring 上下文赋值给静态变量
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 通过名称获取 Bean
     *
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        // 从 Spring 上下文中获取指定名称的 Bean
        return applicationContext.getBean(beanName);
    }

    /**
     * 通过 class 获取 Bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        // 从 Spring 上下文中获取指定类型的 Bean
        return applicationContext.getBean(beanClass);
    }

    /**
     * 通过名称和类型获取 Bean
     *
     * @param beanName
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        // 从 Spring 上下文中获取指定名称和类型的 Bean
        return applicationContext.getBean(beanName, beanClass);
    }
}