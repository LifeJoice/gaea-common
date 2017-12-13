package org.gaea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Created by iverson on 2017年12月12日 星期二
 */
public class GaeaCollectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(GaeaDataUtils.class);

    /**
     * 把一个普通的map转换为Spring的MultiValueMap。这个在Spring的RestTemplate等地方有用。
     * 因为spring的get需要用MultiValueMap构建URI，而POST请求一般可以不用。
     * @param inMap
     * @return
     */
    public static MultiValueMap convert(Map inMap) {
        if (inMap == null) {
            return null;
        }
        MultiValueMap result = new LinkedMultiValueMap();
        for(Object key: inMap.keySet()){
            result.add(key, inMap.get(key));
        }
        return result;
    }
}
