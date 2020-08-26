package com.redbyte.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * get bean
     */
    public static <T> T getBean(String name) {
        return (T) beanFactory.getBean(name);
    }
}
