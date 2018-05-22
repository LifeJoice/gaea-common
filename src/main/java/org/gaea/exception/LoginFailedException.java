package org.gaea.exception;

/**
 * 登录失败的异常
 * Created by Iverson on 2018-4-26 15:47:58
 */
public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
