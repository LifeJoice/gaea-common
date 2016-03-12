package org.gaea.util;

import org.apache.commons.lang3.StringUtils;
import org.gaea.exception.SysInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * properties文件的读取器。会把所有的配置文件都放进同一个map中。所以，相同的key会互相覆盖！<p/>
 * 基于UTF-8读取properties文件。单例模式，一旦初始化加载后就不会再刷新！
 * Created by iverson on 2016/3/9.
 */
public class GaeaPropertiesReader {
    private final Logger logger = LoggerFactory.getLogger(GaeaPropertiesReader.class);
    /**
     * 这个适合读取classpath等路径的文件。例如：classpath:xxx.properties
     * 不适合读取WEB-INF的文件，因为获取不到容器的上下文，无法定位(ServletContext)
     */
    private List<String> propLocations = null;
    /**
     * 基于Resource对象的读取器。这个可以用于读取WEB-INF下面的文件，只要注入一个ServletContextResource即可。
     * 因为读取WEB-INF下面的文档，需要ServletContext。如果不需要读取WEB-INF下面的，用上面的propLocations的即可。
     */
    private Resource propResource = null;
    private Map<String, String> propMap = null;

    public GaeaPropertiesReader(List<String> propLocations) {
        this.propLocations = propLocations;
    }

    public GaeaPropertiesReader(Resource propResource) {
        this.propResource = propResource;
    }

    /**
     * 构建对象后，读取相关的配置文件
     */
    @PostConstruct
    public void init() throws SysInitException {
        String logLocation = null;
        try {
            if (propLocations != null && propLocations.size() > 0) {
                // 遍历所有properties文件。文件地址可以加上协议类型，例如：classpath:之类的
                for (String location : propLocations) {
                    logLocation = location;
                    if (StringUtils.isNotEmpty(location)) {
                        ResourceLoader loader = new DefaultResourceLoader();
                        Resource resource = loader.getResource(location);
                        if (resource.exists()) {
                            Properties properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(resource, "UTF-8"));
                            // 把properties文件里的值，放到全局的map中缓存
                            initProperties(properties, location);
                        } else {
                            logger.warn("系统初始化，无法加载对应的配置文件。可能影响系统的运作。properties file location: " + location);
                        }
                    }
                }
            } else if (propResource != null) {
                if (propResource.exists()) {
                    Properties properties = PropertiesLoaderUtils.loadProperties(propResource);
                    // 把properties文件里的值，放到全局的map中缓存
                    initProperties(properties, propResource.getURL().toString());
                }
            }
        } catch (FileNotFoundException e) {
            throw new SysInitException("读取配置文件失败。file path：" + logLocation, e);
        } catch (IOException e) {
            throw new SysInitException("读取配置文件失败。file path：" + logLocation, e);
        }
    }

    private void initProperties(Properties properties, String logLocation) {
        // 遍历，把properties文件里的键值对都放进map。
        for (Object k : properties.keySet()) {
            String key = k.toString();
            String value = properties.get(k).toString();
            if (propMap == null) {
                propMap = new HashMap<String, String>();
            }
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            if (propMap.containsKey(key)) {
                logger.warn("初始化读取配置文件，发现重复值！key：" + key + " file path：" + logLocation + ". 值 '" + propMap.get(key) + "' 被值 '" + properties.get(k) + "' 覆盖！");
            }
            propMap.put(key, value);
        }
    }

    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        if (propMap != null) {
            if (!propMap.isEmpty()) {
                return propMap.get(key);
            }
        } else {
            logger.warn("本properties读取器未初始化成功。properties map为空！");
        }
        return "";
    }

    public List<String> getPropLocations() {
        return propLocations;
    }

    public void setPropLocations(List<String> propLocations) {
        this.propLocations = propLocations;
    }
}
