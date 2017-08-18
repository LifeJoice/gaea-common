package org.gaea.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.gaea.exception.ValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装统一的Bean处理。因为像Bean复制可能会比较影响性能，如果以后发现某种方式的复制不合适，方面整体替换。<p/>
 * 当前先基于Spring的BeanUtils处理。
 * Created by iverson on 2016/2/2.
 */
public class BeanUtils {
    private final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static Map<String, String> getPropNames(Class<?> beanClass) {
        Map<String, String> result = new HashMap<String, String>();
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(beanClass);
        for (PropertyDescriptor pd : pds) {
            result.put(pd.getName().toLowerCase(), pd.getName());
        }
        return result;
    }

    /**
     * 获取属性的值。没有对应的属性值会抛出异常。
     * @param target
     * @param propName
     * @return
     * @throws ValidationFailedException
     */
    public static Object getProperty(Object target, String propName) throws ValidationFailedException {
        if (StringUtils.isEmpty(propName)) {
            throw new ValidationFailedException("要获取的Bean的属性，传入的属性名不允许为空！");
        }
        Object propValue = null;
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
        try {
            propValue = wrapper.getPropertyValue(propName);
        } catch (NotReadablePropertyException e) {
            throw new ValidationFailedException("要读取的对象的属性不存在！property name: " + propName, e);
        }
        return propValue;
    }

    /**
     * 设定某个对象的某个属性的值。没有对应的属性值会抛出异常。
     * @param target
     * @param propName
     * @param propValue
     * @throws ValidationFailedException
     */
    public static void setProperty(Object target, String propName, Object propValue) throws ValidationFailedException {
        if (StringUtils.isEmpty(propName)) {
            throw new ValidationFailedException("要获取的Bean的属性，传入的属性名不允许为空！");
        }
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
        try {
            wrapper.setPropertyValue(propName, propValue);
        } catch (NotReadablePropertyException e) {
            throw new ValidationFailedException("要读取的对象的属性不存在！property name: " + propName, e);
        }
    }

    /**
     * 某个属性值是否可读。也可用于判断某个属性值是否存在。
     * @param target
     * @param propName
     * @return
     * @throws ValidationFailedException
     */
    public static boolean isReadableProperty(Object target, String propName) throws ValidationFailedException {
        if (StringUtils.isEmpty(propName)) {
            throw new ValidationFailedException("要获取的Bean的属性，传入的属性名不允许为空！");
        }
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
        return wrapper.isReadableProperty(propName);
    }
}
