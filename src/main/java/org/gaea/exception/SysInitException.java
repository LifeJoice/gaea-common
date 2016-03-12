package org.gaea.exception;

/**
 * 系统初始化失败的异常。
 * 2016-3-9 19:01:00 by Iverson
 */
public class SysInitException extends Exception {

    public SysInitException(String message) {
        super(message);
    }

    public SysInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
