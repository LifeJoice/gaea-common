package org.gaea.exception;

/**
 * 统一所有Gaea相关的异常体系。
 * Created by iverson on 2016-6-8 11:07:25.
 */
public interface GaeaException {
    public static final int DEFAULT_FAIL = 600; // 默认失败的状态码。可以用于HTTP STATUS。
}
