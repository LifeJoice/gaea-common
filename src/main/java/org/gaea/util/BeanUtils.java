package org.gaea.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.gaea.exception.ValidationFailedException;
import org.gaea.util.bean.DatePropEditor;
import org.gaea.util.bean.TimestampPropEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.sql.Timestamp;

/**
 * 封装统一的Bean处理。因为像Bean复制可能会比较影响性能，如果以后发现某种方式的复制不合适，方面整体替换。<p/>
 * 当前先基于Spring的BeanUtils处理。
 * Created by iverson on 2016/2/2.
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 把map的值写入对象中。
     * @param map
     * @param target
     */
    public static void setValues(Map map, Object target, String... ignoreProperties) {
        try {
            // 借用spring的getPropertyDescriptors，因为貌似Spring会做Java class的properties缓存。
            // spring的Bean copy也是用的Method，所以这个就不需要用spring了。
            PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(target.getClass());
            List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

            for (PropertyDescriptor targetPd : propertyDescriptors) {
                String key = targetPd.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method writeMethod = targetPd.getWriteMethod();

                    if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                        writeMethod.invoke(target, value);
                    }
                }
            }
        } catch (InvocationTargetException e) {
            logger.error("尝试把Map的值写入bean失败！Map: " + map.toString(), e);
        } catch (IllegalAccessException e) {
            logger.error("尝试把Map的值写入bean失败！Map: " + map.toString(), e);
        }
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
     *
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
     *
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
     *
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

    /**
     * 负责把dataList转换为bean list。
     * <p>因为在转换过程中，有很多特殊类型，例如Date的从字符到Date、Timestamp等，是需要人工介入的。</p>
     * <p>（虽然当前不支持）还有像String=1.0转换成int的，没有特殊转换器会出错。</p>
     *
     * @param dataList
     * @param beanClass
     * @param <T>
     * @return
     * @throws ValidationFailedException
     */
    public static <T> List<T> getData(List<Map<String, String>> dataList, Class<T> beanClass) throws ValidationFailedException {
        if (dataList == null || beanClass == null) {
            return null;
        }
        List<T> results = new ArrayList<T>();
        boolean hasDate = false;
        // 属性一次读出，不要每次循环构建
        PropertyDescriptor[] pds = org.springframework.beans.BeanUtils.getPropertyDescriptors(beanClass);
        DatePropEditor datePropEditor = new DatePropEditor(); // 日期类型的转换器
        TimestampPropEditor timestampPropEditor = new TimestampPropEditor(); // Timestamp类型的转换器

        // 一次性判断bean是否含有某些需要转换的类型，例如：Date、Timestamp等
        for (PropertyDescriptor pd : pds) {
            if (Date.class.isAssignableFrom(pd.getPropertyType())) {
                hasDate = true;
//            } else if (pd.getPropertyType().isAssignableFrom(Timestamp.class)) { // 这个不行，区分不出Date和TimeStamp类型
//                hasTimestamp = true;
            } else if (pd.getPropertyType().isAssignableFrom(java.sql.Date.class)) {
                throw new ValidationFailedException("不支持java.sql.Date类型的转换。建议使用java.util.Date.");
            }
        }
        // 数据转换bean
        for (int i = 0; dataList != null && i < dataList.size(); i++) {
            T bean = org.springframework.beans.BeanUtils.instantiate(beanClass);
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
            // 注册特定属性类型的转换器, 否则转换不了就报错
            if (hasDate) {
                wrapper.registerCustomEditor(Date.class, datePropEditor);
                // 不需要分开考虑Date和TimeStamp了，感觉不好区分，一起注册得了
                wrapper.registerCustomEditor(Timestamp.class, timestampPropEditor);
            }
            wrapper.setAutoGrowNestedPaths(true);
            Map<String, ? extends Object> dataMap = dataList.get(i);
            wrapper.setPropertyValues(dataMap);
            results.add(bean);
        }
        return results;
    }
}
