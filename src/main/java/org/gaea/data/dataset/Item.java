package org.gaea.data.dataset;

/**
 * 对DataSet的row某column值的统一定义。
 * 因为这个可能会跨多个包使用，例如：common-db, common-poi等都会用。但不适合让common-poi去依赖common-db。
 * Created by iverson on 2016-11-1 16:44:23.
 */
public interface Item {
    public static final String ITEM_VALUE_NAME = "value"; // 通用的DataSet的值(例如:1)的默认key
    public static final String ITEM_TEXT_NAME = "text"; // 通用的DataSet的值对应的文字描述(例如:可用)的默认key
    public String getValue();
    public String getText();
}
