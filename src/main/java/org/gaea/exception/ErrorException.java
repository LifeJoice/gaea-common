package org.gaea.exception;

/**
 * 用于Controller返回给前端的错误的异常。配合提供特定的http status，和让前端配合显示错误图标。
 * Created by Iverson on 2017年8月19日17:22:58
 */
public class ErrorException extends GaeaExceptionImpl {

    public ErrorException(String message) {
        super(message);
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorException(String message, String debugMessage) {
        super(message, debugMessage);
    }

    public ErrorException(String message, Throwable cause, String debugMessage) {
        super(message, cause, debugMessage);
    }
}
