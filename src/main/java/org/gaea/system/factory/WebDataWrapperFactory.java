package org.gaea.system.factory;

import org.gaea.paging.PagingResultDataWrapper;

import java.util.List;

/**
 * 从Controller到页面的数据封装器的构造工厂。提供夸页面平台的数据构造。
 * <p>
 * 例如：<br/>
 * ExtJS的Grid分页需要的信息，和普通分页所需的信息不同。但数据(Entity)是一样的。所以需要一个统一的封装。
 * </p>
 * @author Iverson
 * 2014年8月22日 星期五
 */
public class WebDataWrapperFactory {
    
    private static String PAGING_UI_FRAMEWORK = "EXTJS3";
    private static String PAGING_UI_FRAMEWORK_EXTJS_3 = "EXTJS3";
    
    public static <T> PagingResultDataWrapper pagingData(List<T> beans){
        if(PAGING_UI_FRAMEWORK_EXTJS_3.equals(PAGING_UI_FRAMEWORK)){
            return new PagingResultDataWrapper<T>(beans);
        }
        return null;
    }
}
