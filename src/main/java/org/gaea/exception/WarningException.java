package org.gaea.exception;

/**
 * 用于Controller返回给前端的警告的异常。配合提供特定的http status，和让前端配合显示警告图标。
 * Created by Iverson on 2017年8月19日17:22:58
 */
public class WarningException extends GaeaExceptionImpl {

    public WarningException(String message) {
        super(message);
    }

    public WarningException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarningException(String message, String debugMessage) {
        super(message, debugMessage);
    }

    public WarningException(String message, Throwable cause, String debugMessage) {
        super(message, cause, debugMessage);
    }
}
