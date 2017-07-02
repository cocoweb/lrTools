package com.foresee.test.util.lang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//************************************************************************
//【文件名】　　　　数组工具类

import java.util.Random;

import com.foresee.test.util.CommonUtil;

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
	public static boolean isEmpty(String[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return true;
		}
		return false;
	}

	public static String assembleArray(String[] strArray, boolean fixedSeparator) {

		if (strArray.length == 0) {
			return null;
		}

		String retStr = strArray[0];

		for (int i = 1; i < strArray.length; i++) {
			String str = strArray[i];

			if (fixedSeparator) {
				retStr = retStr + "|";
			} else {
				if (!CommonUtil.isEmpty(retStr) && !CommonUtil.isEmpty(str)) {
					retStr = retStr + "|";
				}
			}

			retStr = retStr + str;
		}

		return retStr;
	}



	public static <T extends Enum<T>> Object[][] enumToArray(T[] t) {
		int length = t.length;
		Object[][] retObj = new Object[length][1];

		for (int i = 0; i < length; i++) {
			retObj[i][0] = t[i].toString();
		}

		return retObj;
	}

	public static <T extends Enum<T>> T searchEnum(T[] enumValues, String value) {
		T ret = null;
		if (null == enumValues)
			return ret;

		for (T t : enumValues) {
			if (value.equals(t.toString())) {
				return t;
			}
		}
		return ret;
	}
	
	public static <T> T getRandomInArray(T[] array){
		int length = array.length;
		Random random = new Random();
		int index = random.nextInt(length);
		return array[index];
	}

    public static Object[][] arrayToDataProvider(String[] array) {
    	int length = array.length;
    	Object[][] retObj = new Object[length][1];
    
    	for (int i = 0; i < length; i++) {
    		retObj[i][0] = array[i];
    	}
    
    	return retObj;
    }

    public static <T> Object[][] combineData(Object[][] data, T[] array) {
    	List<Object[]> rl = new ArrayList<Object[]>();
    
    	int col = data[0].length + 1;
    
    	List<Object[]> dl = Arrays.asList(data);
    	for (int j = 0; j < array.length; j++) {
    		for (Object[] d : dl) {
    			Object[] r = new Object[col];
    			for (int i = 0; i < d.length; i++) {
    				r[i] = d[i];
    			}
    			r[col - 1] = array[j].toString();
    			rl.add(r);
    		}
    	}
    
    	Object[][] ret = ArrayUtil.listToDataProvider(rl);
    
    	return ret;
    
    }

    public static <T> Object[][] combineDataObject(Object[][] data, T[] array) {
    	List<Object[]> rl = new ArrayList<Object[]>();
    
    	int col = data[0].length + 1;
    
    	List<Object[]> dl = Arrays.asList(data);
    	for (int j = 0; j < array.length; j++) {
    		for (Object[] d : dl) {
    			Object[] r = new Object[col];
    			for (int i = 0; i < d.length; i++) {
    				r[i] = d[i];
    			}
    			r[col - 1] = array[j];
    			rl.add(r);
    		}
    	}
    
    	Object[][] ret = ArrayUtil.listToDataProvider(rl);
    
    	return ret;
    
    }

    public static Object[][] listToDataProvider(List<Object[]> l) {
    	int row = l.size();
    	Object[] tmp = l.get(0);
    	int col = tmp.length;
    
    	Object[][] ret = new Object[row][col];
    
    	for (int i = 0; i < row; i++) {
    		tmp = l.get(i);
    		for (int j = 0; j < col; j++) {
    			ret[i][j] = tmp[j];
    		}
    	}
    
    	return ret;
    
    }
	

}