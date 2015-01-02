package com.foresee.test.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 反射工具类
 * @author ZMM
 */
public class ReflectUtil
{
	/** 日志 **/
	private static Logger logger = Logger
			.getLogger(ReflectUtil.class);
	
    /**
     * 按照类名反射出它的一个对象
     * 
     * @param classname
     *            包名+类名
     * @return Object
     */
    @SuppressWarnings("rawtypes")
    public static Object getObjByClassName(String classname) {
        Object obj = null;
        if (classname != null) {
            try {
                Class a = Class.forName(classname);
                obj = a.newInstance();
            }
            catch (Exception e) {
            	logger.error("类：com.foresee.biz.common.ReflectUtil 方法：getObjByClassName 原因："
    					+ e.getMessage());
            }
        }
        return obj;
    }

    /**
     * 按照类名,参数值反射出它的一个对象
     * 
     * @param classname
     *            包名+类名
     * @param parameter
     *            构造函数的参数值
     * @return Object
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public static Object getObjByClassNameAndParameter(String classname, Object[] parameter) {
        Object obj = null;
        if (classname != null) {
            try {
                Class a = Class.forName(classname);
                // 获取公有的构造函数,指定参数
                Constructor con = a.getConstructor(getParameterClass(parameter));
                obj = con.newInstance(parameter);
            }
            catch (Exception e) {
            	logger.error("类：com.foresee.biz.common.ReflectUtil 方法：getObjByClassNameAndParameter 原因："
    					+ e.getMessage());
            }
        }
        return obj;
    }

    /**
     * 用类名反射调用它的某个方法(一般针对工具类或者service)(无参数)
     * 
     * @param className
     *            类名
     * @param methodName
     *            方法名
     * @return Object
     */
    public static Object invokeMethod(String className, String methodName) {
        return invokeMethodWithObjHasParame(className, getObjByClassName(className), methodName, new Object[0]);
    }

    /**
     * 用类名反射调用它的某个方法(一般针对工具类或者service)(有参数)
     * 
     * @param className
     *            类名
     * @param methodName
     *            方法名
     * @param parameter
     *            参数数组
     * @return Object
     */
    public static Object invokeMethodHasParame(String className, String methodName, Object[] parameter) {
        return invokeMethodWithObjHasParame(className, getObjByClassName(className), methodName, parameter);
    }

    /**
     * 用对象反射调用它的某个方法(没有参数的方法)
     * 
     * @param className
     *            类名
     * @param obj
     *            对象
     * @param methodName
     *            方法名
     * @return [返回类型说明]
     */
    public static Object invokeMethodWithObj(String className, Object obj, String methodName) {
        return invokeMethodWithObjHasParame(className, obj, methodName, new Object[0]);
    }

    /**
     * 用对象反射调用它的某个方法(有参数的方法)
     * 
     * @param className
     *            类名
     * @param obj
     *            对象
     * @param methodName
     *            方法名
     * @param parameter
     *            参数数组
     * @return Object
     */
    public static Object invokeMethodWithObjHasParame(String className, Object obj, String methodName,
            Object[] parameter) {
        return invokeMethodWithObjHasSpecialParame(className, obj, methodName, parameter, getParameterClass(parameter));
    }

    /**
     * 获取参数列表的class对象
     * 
     * @param parameter
     *            参数值数组
     * @return Class[]
     */
    @SuppressWarnings("rawtypes")
    private static Class[] getParameterClass(Object[] parameter) {
        Class[] methodParameters = null;
        if (parameter != null && parameter.length > 0) {
            methodParameters = new Class[parameter.length];
            for (int i = 0; i < parameter.length; i++) {
                methodParameters[i] = parameter[i].getClass();
            }
        }
        return methodParameters;
    }

    /**
     * 用对象反射调用它的某个方法(指定参数类型的方法)
     * 
     * @param className
     *            类名
     * @param obj
     *            对象
     * @param methodName
     *            方法名
     * @param parameter
     *            参数数组
     * @param methodParameters
     *            参数类型数组
     * @return Object
     */
    @SuppressWarnings("rawtypes")
    public static Object invokeMethodWithObjHasSpecialParame(String className, Object obj, String methodName,
            Object[] parameter, Class[] methodParameters) {
        Object object = null;
        try {
            Method method = Class.forName(className).getMethod(methodName.trim(), methodParameters);
            object = method.invoke(obj, parameter);
        }
        catch (Exception e) {
        	logger.error("类：com.foresee.biz.common.ReflectUtil 方法：invokeMethodWithObjHasSpecialParame 原因："
					+ e.getMessage());
        }
        return object;
    }

    /**
     * 反射获取一个类的方法信息 包括参数,方法名,返回类型
     * 
     * @param className
     *            类名
     * @return List<String>
     */
    @SuppressWarnings("rawtypes")
    public static List<String> getMethodMsg(String className) {
        List<String> retValue = new ArrayList<String>();
        try {
            // 通过getMethods得到类中包含的方法
            Class myClass = Class.forName(className);
            Method m[] = myClass.getDeclaredMethods();
            for (int i = 0; i < m.length; i++) {
                String meth = m[i].toString();
                // 截取出所有的参数,参数以,形式分割
                meth = meth.substring(meth.indexOf("(") + 1, meth.indexOf(")"));
                // ret由3部分构成：参数;方法名;返回类型
                String ret = meth + ";" + m[i].getName() + ";" + m[i].getReturnType();
                retValue.add(ret);
            }
            return retValue;
        }
        catch (ClassNotFoundException e) {
        	logger.error("类：com.foresee.biz.common.ReflectUtil 方法：getMethodMsg 原因："
					+ e.getMessage());
        }
        return retValue;
    }

}