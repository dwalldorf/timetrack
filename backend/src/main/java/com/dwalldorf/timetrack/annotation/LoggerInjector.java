package com.dwalldorf.timetrack.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class LoggerInjector implements BeanPostProcessor {

    private final static Logger logger = LoggerFactory.getLogger(LoggerInjector.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        ReflectionUtils.doWithFields(beanClass, field -> {
            ReflectionUtils.makeAccessible(field);

            if (field.getAnnotation(Log.class) != null) {
                logger.info("creating logger for {}", beanClass.getSimpleName());
                field.set(bean, LoggerFactory.getLogger(beanClass));
            }
        });

        return bean;
    }
}