package org.gaea.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * 用Jackson包把特定的map转换成json工具
 * Created by Iverson on 2015/7/2.
 */
public class GaeaJacksonUtils {
    private static final Logger logger = LoggerFactory.getLogger(GaeaJacksonUtils.class);
    // 这个应该是线程安全的
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String parse(Object inObj) throws IOException {
        if (inObj == null) {
            return null;
        }
        return objectMapper.writeValueAsString(inObj);
    }
}
