package org.gaea.exception;

/**
 * 普通的数据错误异常类。例如：传入的金额有非数字之类的。
 * @author Iverson
 * 2014-5-6 星期二
 */
public class InvalidDataException extends GaeaExceptionImpl{

    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(String message, String debugMessage) {
        super(message, debugMessage);
    }

    public InvalidDataException(String message, Throwable cause, String debugMessage) {
        super(message, cause, debugMessage);
    }
}
