package org.gaea.exception;

/**
 * 系统配置异常。更多可能是一般性的配置，例如：数据集配置有误; UI配置有误等.
 * Created by Iverson on 2016-8-7 17:56:58.
 */
public class SystemConfigException extends GaeaExceptionImpl {

    public SystemConfigException(String message) {
        super(message);
    }

    public SystemConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemConfigException(String message, String debugMessage) {
        super(message, debugMessage);
    }

    public SystemConfigException(String message, Throwable cause, String debugMessage) {
        super(message, cause, debugMessage);
    }
}
