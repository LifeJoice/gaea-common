package org.gaea.data.cache;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作接口。定义操作缓存的一些通用方法。例如放入、获取等。
 * Created by iverson on 2016/11/3.
 */
public interface CacheOperator {
    public void put(String key, String value, long timeOut, TimeUnit timeUnit);

    public <T> void put(final String key, final Map<String, T> map);

    public <T> void putHashValue(String key, String mapKey, T value);

    public <T> T getHashValue(String key, String hashKey);
}
