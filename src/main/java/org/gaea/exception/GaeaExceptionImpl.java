package org.gaea.exception;

/**
 * 整合一些通用的功能或信息。
 * <p>用法：
 * 更多用在一般的异常，需要区分提示消息和错误消息的。提示消息可以提供给前端页面，错误消息可以给开发人员检查。
 * </p>
 * 如果是系统级异常，就不需要继承这个了，因为不存在把系统级给前台看的情况。
 * Created by iverson on 2016-7-13 09:28:25.
 */
public class GaeaExceptionImpl extends Exception implements GaeaException {

    protected String debugMessage;

    public GaeaExceptionImpl() {
    }

    public GaeaExceptionImpl(String message) {
        super(message);
    }

    public GaeaExceptionImpl(String message, Throwable cause) {
        super(message, cause);
    }

    public GaeaExceptionImpl(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

    public GaeaExceptionImpl(String message, Throwable cause, String debugMessage) {
        super(message, cause);
        this.debugMessage = debugMessage;
    }

    public GaeaExceptionImpl(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String debugMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.debugMessage = debugMessage;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
