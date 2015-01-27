package com.foresee.test.util.lang;

import org.apache.commons.lang.ObjectUtils;

import com.foresee.test.util.lang.ReflectionUtils;

/**
 * 提供外部访问一个特定的枚举值时使用<br/>
 * 封装成抽象类，采用静态的方法，提供外部用类名访问，防止用户创建类实例
 * 
 * @author Javen
 * 
 */
public abstract class EnumUtils {

    /**
     * 从指定的枚举类中根据property搜寻匹配指定值的枚举实例
     * 
     * @param <T>
     * @param enumClass
     * @param property
     * @param propValue
     * @return
     */
    public static <T extends Enum<T>> T fromEnumProperty(Class<T> enumClass, String property, Object propValue) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T t : enumConstants) {
            Object constantPropValue;
            try {
                constantPropValue = ReflectionUtils.getFieldValue(t, property);
                if (ObjectUtils.equals(constantPropValue, propValue)) {
                    return t;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    /**
     * 从指定的枚举类中根据名称匹配指定值
     * 
     * @param <T>
     * @param enumClass
     * @param constantName
     * @return
     */
    public static <T extends Enum<T>> T fromEnumConstantName(Class<T> enumClass, String constantName) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T t : enumConstants) {
            if (((Enum<?>) t).name().equals(constantName)) {
                return t;
            }
        }
        return null;
    }

}