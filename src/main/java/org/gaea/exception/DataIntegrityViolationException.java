package org.gaea.exception;

/**
 * 这是一个针对违反数据完整性约束错误的异常类。
 * 拓展开来，可以把数据逻辑性的错误也归到使用这个异常。例如：数据错误等。
 * <p>
 * 注意：<br/>
 * 是针对数据库里数据的逻辑错误，例如：金额字段中有非数值之类的。</p>
 * @author Iverson
 * 2014-5-6 星期二
 */
public class DataIntegrityViolationException extends Exception implements GaeaException{

    public DataIntegrityViolationException() {
    }

    public DataIntegrityViolationException(String message) {
        super("违反数据完整性错误！\n"+message);
    }

    public DataIntegrityViolationException(String message, Throwable cause) {
        super("违反数据完整性错误！\n"+message, cause);
    }
    
}
