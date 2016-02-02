package org.gaea.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的校验类
 * @author Iverson
 */
public class ValidationUtils {
    /**
     * 利用instanceof进行判断。非空且无法识别的对象会抛出IllegalArgumentException异常。
     * @param o
     * @return 
     */
    public static Boolean isBlank(Object o){
        // 这里需要提前判断，并和instanceof区分开（instanceof无法识别的会为false，但我们要抛出异常）。
        if(o==null){
            return true;
        }
        // 类型判断
        if((o instanceof String)||(o instanceof StringBuilder)||(o instanceof StringBuffer)){
            return "".equals(o.toString());
        }else if(o instanceof List){
            List list = (List) o;
            return list.isEmpty();
        }else{
            throw new IllegalArgumentException("无法识别的类型。无法进行是否为空判断。");
        }
    }
    
    public static void main(String[] args) {
        Object str = null;
        List list = new ArrayList();
        if(str instanceof String){
            System.out.println("------>>>是字符串噢");
        }else if(str instanceof List){
            System.out.println("------>>>是列表噢");
        }
        System.out.println("------->>>list is empty? "+list.isEmpty());
    }
}
