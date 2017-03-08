package org.gaea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 和exception相关的一些通用方法。例如：生成错误的json等。
 * Created by iverson on 2017年3月2日11:36:06
 */
public class GaeaExceptionUtils {
    private final Logger logger = LoggerFactory.getLogger(GaeaExceptionUtils.class);

    /**
     * 把exception错误生成json格式。格式内容在本方法内hard code了。
     * @param exCode
     * @param debugMessage
     * @param message
     * @return
     * @throws IOException
     */
    public static String getJsonMessage(Object exCode, String debugMessage, String message) throws IOException {
        Map<String,Object> exception = new HashMap<String, Object>();
        exception.put("code", exCode);
        exception.put("message",message);
        exception.put("debugMessage",debugMessage);
        return GaeaJacksonUtils.parse(exception);
    }
}
