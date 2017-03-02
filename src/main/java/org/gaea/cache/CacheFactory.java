package org.gaea.cache;

import org.gaea.exception.SysInitException;

/**
 * 缓存的工厂类。供子模块实现并托管给Spring容器。然后其他模块就可以用注入直接使用。
 * Created by iverson on 2016-11-3 14:14:26.
 */
public interface CacheFactory {
    /**
     * 获取相关的Cache操作类。负责放入内容等。
     * @return
     */
    public CacheOperator getCacheOperator() throws SysInitException;
}
