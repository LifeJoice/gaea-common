package org.gaea.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用的日期时间处理类。
 * Rename by Iverson 2016-5-30 14:04:58
 *
 * @author Iverson 2014年7月15日
 */
public class GaeaDateTimeUtils {

    private static final GaeaDateTimeUtils self = new GaeaDateTimeUtils();

    private static final String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String GAEA_TIME_SUFFIX_MILLISECOND = "ms";
    public static final String GAEA_TIME_SUFFIX_SECOND = "s";
    public static final String GAEA_TIME_SUFFIX_MIN = "min";
    public static final String GAEA_TIME_SUFFIX_DAY = "d";

    public static GaeaDateTimeUtils getInstance() {
        return self;
    }

    public String getYYYYMMFirstTime(Date d) throws ParseException {
        Date date = DateUtils.truncate(d, Calendar.MONTH);
        FastDateFormat yMdHms = FastDateFormat.getInstance("");
        return yMdHms.format(date);
    }

    /**
     * 把字符串的年月（例如：2014-07）日期转换为精度到秒的当月最早一刻（2014-07-01 00:00:00）
     *
     * @param yyyyMM
     * @return
     * @throws ParseException
     */
    public String getYYYYMMFirstTime(String yyyyMM) throws ParseException {
        FastDateFormat yM = FastDateFormat.getInstance("yyyy-MM");
        FastDateFormat yMdHms = FastDateFormat.getInstance(FULL_DATE_TIME);
        return yMdHms.format(yM.parse(yyyyMM));
    }

    /**
     * 把字符串的年月（例如：2014-07）日期转换为精度到秒的当月最早一刻（2014-07-01 00:00:00）
     *
     * @param yyyyMM
     * @return
     * @throws ParseException
     */
    public Date getMonthBeginDateTime(String yyyyMM) throws ParseException {
        return DateUtils.parseDate(getYYYYMMFirstTime(yyyyMM), FULL_DATE_TIME);
    }

    /**
     * 把字符串的年月（例如：2014-07）日期转换为精度到秒的当月最后一刻（2014-07-31 23:59:59）
     *
     * @param yyyyMM 2014-07。注意'-'不可以少
     * @return
     * @throws ParseException
     */
    public String getYYYYMMLastTime(String yyyyMM) throws ParseException {
        Date d1 = FastDateFormat.getInstance("yyyy-MM").parse(yyyyMM);
        FastDateFormat yMdHms = FastDateFormat.getInstance(FULL_DATE_TIME);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.MONTH, 1);         // 加一个月
        cal.add(Calendar.SECOND, -1);       // 减一秒，就是上个月最后一秒了。
        return yMdHms.format(cal);
    }

    /**
     * 把字符串的年月（例如：2014-07）日期转换为精度到秒的当月最后一刻（2014-07-31 23:59:59）
     *
     * @param yyyyMM 2014-07。注意'-'不可以少
     * @return
     * @throws ParseException
     */
    public Date getMonthEndDateTime(String yyyyMM) throws ParseException {
        return DateUtils.parseDate(getYYYYMMLastTime(yyyyMM), FULL_DATE_TIME);
    }

    public String getYYYYMMLastTime(Date d) throws ParseException {
        FastDateFormat yM = FastDateFormat.getInstance("yyyy-MM");
        FastDateFormat yMdHms = FastDateFormat.getInstance(FULL_DATE_TIME);
        // 截取日期到月
        Date date = DateUtils.truncate(d, Calendar.MONTH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);         // 加一个月
        cal.add(Calendar.SECOND, -1);       // 减一秒，就是上个月最后一秒了。
        return yMdHms.format(cal);
    }

    /**
     * 比较两个日期，哪个更大。日期格式：yyyy-MM-dd HH:mm:ss
     * date1 > date2, return true
     *
     * @param strDate1
     * @param strDate2
     * @return date1>date2
     * @throws ParseException
     */
    public boolean isLarger(String strDate1, String strDate2) throws ParseException {
        if (StringUtils.isEmpty(strDate1) || StringUtils.isEmpty(strDate2)) {
            throw new IllegalArgumentException("输入参数不允许为空！");
        }
        FastDateFormat yMdHms = FastDateFormat.getInstance(FULL_DATE_TIME);
        Date date1 = yMdHms.parse(strDate1);
        Date date2 = yMdHms.parse(strDate2);
        return date1.getTime() > date2.getTime();
    }

    /**
     * 接受Gaea系统特有的时间描述符，转换成毫秒。
     *
     * @param gaeaStrTime 例如：6秒 = 6s，6分钟 = 6m等
     * @return
     */
    public static long getTimeMillis(String gaeaStrTime) {
        if (StringUtils.isEmpty(gaeaStrTime)) {
            throw new IllegalArgumentException("要转换的时间不允许为空！");
        }
        long result = 0;
        if (StringUtils.endsWith(gaeaStrTime, GAEA_TIME_SUFFIX_MILLISECOND)) {
            result = getGaeaTime(gaeaStrTime, GAEA_TIME_SUFFIX_MILLISECOND);
        } else if (StringUtils.endsWith(gaeaStrTime, GAEA_TIME_SUFFIX_MIN)) {
            long num = getGaeaTime(gaeaStrTime, GAEA_TIME_SUFFIX_MIN);
            result = num * 60 * 1000;
        } else if (StringUtils.endsWith(gaeaStrTime, GAEA_TIME_SUFFIX_SECOND)) { // 单个字母的放最后解析，以免误解。
            long num = getGaeaTime(gaeaStrTime, GAEA_TIME_SUFFIX_SECOND);
            result = num * 1000;
        } else if (StringUtils.endsWith(gaeaStrTime, GAEA_TIME_SUFFIX_DAY)) {
            long num = getGaeaTime(gaeaStrTime, GAEA_TIME_SUFFIX_DAY);
            result = num * 24 * 60 * 60 * 1000;
        } else {
            // 如果传入的是纯数值，转换后返回！
            if (StringUtils.isNumeric(gaeaStrTime)) {
                return Long.parseLong(gaeaStrTime);
            }
        }
        return result;
    }

    private static long getGaeaTime(String gaeaStrTime, String suffix) {
        long result = 0;
        if (StringUtils.endsWith(gaeaStrTime, suffix)) {
            String time = StringUtils.removeEnd(gaeaStrTime, suffix);
            if (!StringUtils.isNumeric(time)) {
                throw new IllegalArgumentException("要转换的时间格式不正确！time = " + gaeaStrTime);
            }
            result = Long.parseLong(time);
            return result;
        }
        return 0;
    }
}
