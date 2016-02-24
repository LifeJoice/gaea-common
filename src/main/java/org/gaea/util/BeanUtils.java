package org.gaea.util;

/**
 * 封装统一的Bean处理。因为像Bean复制可能会比较影响性能，如果以后发现某种方式的复制不合适，方面整体替换。<p/>
 * 当前先基于Spring的BeanUtils处理。
 * Created by iverson on 2016/2/2.
 */
public class BeanUtils {

    public static void copyProperties(Object source,Object target){
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
}
