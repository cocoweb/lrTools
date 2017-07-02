package com.foresee.test.util.none;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 代替webwork默认的时间转换 <br>
 * 要让这个类来代替webwork默认的转换你还需要在classpath目录, 一般就是classes目录放置配置文件
 * xwork-conversion.properties: <br>
 * 如果要只对单个action起作用, 在action同一个package写一个ActionClassName-conversion.properties:
 * 
 * @author javen
 * 
 */
@SuppressWarnings("unchecked")
public class DateConverter extends StrutsTypeConverter {

    private static final Logger log = Logger.getLogger(DateConverter.class);

    private final static String regex_time = "[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";

    private final static SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static String regex_time1 = "[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}";

    private final static SimpleDateFormat sdf_time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final static String regex_date = "[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}";

    private final static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");

    private final static String regex_date1 = "[0-9]{2,4}/[0-9]{1,2}/[0-9]{1,2}";

    private final static SimpleDateFormat sdf_date1 = new SimpleDateFormat("yyyy/MM/dd");

    private final static String regex_date2 = "[0-9]{2,4}.[0-9]{1,2}.[0-9]{1,2}";

    private final static SimpleDateFormat sdf_date2 = new SimpleDateFormat("yyyy.MM.dd");

    private final static String regex_time3 = "[0-9]{1,2}:[0-9]{1,2}";

    private final static SimpleDateFormat sdf_time3 = new SimpleDateFormat("HH:mm");

    // Thu Apr 7 11:16:10 UTC 2011
    private final static String regex_st = "[a-zA-Z]{1,3} [a-zA-Z]{1,3} [0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} [a-zA-Z]{1,3} [0-9]{2,4}";

    private final static SimpleDateFormat sdf_st = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

    // 2011-04-21 08:44:36 UTC
    private final static String regex_st2 = "[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} [a-zA-Z]{1,3}";

    private final static SimpleDateFormat sdf_st2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz", Locale.US);

    // 定义日期Converter的格式
    static {
        com.foresee.test.util.none.DateConverter dc = new com.foresee.test.util.none.DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss" });
        org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
    }

    // YYYY-MM-DD YYYY/MM/DD YYYY.MM.DD

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values.length == 1) {
            Date date = null;
            try {
                date = convertToDate(values[0]);
            } catch (ParseException e) {
                log.warn(e.getMessage());
            }
            log.debug("DateConverter1=" + date);
            return date;
        }

        Date[] results = new Date[values.length];

        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            try {
                results[i] = convertToDate(value);
            } catch (ParseException e) {
                log.warn(e.getMessage());
            }
        }

        return results;
    }

    public static Date convertToDate(String value) throws ParseException {
        if (value.matches(regex_time)) {
            return sdf_time.parse(value);
        } else if (value.matches(regex_date)) {
            return sdf_date.parse(value);
        } else if (value.matches(regex_date1)) {
            return sdf_date1.parse(value);
        } else if (value.matches(regex_date2)) {
            return sdf_date2.parse(value);
        } else if (value.matches(regex_time1)) {
            return sdf_time1.parse(value);
        } else if (value.matches(regex_time3)) {
            return sdf_time3.parse(value);
        } else if (value.matches(regex_st)) {
            return sdf_st.parse(value);
        } else if (value.matches(regex_st2)) {
            return sdf_st2.parse(value);
        }

        return null;
    }

    @Override
    public String convertToString(Map context, Object o) {
        if (o instanceof Date) {
            Date date = (Date) o;
            return sdf_time.format(date);
        }

        return o.toString();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("2011-04-06".matches(regex_date));
        System.out.println("2008-10-11 00:21:12".matches(regex_time));
        System.out.println("2008-10-11 00:21".matches(regex_time1));
        System.out.println("2008/10/11".matches(regex_date1));
        System.out.println("2008.10.11".matches(regex_date2));
        System.out.println("00:21".matches(regex_time3));
        System.out.println("Thu Apr 7 11:16:10 UTC 2011".matches(regex_st));
        System.out.println(convertToDate("Thu Apr 7 11:16:10 UTC 2011"));
        System.out.println(convertToDate("2011-04-21 08:44:36 UTC"));
    }
}
