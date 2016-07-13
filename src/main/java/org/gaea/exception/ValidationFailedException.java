package org.gaea.exception;

/**
 * Created by Iverson on 2015/6/28.
 */
public class ValidationFailedException extends GaeaExceptionImpl {

    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationFailedException(String message, String debugMessage) {
        super(message, debugMessage);
    }

    public ValidationFailedException(String message, Throwable cause, String debugMessage) {
        super(message, cause, debugMessage);
    }
}
