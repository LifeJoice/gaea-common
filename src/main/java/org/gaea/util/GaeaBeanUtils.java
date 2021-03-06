package org.gaea.util;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iverson on 2015/7/6.
 */
public class GaeaBeanUtils {

    public static Map<String, String> getPropNames(Class<?> beanClass) {
        Map<String, String> result = new HashMap<String, String>();
        PropertyDescriptor[] pds = org.springframework.beans.BeanUtils.getPropertyDescriptors(beanClass);
        for (PropertyDescriptor pd : pds) {
            result.put(pd.getName().toLowerCase(), pd.getName());
        }
        return result;
    }
}
