package org.gaea.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gaea.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 把一个字符串JSONString转换为一个{@code List<SomeClass>}
     * @param jsonString       要被转换的json字符串
     * @param implListClass    真实new的List类。例如：ArrayList
     * @param genericClass     List中泛型对象T的Class。例如：DataItem.class
     * @param <T>              List中泛型对象T的Class。例如：DataItem.class
     * @return 例如{@code List<DateItem>}
     * @throws InvalidDataException
     */
    public static <T> List<T> parseList(String jsonString, Class implListClass, Class<T> genericClass) throws InvalidDataException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(implListClass, List.class, genericClass);
        try {
            List<T> parseList = objectMapper.readValue(jsonString, javaType);
            return parseList;
        } catch (IOException e) {
            throw new InvalidDataException("转换json字符串为List<SomeClass>失败！json：" + jsonString, e);
        }
    }
}
