package org.gaea.util.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.gaea.common.CommonDefinition;
import org.gaea.config.SystemProperties;
import org.gaea.util.GaeaDateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;

/**
 * bean属性是Date类型的值转换器。
 * Created by iverson on 2017/8/30.
 */
public class TimestampPropEditor extends PropertyEditorSupport {
    private static final Logger logger = LoggerFactory.getLogger(TimestampPropEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] convertPatterns = GaeaDateTimeUtils.getDefaultConvertPatterns(); // 默认的转换格式
        try {
            if(StringUtils.isNotEmpty(text)) {
                setValue(new Timestamp(DateUtils.parseDate(text, convertPatterns).getTime()));
            }
        } catch (ParseException e) {
            logger.error("转换bean的属性为日期类型Date出错！值："+text, e);
        }
    }
}
