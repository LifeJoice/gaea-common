package org.gaea.exception;

/**
 * 处理失败异常。一般的业务处理失败。例如：导入的时候转换excel返回空但又没有异常之类的。
 * Created by iverson on 2016-6-8 11:11:06.
 */
public class ProcessFailedException extends GaeaExceptionImpl{

    public ProcessFailedException(String message) {
        super(message);
    }

    public ProcessFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
