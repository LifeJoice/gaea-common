package org.gaea.util;

import org.apache.commons.lang3.StringUtils;
import org.gaea.exception.SysInitException;
import org.gaea.exception.ValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Web的系统通用类。放一些系统级的通用功能。
 * 这个依赖于Spring容器。如果有多个，可能会有问题。
 * Copy from gaea-web GaeaWebSystem.class by iverson on 2018年1月12日 星期五.
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        logger.debug("注入通用容器（供静态获取的）context name:{} context id:{} has parent:{}", ac.getApplicationName(), ac.getId(), (ac.getParent() == null));
        applicationContext = ac;
    }

    public static <T> T getBean(Class<T> aClass) throws SysInitException {
        if (applicationContext == null) {
            throw new SysInitException("系统初始化ApplicationContext失败。ApplicationContext=null。无法通过静态方法获取bean。");
        }
        return applicationContext.getBean(aClass);
    }

    public static Object getBean(String beanName) throws SysInitException, ValidationFailedException {
        if (applicationContext == null) {
            throw new SysInitException("系统初始化ApplicationContext失败。ApplicationContext=null。无法通过静态方法获取bean。");
        }
        if(StringUtils.isEmpty(beanName)){
            throw new ValidationFailedException("bean name为空，无法获取对应的bean。");
        }
        return applicationContext.getBean(beanName);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> aClass) throws SysInitException {
        if (applicationContext == null) {
            throw new SysInitException("系统初始化ApplicationContext失败。ApplicationContext=null。无法通过静态方法获取bean。");
        }
        return applicationContext.getBeansOfType(aClass);
    }
}
