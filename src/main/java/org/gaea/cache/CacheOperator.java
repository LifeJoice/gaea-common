package org.gaea.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作接口。定义操作缓存的一些通用方法。例如放入、获取等。
 * Created by iverson on 2016/11/3.
 */
public interface CacheOperator {
    public void put(String key, String value, String gaeaTimeOutStr);

    public <T> void put(final String key, final Map<String, T> map, Class<T> tClass);

    public <T> void putHashValue(String key, String mapKey, T value, Class<T> tClass);

    public <T> T getHashValue(String key, String hashKey, Class<T> tClass);

    public <T> void put(final String key, T[] setArray, Class<T> tClass);

    public <T> void put(String key, T value, Class<T> tClass, String gaeaTimeOutStr);
}
