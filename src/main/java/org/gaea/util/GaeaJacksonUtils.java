package org.gaea.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.gaea.data.jackson.GaeaJacksonPropertyFilterMixIn;
import org.gaea.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
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
    private static ObjectMapper filterObjectMapper = new ObjectMapper();

    // init
    static {
        filterObjectMapper.addMixIn(
                Object.class, GaeaJacksonPropertyFilterMixIn.class);
        // 普通ObjectMapper初始化
        // 针对Long和BigDecimal的，转换为String。因为太长的大数，前端JavaScript会精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance); // ToStringSerializer线程安全
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    public static String parse(Object inObj) throws IOException {
        if (inObj == null) {
            return null;
        }
        return objectMapper.writeValueAsString(inObj);
    }

    /**
     * 把一个字符串JSONString转换为一个{@code List<SomeClass>}
     *
     * @param jsonString    要被转换的json字符串
     * @param implListClass 真实new的List类。例如：ArrayList
     * @param genericClass  List中泛型对象T的Class。例如：DataItem.class
     * @param <T>           List中泛型对象T的Class。例如：DataItem.class
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

    /**
     * 动态转换inObj为json string。
     * <p style='color:red'>
     *     对于大量、固定的要不展示给前端的字段，建议用DTO类处理。不要频繁直接用这个方法。
     * </p>
     *
     * @param inObj
     * @param ignoreProperties
     * @return
     * @throws IOException
     */
    public static String parse(Object inObj, String... ignoreProperties) throws IOException {
        if (ArrayUtils.isEmpty(ignoreProperties)) {
            return parse(inObj);
        }
        // 添加动态字段过滤器
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter
                .serializeAllExcept(ignoreProperties);
        // gaeaJacksonFilter对应GaeaJacksonPropertyFilterMixIn里的component name
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("gaeaJacksonFilter", theFilter);
        return filterObjectMapper.writer(filters).writeValueAsString(inObj);
    }
}
