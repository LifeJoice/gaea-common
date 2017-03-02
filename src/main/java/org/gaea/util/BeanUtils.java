package org.gaea.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
