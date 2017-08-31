package org.gaea.common;

/**
 * Created by iverson on 2017/8/30.
 */
public interface CommonDefinition {

    /**
     * =============================================================================================================================================
     *                                                              日期、时间相关
     * =============================================================================================================================================
     */
    // 在日期转换中支持的格式（例如Excel导入转换日期列）。
    // 注意：这是日期转换支持的格式，不是gaea使用的标准格式!
    public static final String PROP_DATETIME_CONVERT_PATTERNS = "system.datetime.convert_support_patterns";
    // 默认使用的转换格式。从properties文件读不到就用这个
    public static final String[] DATETIME_CONVERT_PATTERNS = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss"};
}
