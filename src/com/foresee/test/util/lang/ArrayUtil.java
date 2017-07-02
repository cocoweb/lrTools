package com.foresee.test.util;


import java.util.ArrayList;
import java.util.List;
//************************************************************************
//【文件名】　　　　数组工具类

/**
 * <p>文件名：ArrayUtil.java</p>.
 * <p>说     明：数组常用方法实现</p>
 *
 * @author      yuegy
 * @version     2011-2-28
 */
//************************************************************************
//【作　成】 　　　　 www.sh-db.com.cn　　　　2011-2-28（R1.00）
//************************************************************************
public class ArrayUtil {

    /**
     * 泛型数组转为列表.
     * 
     * @param <T>
     * @param array
     * @return array = null 或 长度为0返回，长度为0的列表
     */
    public static <T> List<T> toList(T[] array) {
        List<T> rtn = new ArrayList<T>();
        if (array != null) {
            for (T t : array) {
                rtn.add(t);
            }
        }
        return rtn;
    }

    /**
     * Array is Null.
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isNull(T[] array) {
        return !isNotNull(array);
    }

    /**
     * Array is not Null.
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isNotNull(T[] array) {
        return array != null && array.length > 0;
    }

}