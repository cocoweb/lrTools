package com.foresee.test.util;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import net.sf.json.JSONObject;
 
//************************************************************************
//【类　名】　　　　Object对象工具集合类
/**
* 对象操作方法实现.
*
* @author　　　 yuegy
* @version　　   2011-02-10
*/
//************************************************************************
//【作　成】 　　　　 www.sh-db.com.cn　　　　2011-02-10（R1.00）
//************************************************************************
public class ObjUtil implements Cloneable {
 
    /**
     * 判断对象是否为空.
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return null == obj;
    }
 
    /**
     * 判断对象是否为空.
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        return null != obj;
    }
 
    /**
     * 强制转化为字符串.
     * @param obj
     * @param defultVal
     * @return
     */
    public static String toStr(Object obj, String defultVal) {
        if (obj != null) {
            return String.valueOf(obj);
        }
        return defultVal;
    }
 
    /**
     * 强制转化为字符串.
     * @param obj
     * @return
     */
    public static String toStr(Object obj) {
        return toStr(obj, "");
    }
 
    /**
     * 强制转化为整形.
     * @param value
     * @param defaultValue
     * @return
     */
    public static int toInt(Object value, int defaultValue) {
        int val = defaultValue;
        try {
            val = Integer.parseInt(toStr(value, StringUtil.EMPTY));
        } catch (Exception e) {
        }
        return val;
    }
 
    /**
     * 强制转化为整形.
     * @param value
     * @return
     */
    public static int toInt(Object value) {
        return toInt(value, 0);
    }
 
    /**
     * 强制转化为Long.
     * @param value
     * @param defaultValue
     * @return
     */
    public static long toLong(Object value, long defaultValue) {
        long val = defaultValue;
        try {
            val = Long.parseLong(toStr(value, StringUtil.EMPTY));
        } catch (Exception e) {
        }
        return val;
    }
 
    /**
     * 强制转化为boolean.
     * @param value
     * @return
     */
    public static boolean toBoolean(Object value) {
        return toBoolean(value, false);
    }
 
    /**
     * 强制转化为boolean.
     * @param value
     * @param defaultValue
     * @return
     */
    public static boolean toBoolean(Object value, boolean defaultValue) {
        boolean val = defaultValue;
        try {
            val = Boolean.parseBoolean(toStr(value, StringUtil.EMPTY));
        } catch (Exception e) {
        }
        return val;
    }
 
    /**
     * 强制转化为Long.
     * @param value
     * @return
     */
    public static long toLong(Object value) {
        return toLong(value, 0);
    }
 
    /**
     * 强制转化为Double.
     * @param value
     * @param defaultValue
     * @return
     */
    public static Double toDouble(Object value, Double defaultValue) {
        Double val = defaultValue;
        try {
            val = Double.valueOf(toStr(value, StringUtil.EMPTY));
        } catch (Exception e) {
        }
        return val;
    }
 
    /**
     * 强制转化为Double.
     * @param value
     * @return
     */
    public static Double toDouble(Object value) {
        return toDouble(value, 0.0);
    }
 
    /**
     * 强制转化为BigDecimal.
     * @param value
     * @param defaultValue
     * @return
     */
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        BigDecimal val = defaultValue;
        try {
            val = new BigDecimal(toStr(value, StringUtil.EMPTY));
        } catch (Exception e) {
        }
        return val;
    }
 
    /**
     * 强制转化为BigDecimal.
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, new BigDecimal(0));
    }
 
    public static Date toDate(Object value) {
        Date retDate = null;
        String val = toStr(value);
        if (StringUtil.isEmpty( val )) {
            return null;
        }
 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            retDate = sdf.parse(val);
        } catch (ParseException e) {
 
        }
 
        return retDate;
    }
    /**
     * 克隆对象.
     * @param src
     * @return
     */
    public static Object cloneObject(Object src) {
        if (src == null) {
            return null;
        }
        JSONObject jsonobj = JSONObject.fromObject(src);
        Object dest = JSONObject.toBean(jsonobj, src.getClass());
        return dest;
    }
     
}