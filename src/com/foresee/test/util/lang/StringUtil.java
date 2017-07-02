package com.foresee.test.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class StringUtil {

	public static boolean findXML(String xxml, String substr) {
		return (xxml.indexOf(substr) != -1);

	}

	public static String getXMLNote(String xxml, String xNote) {
		String value = "";
		if (xxml.indexOf("<" + xNote + ">") != -1
				&& xxml.indexOf("</" + xNote + ">") != -1) {
			value = xxml.substring(xxml.indexOf("<" + xNote + ">")
					+ ("<" + xNote + ">").length(),
					xxml.indexOf("</" + xNote + ">"));
		}
		return value;
	}

	public static String createXMLNote(String skey, String svalue) {
		return new StringBuffer().append("<").append(skey).append(">")
				.append(svalue).append("</").append(skey).append(">")
				.toString();
	}

	public static final String EMPTY = "";

	/**
	 * 讲NUll转换为EMPTY
	 *
	 * @param string
	 * @return
	 */
	public static String nullToEmpty(String string) {
		return (string == null) ? "" : string;
	}

	/**
	 * 将empty装欢为NUll
	 *
	 * @param string
	 * @return
	 */
	public static String emptyToNull(String string) {
		return isNullOrEmpty(string) ? null : string;
	}

	/**
	 * 判断是否为null或empty
	 *
	 * @param string
	 * @return
	 */
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * 字符窜填充，用padChar填充头部
	 *
	 * @param string
	 * @param minLength
	 * @param padChar
	 * @return
	 */
	public static String padStart(String string, int minLength, char padChar) {
		if (string.length() >= minLength) {
			return string;
		}
		StringBuilder sb = new StringBuilder(minLength);
		for (int i = string.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		sb.append(string);
		return sb.toString();
	}

	/**
	 * 字符窜填充，用padChar填充尾部
	 *
	 * @param string
	 * @param minLength
	 * @param padChar
	 * @return
	 */
	public static String padEnd(String string, int minLength, char padChar) {
		if (string.length() >= minLength) {
			return string;
		}
		StringBuilder sb = new StringBuilder(minLength);
		sb.append(string);
		for (int i = string.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		return sb.toString();
	}

	/**
	 * 首字母大写
	 */
	public static String firstUpperCase(String str) {
		Character ch = str.charAt(0);
		char[] array = str.toCharArray();
		array[0] = Character.toUpperCase(ch);
		return String.valueOf(array);
	}

	/**
	 * 首字母小写
	 *
	 * @param str
	 * @return
	 */
	public static String firstLowerCase(String str) {
		Character ch = str.charAt(0);
		char[] array = str.toCharArray();
		array[0] = Character.toLowerCase(ch);
		return String.valueOf(array);
	}

	/**
	 * 连接字符串
	 *
	 * @param elements
	 *            要连接的数组
	 * @param separator
	 *            分隔符
	 * @return 连接结果
	 */
	public static String join1(Object[] elements, String separator) {
		if (elements == null) {
			return EMPTY;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		StringBuilder sb = new StringBuilder(elements.length * 16);

		for (int i = 0; i < elements.length; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(elements[i]);
		}
		return sb.toString();
	}

	/**
	 * 将一个字符串重复 N 次
	 *
	 * @param str
	 *            要重复的字符串
	 * @param times
	 *            重复次数
	 * @return
	 */
	public static String repeat(String str, int times) {
		StringBuilder sb = new StringBuilder(str.length() * times);
		for (int i = 0; i < times; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String repeat(Object str, String separator, int times) {
		Object[] array = new Object[times];
		Arrays.fill(array, str);
		return join(array, separator);
	}

	public static Object[] concatStr(Object[] obj, String str) {
		Object[] param = new Object[obj.length];
		for (int i = 0; i < obj.length; i++) {
			param[i] = obj[i].toString().concat(str);
		}
		return param;
	}

	/**
	 * 利用反射将对象转换为格式化的字符串 (是null安全的)
	 *
	 * @param object
	 *            要格式化的对象
	 * @return
	 */
	public static String toString(Object object) {

		if (object == null)
			return "null";

		if (object instanceof String || isPrimaryType(object)) {
			return object.toString();
		}

		if (object.getClass().isArray()) {
			return arrayToString(object);
		}
		if (object instanceof Iterable<?>) {
			return iterableToString((Iterable<?>) object);
		}

		StringBuilder sb = new StringBuilder();

		Class<?> clazz = object.getClass();
		while (clazz != null) {
			addField(object, clazz, sb);
			clazz = clazz.getSuperclass();
			if (clazz != null && !Object.class.equals(clazz)) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private static final String arrayToString(Object object) {
		StringBuilder sb = new StringBuilder();
		if (!object.getClass().isArray())
			return sb.toString();
		int len = Array.getLength(object);
		sb.append("[");
		for (int i = 0; i < len; i++) {
			Object obj = Array.get(object, i);
			String str = obj == null ? "null" : toString(obj);
			if (isPrimaryType(obj)) {
				sb.append(str);
			} else {
				sb.append("(").append(str).append(")");
			}
			if (i < len - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static final String iterableToString(Iterable<?> iterable) {
		StringBuilder sb = new StringBuilder();

		Iterator<?> iter = iterable.iterator();
		sb.append("[\n");
		while (true) {
			sb.append("(");
			Object obj = iter.next();
			sb.append(obj == null ? "null" : toString(obj));
			sb.append(")");
			if (iter.hasNext()) {
				sb.append(",\n");
			} else {
				break;
			}
		}
		sb.append("\n]");
		return sb.toString();
	}

	private static final String addField(Object object, Class<?> clazz,
			StringBuilder sb) {
		Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			try {
				String name = field.getName();
				if (i > 0)
					sb.append(", ");
				sb.append(name).append("=").append(toString(field.get(object)));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static final boolean isPrimaryType(Object object) {
		return object instanceof Byte || object instanceof Short
				|| object instanceof Integer || object instanceof Long
				|| object instanceof Character || object instanceof Float
				|| object instanceof Double || object instanceof Boolean;
	}

	public static String normalString(String s) {
		return nonEmptyTrimmedOrNull(s);
	}

	public static boolean nonEmptyString(String s) {
		return s != null && s.length() > 0;
	}

	public static boolean nonWhitespaceString(String s) {
		return s != null && s.trim().length() > 0;
	}

	public static String nonEmptyOrNull(String s) {
		return nonEmptyString(s) ? s : null;
	}

	public static String nonNullOrBlank(String s) {
		return s == null ? "" : s;
	}

	public static String nonEmptyTrimmedOrNull(String s) {
		String s1 = s;
		if (s1 != null) {
			s1 = s1.trim();
			s1 = s1.length() <= 0 ? null : s1;
		}
		return s1;
	}

	public static byte[] getUTF8Bytes(String s) {
		try {
			return s.getBytes("UTF8");
		} catch (UnsupportedEncodingException unsupportedencodingexception) {
			unsupportedencodingexception.printStackTrace();
		}
		throw new InternalError("UTF8 is an unsupported encoding?!?");
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 常量和singleton。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/** 空字符串。 */
	public static final String EMPTY_STRING = "";

	private static final String FILE_SEPARATOR = "/";

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 判空函数。 */
	/*                                                                              */
	/* 以下方法用来判定一个字符串是否为： */
	/* 1. null */
	/* 2. empty - "" */
	/* 3. blank - "全部是空白" - 空白由Character.isWhitespace所定义。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串转义以方便html输出
	 */
	public String escapeHtml(String s) {

		return org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
	}

	public String unescapeHtml(String s) {
		return org.apache.commons.lang.StringEscapeUtils.unescapeHtml(s);
	}

	public String arrayIndex(String[] args, int _index) {
		return args[_index];
	}

	/**
	 * 检查字符串是否为 <code>null</code> 或空字符串 <code>""</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.isEmpty(null)      = true
	 *    StringUtil.isEmpty(&quot;&quot;)        = true
	 *    StringUtil.isEmpty(&quot; &quot;)       = false
	 *    StringUtil.isEmpty(&quot;bob&quot;)     = false
	 *    StringUtil.isEmpty(&quot;  bob  &quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果为空, 则返回 <code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * 检查字符串是否不是 <code>null</code> 和空字符串 <code>""</code>。
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果不为空, 则返回 <code>true</code>
	 *
	 * @see com.nonfamous.commom.util.StringUtils#isEmpty(String)
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 检查字符串是否是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符。
	 *
	 * <pre>
	 * 
	 *    StringUtil.isBlank(null)      = true
	 *    StringUtil.isBlank(&quot;&quot;)        = true
	 *    StringUtil.isBlank(&quot; &quot;)       = true
	 *    StringUtil.isBlank(&quot;bob&quot;)     = false
	 *    StringUtil.isBlank(&quot;  bob  &quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果为空白, 则返回 <code>true</code>
	 */
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查字符串是否不是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符。
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果不是空白, 则返回 <code>true</code>
	 *
	 * @see com.nonfamous.commom.util.StringUtils#isBlank(String)(String)
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 默认值函数。 */
	/*                                                                              */
	/* 当字符串为null、empty或blank时，将字符串转换成指定的默认字符串。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code> ，否则返回字符串本身。
	 *
	 * <pre>
	 * 
	 *    StringUtil.defaultIfNull(null)  = &quot;&quot;
	 *    StringUtil.defaultIfNull(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.defaultIfNull(&quot;  &quot;)  = &quot;  &quot;
	 *    StringUtil.defaultIfNull(&quot;bat&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfNull(String str) {
		return (str == null) ? EMPTY_STRING : str;
	}

	/**
	 * 如果字符串是 <code>null</code> ，则返回指定默认字符串，否则返回字符串本身。
	 *
	 * <pre>
	 * 
	 *    StringUtil.defaultIfNull(null, &quot;default&quot;)  = &quot;default&quot;
	 *    StringUtil.defaultIfNull(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
	 *    StringUtil.defaultIfNull(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
	 *    StringUtil.defaultIfNull(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 *
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfNull(String str, String defaultStr) {
		return (str == null) ? defaultStr : str;
	}

	/**
	 * 如果字符串是 <code>null</code> 或空字符串 <code>""</code> ，则返回空字符串 <code>""</code>
	 * ，否则返回字符串本身。
	 *
	 * <p>
	 * 此方法实际上和 <code>defaultIfNull(String)</code> 等效。
	 *
	 * <pre>
	 * 
	 *    StringUtil.defaultIfEmpty(null)  = &quot;&quot;
	 *    StringUtil.defaultIfEmpty(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.defaultIfEmpty(&quot;  &quot;)  = &quot;  &quot;
	 *    StringUtil.defaultIfEmpty(&quot;bat&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfEmpty(String str) {
		return isEmpty(str) ? EMPTY_STRING : str;
	}

	/**
	 * 如果字符串是 <code>null</code> 或空字符串 <code>""</code> ，则返回指定默认字符串，否则返回字符串本身。
	 *
	 * <pre>
	 * 
	 *    StringUtil.defaultIfEmpty(null, &quot;default&quot;)  = &quot;default&quot;
	 *    StringUtil.defaultIfEmpty(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
	 *    StringUtil.defaultIfEmpty(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
	 *    StringUtil.defaultIfEmpty(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 *
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	/**
	 * 如果字符串是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符，则返回空字符串
	 * <code>""</code> ，否则返回字符串本身。
	 *
	 * <pre>
	 * 
	 *    StringUtil.defaultIfBlank(null)  = &quot;&quot;
	 *    StringUtil.defaultIfBlank(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.defaultIfBlank(&quot;  &quot;)  = &quot;&quot;
	 *    StringUtil.defaultIfBlank(&quot;bat&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfBlank(String str) {
		return isBlank(str) ? EMPTY_STRING : str;
	}

	/**
	 * 如果字符串是 <code>null</code> 或空字符串 <code>""</code> ，则返回指定默认字符串，否则返回字符串本身。
	 *
	 * <pre>
	 * 
	 *    StringUtil.defaultIfBlank(null, &quot;default&quot;)  = &quot;default&quot;
	 *    StringUtil.defaultIfBlank(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
	 *    StringUtil.defaultIfBlank(&quot;  &quot;, &quot;default&quot;)  = &quot;default&quot;
	 *    StringUtil.defaultIfBlank(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 *
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 去空白（或指定字符）的函数。 */
	/*                                                                              */
	/* 以下方法用来除去一个字串中的空白或指定字符。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trim(null)          = null
	 *    StringUtil.trim(&quot;&quot;)            = &quot;&quot;
	 *    StringUtil.trim(&quot;     &quot;)       = &quot;&quot;
	 *    StringUtil.trim(&quot;abc&quot;)         = &quot;abc&quot;
	 *    StringUtil.trim(&quot;    abc    &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trim(String str) {
		return trim(str, null, 0);
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = null
	 *    StringUtil.trim(&quot;&quot;, *)            = &quot;&quot;
	 *    StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 *
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trim(String str, String stripChars) {
		return trim(str, stripChars, 0);
	}

	/**
	 * 除去字符串头部的空白，如果字符串是 <code>null</code> ，则返回 <code>null</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trimStart(null)         = null
	 *    StringUtil.trimStart(&quot;&quot;)           = &quot;&quot;
	 *    StringUtil.trimStart(&quot;abc&quot;)        = &quot;abc&quot;
	 *    StringUtil.trimStart(&quot;  abc&quot;)      = &quot;abc&quot;
	 *    StringUtil.trimStart(&quot;abc  &quot;)      = &quot;abc  &quot;
	 *    StringUtil.trimStart(&quot; abc &quot;)      = &quot;abc &quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimStart(String str) {
		return trim(str, null, -1);
	}

	/**
	 * 除去字符串头部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trimStart(null, *)          = null
	 *    StringUtil.trimStart(&quot;&quot;, *)            = &quot;&quot;
	 *    StringUtil.trimStart(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 *    StringUtil.trimStart(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *    StringUtil.trimStart(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trimStart(&quot;abc  &quot;, null)    = &quot;abc  &quot;
	 *    StringUtil.trimStart(&quot; abc &quot;, null)    = &quot;abc &quot;
	 *    StringUtil.trimStart(&quot;yxabc  &quot;, &quot;xyz&quot;) = &quot;abc  &quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 *
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trimStart(String str, String stripChars) {
		return trim(str, stripChars, -1);
	}

	/**
	 * 除去字符串尾部的空白，如果字符串是 <code>null</code> ，则返回 <code>null</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trimEnd(null)       = null
	 *    StringUtil.trimEnd(&quot;&quot;)         = &quot;&quot;
	 *    StringUtil.trimEnd(&quot;abc&quot;)      = &quot;abc&quot;
	 *    StringUtil.trimEnd(&quot;  abc&quot;)    = &quot;  abc&quot;
	 *    StringUtil.trimEnd(&quot;abc  &quot;)    = &quot;abc&quot;
	 *    StringUtil.trimEnd(&quot; abc &quot;)    = &quot; abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimEnd(String str) {
		return trim(str, null, 1);
	}

	/**
	 * 除去字符串尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trimEnd(null, *)          = null
	 *    StringUtil.trimEnd(&quot;&quot;, *)            = &quot;&quot;
	 *    StringUtil.trimEnd(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 *    StringUtil.trimEnd(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *    StringUtil.trimEnd(&quot;  abc&quot;, null)    = &quot;  abc&quot;
	 *    StringUtil.trimEnd(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trimEnd(&quot; abc &quot;, null)    = &quot; abc&quot;
	 *    StringUtil.trimEnd(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 *
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trimEnd(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串 <code>""</code> ，则返回 <code>null</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trimToNull(null)          = null
	 *    StringUtil.trimToNull(&quot;&quot;)            = null
	 *    StringUtil.trimToNull(&quot;     &quot;)       = null
	 *    StringUtil.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
	 *    StringUtil.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str) {
		return trimToNull(str, null);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串 <code>""</code> ，则返回 <code>null</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = null
	 *    StringUtil.trim(&quot;&quot;, *)            = null
	 *    StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str, String stripChars) {
		String result = trim(str, stripChars);

		if ((result == null) || (result.length() == 0)) {
			return null;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trimToEmpty(null)          = &quot;&quot;
	 *    StringUtil.trimToEmpty(&quot;&quot;)            = &quot;&quot;
	 *    StringUtil.trimToEmpty(&quot;     &quot;)       = &quot;&quot;
	 *    StringUtil.trimToEmpty(&quot;abc&quot;)         = &quot;abc&quot;
	 *    StringUtil.trimToEmpty(&quot;    abc    &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str) {
		return trimToEmpty(str, null);
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code>。
	 *
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = &quot;&quot;
	 *    StringUtil.trim(&quot;&quot;, *)            = &quot;&quot;
	 *    StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要处理的字符串
	 *
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str, String stripChars) {
		String result = trim(str, stripChars);

		if (result == null) {
			return EMPTY_STRING;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = null
	 *    StringUtil.trim(&quot;&quot;, *)            = &quot;&quot;
	 *    StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 *    StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * @param mode
	 *            <code>-1</code> 表示trimStart， <code>0</code> 表示trim全部，
	 *            <code>1</code> 表示trimEnd
	 *
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end)
						&& (Character.isWhitespace(str.charAt(start)))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end)
						&& (stripChars.indexOf(str.charAt(start)) != -1)) {
					start++;
				}
			}
		}

		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null) {
				while ((start < end)
						&& (Character.isWhitespace(str.charAt(end - 1)))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end)
						&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
					end--;
				}
			}
		}

		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}

		return str;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 比较函数。 */
	/*                                                                              */
	/* 以下方法用来比较两个字符串是否相同。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 比较两个字符串（大小写敏感）。
	 *
	 * <pre>
	 * 
	 *    StringUtil.equals(null, null)   = true
	 *    StringUtil.equals(null, &quot;abc&quot;)  = false
	 *    StringUtil.equals(&quot;abc&quot;, null)  = false
	 *    StringUtil.equals(&quot;abc&quot;, &quot;abc&quot;) = true
	 *    StringUtil.equals(&quot;abc&quot;, &quot;ABC&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str1
	 *            要比较的字符串1
	 * @param str2
	 *            要比较的字符串2
	 *
	 * @return 如果两个字符串相同，或者都是 <code>null</code> ，则返回 <code>true</code>
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equals(str2);
	}

	/**
	 * 比较两个字符串（大小写不敏感）。
	 *
	 * <pre>
	 * 
	 *    StringUtil.equalsIgnoreCase(null, null)   = true
	 *    StringUtil.equalsIgnoreCase(null, &quot;abc&quot;)  = false
	 *    StringUtil.equalsIgnoreCase(&quot;abc&quot;, null)  = false
	 *    StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
	 *    StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
	 * 
	 * </pre>
	 *
	 * @param str1
	 *            要比较的字符串1
	 * @param str2
	 *            要比较的字符串2
	 *
	 * @return 如果两个字符串相同，或者都是 <code>null</code> ，则返回 <code>true</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串类型判定函数。 */
	/*                                                                              */
	/* 判定字符串的类型是否为：字母、数字、空白等 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 判断字符串是否只包含unicode字母。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isAlpha(null)   = false
	 *    StringUtil.isAlpha(&quot;&quot;)     = true
	 *    StringUtil.isAlpha(&quot;  &quot;)   = false
	 *    StringUtil.isAlpha(&quot;abc&quot;)  = true
	 *    StringUtil.isAlpha(&quot;ab2c&quot;) = false
	 *    StringUtil.isAlpha(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母组成，则返回 <code>true</code>
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母和空格 <code>' '</code>。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isAlphaSpace(null)   = false
	 *    StringUtil.isAlphaSpace(&quot;&quot;)     = true
	 *    StringUtil.isAlphaSpace(&quot;  &quot;)   = true
	 *    StringUtil.isAlphaSpace(&quot;abc&quot;)  = true
	 *    StringUtil.isAlphaSpace(&quot;ab c&quot;) = true
	 *    StringUtil.isAlphaSpace(&quot;ab2c&quot;) = false
	 *    StringUtil.isAlphaSpace(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母和空格组成，则返回 <code>true</code>
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母和数字。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isAlphanumeric(null)   = false
	 *    StringUtil.isAlphanumeric(&quot;&quot;)     = true
	 *    StringUtil.isAlphanumeric(&quot;  &quot;)   = false
	 *    StringUtil.isAlphanumeric(&quot;abc&quot;)  = true
	 *    StringUtil.isAlphanumeric(&quot;ab c&quot;) = false
	 *    StringUtil.isAlphanumeric(&quot;ab2c&quot;) = true
	 *    StringUtil.isAlphanumeric(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母数字组成，则返回 <code>true</code>
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母数字和空格 <code>' '</code>。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isAlphanumericSpace(null)   = false
	 *    StringUtil.isAlphanumericSpace(&quot;&quot;)     = true
	 *    StringUtil.isAlphanumericSpace(&quot;  &quot;)   = true
	 *    StringUtil.isAlphanumericSpace(&quot;abc&quot;)  = true
	 *    StringUtil.isAlphanumericSpace(&quot;ab c&quot;) = true
	 *    StringUtil.isAlphanumericSpace(&quot;ab2c&quot;) = true
	 *    StringUtil.isAlphanumericSpace(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母数字和空格组成，则返回
	 *         <code>true</code>
	 */
	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode数字。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isNumeric(null)   = false
	 *    StringUtil.isNumeric(&quot;&quot;)     = true
	 *    StringUtil.isNumeric(&quot;  &quot;)   = false
	 *    StringUtil.isNumeric(&quot;123&quot;)  = true
	 *    StringUtil.isNumeric(&quot;12 3&quot;) = false
	 *    StringUtil.isNumeric(&quot;ab2c&quot;) = false
	 *    StringUtil.isNumeric(&quot;12-3&quot;) = false
	 *    StringUtil.isNumeric(&quot;12.3&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode数字组成，则返回 <code>true</code>
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode数字和空格 <code>' '</code>。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isNumericSpace(null)   = false
	 *    StringUtil.isNumericSpace(&quot;&quot;)     = true
	 *    StringUtil.isNumericSpace(&quot;  &quot;)   = true
	 *    StringUtil.isNumericSpace(&quot;123&quot;)  = true
	 *    StringUtil.isNumericSpace(&quot;12 3&quot;) = true
	 *    StringUtil.isNumericSpace(&quot;ab2c&quot;) = false
	 *    StringUtil.isNumericSpace(&quot;12-3&quot;) = false
	 *    StringUtil.isNumericSpace(&quot;12.3&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode数字和空格组成，则返回 <code>true</code>
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode空白。
	 *
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 *
	 * <pre>
	 * 
	 *    StringUtil.isWhitespace(null)   = false
	 *    StringUtil.isWhitespace(&quot;&quot;)     = true
	 *    StringUtil.isWhitespace(&quot;  &quot;)   = true
	 *    StringUtil.isWhitespace(&quot;abc&quot;)  = false
	 *    StringUtil.isWhitespace(&quot;ab2c&quot;) = false
	 *    StringUtil.isWhitespace(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 *
	 * @param str
	 *            要检查的字符串
	 *
	 * @return 如果字符串非 <code>null</code> 并且全由unicode空白组成，则返回 <code>true</code>
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 大小写转换。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串转换成大写。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.toUpperCase(null)  = null
	 *    StringUtil.toUpperCase(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.toUpperCase(&quot;aBc&quot;) = &quot;ABC&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 大写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toUpperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	/**
	 * 将字符串转换成小写。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.toLowerCase(null)  = null
	 *    StringUtil.toLowerCase(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.toLowerCase(&quot;aBc&quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 大写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toLowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	/**
	 * 将字符串的首字符转成大写（ <code>Character.toTitleCase</code> ），其它字符不变。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.capitalize(null)  = null
	 *    StringUtil.capitalize(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.capitalize(&quot;cat&quot;) = &quot;Cat&quot;
	 *    StringUtil.capitalize(&quot;cAt&quot;) = &quot;CAt&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 首字符为大写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String capitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * 将字符串的首字符转成小写，其它字符不变。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.uncapitalize(null)  = null
	 *    StringUtil.uncapitalize(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.uncapitalize(&quot;Cat&quot;) = &quot;cat&quot;
	 *    StringUtil.uncapitalize(&quot;CAT&quot;) = &quot;cAT&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 首字符为小写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String uncapitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen)
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * 反转字符串的大小写。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.swapCase(null)                 = null
	 *    StringUtil.swapCase(&quot;&quot;)                   = &quot;&quot;
	 *    StringUtil.swapCase(&quot;The dog has a BONE&quot;) = &quot;tHE DOG HAS A bone&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 大小写被反转的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String swapCase(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;

		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);

			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}

			buffer.append(ch);
		}

		return buffer.toString();
	}

	/**
	 * 将字符串转换成camel case。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.toCamelCase(null)  = null
	 *    StringUtil.toCamelCase(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.toCamelCase(&quot;aBc&quot;) = &quot;aBc&quot;
	 *    StringUtil.toCamelCase(&quot;aBc def&quot;) = &quot;aBcDef&quot;
	 *    StringUtil.toCamelCase(&quot;aBc def_ghi&quot;) = &quot;aBcDefGhi&quot;
	 *    StringUtil.toCamelCase(&quot;aBc def_ghi 123&quot;) = &quot;aBcDefGhi123&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * <p>
	 * 此方法会保留除了下划线和空白以外的所有分隔符。
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return camel case字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toCamelCase(String str) {
		return CAMEL_CASE_TOKENIZER.parse(str);
	}

	/**
	 * 将字符串转换成pascal case。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.toPascalCase(null)  = null
	 *    StringUtil.toPascalCase(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.toPascalCase(&quot;aBc&quot;) = &quot;ABc&quot;
	 *    StringUtil.toPascalCase(&quot;aBc def&quot;) = &quot;ABcDef&quot;
	 *    StringUtil.toPascalCase(&quot;aBc def_ghi&quot;) = &quot;ABcDefGhi&quot;
	 *    StringUtil.toPascalCase(&quot;aBc def_ghi 123&quot;) = &quot;aBcDefGhi123&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * <p>
	 * 此方法会保留除了下划线和空白以外的所有分隔符。
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return pascal case字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toPascalCase(String str) {
		return PASCAL_CASE_TOKENIZER.parse(str);
	}

	/**
	 * 将字符串转换成下划线分隔的大写字符串。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.toUpperCaseWithUnderscores(null)  = null
	 *    StringUtil.toUpperCaseWithUnderscores(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.toUpperCaseWithUnderscores(&quot;aBc&quot;) = &quot;A_BC&quot;
	 *    StringUtil.toUpperCaseWithUnderscores(&quot;aBc def&quot;) = &quot;A_BC_DEF&quot;
	 *    StringUtil.toUpperCaseWithUnderscores(&quot;aBc def_ghi&quot;) = &quot;A_BC_DEF_GHI&quot;
	 *    StringUtil.toUpperCaseWithUnderscores(&quot;aBc def_ghi 123&quot;) = &quot;A_BC_DEF_GHI_123&quot;
	 *    StringUtil.toUpperCaseWithUnderscores(&quot;__a__Bc__&quot;) = &quot;__A__BC__&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * <p>
	 * 此方法会保留除了空白以外的所有分隔符。
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 下划线分隔的大写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toUpperCaseWithUnderscores(String str) {
		return UPPER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
	}

	/**
	 * 将字符串转换成下划线分隔的小写字符串。
	 *
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.toLowerCaseWithUnderscores(null)  = null
	 *    StringUtil.toLowerCaseWithUnderscores(&quot;&quot;)    = &quot;&quot;
	 *    StringUtil.toLowerCaseWithUnderscores(&quot;aBc&quot;) = &quot;a_bc&quot;
	 *    StringUtil.toLowerCaseWithUnderscores(&quot;aBc def&quot;) = &quot;a_bc_def&quot;
	 *    StringUtil.toLowerCaseWithUnderscores(&quot;aBc def_ghi&quot;) = &quot;a_bc_def_ghi&quot;
	 *    StringUtil.toLowerCaseWithUnderscores(&quot;aBc def_ghi 123&quot;) = &quot;a_bc_def_ghi_123&quot;
	 *    StringUtil.toLowerCaseWithUnderscores(&quot;__a__Bc__&quot;) = &quot;__a__bc__&quot;
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * <p>
	 * 此方法会保留除了空白以外的所有分隔符。
	 * </p>
	 *
	 * @param str
	 *            要转换的字符串
	 *
	 * @return 下划线分隔的小写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toLowerCaseWithUnderscores(String str) {
		return LOWER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
	}

	/** 解析单词的解析器。 */
	private static final WordTokenizer CAMEL_CASE_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(Character.toUpperCase(ch));
			} else {
				buffer.append(Character.toLowerCase(ch));
			}
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			if (ch != UNDERSCORE) {
				buffer.append(ch);
			}
		}
	};

	private static final WordTokenizer PASCAL_CASE_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			if (ch != UNDERSCORE) {
				buffer.append(ch);
			}
		}
	};

	private static final WordTokenizer UPPER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(Character.toUpperCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}
	};

	private static final WordTokenizer LOWER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(Character.toLowerCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}
	};

	/**
	 * 解析出下列语法所构成的 <code>SENTENCE</code>。
	 *
	 * <pre>
	 * 
	 *     SENTENCE = WORD (DELIMITER* WORD)*
	 *   
	 *     WORD = UPPER_CASE_WORD | LOWER_CASE_WORD | TITLE_CASE_WORD | DIGIT_WORD
	 *   
	 *     UPPER_CASE_WORD = UPPER_CASE_LETTER+
	 *     LOWER_CASE_WORD = LOWER_CASE_LETTER+
	 *     TITLE_CASE_WORD = UPPER_CASE_LETTER LOWER_CASE_LETTER+
	 *     DIGIT_WORD      = DIGIT+
	 *   
	 *     UPPER_CASE_LETTER = Character.isUpperCase()
	 *     LOWER_CASE_LETTER = Character.isLowerCase()
	 *     DIGIT             = Character.isDigit()
	 *     NON_LETTER_DIGIT  = !Character.isUpperCase() &amp;&amp; !Character.isLowerCase() &amp;&amp; !Character.isDigit()
	 *   
	 *     DELIMITER = WHITESPACE | NON_LETTER_DIGIT
	 * 
	 * </pre>
	 */
	private abstract static class WordTokenizer {
		protected static final char UNDERSCORE = '_';

		/**
		 * Parse sentence。
		 */
		public String parse(String str) {
			if (StringUtil.isEmpty(str)) {
				return str;
			}

			int length = str.length();
			StringBuffer buffer = new StringBuffer(length);

			for (int index = 0; index < length; index++) {
				char ch = str.charAt(index);

				// 忽略空白。
				if (Character.isWhitespace(ch)) {
					continue;
				}

				// 大写字母开始：UpperCaseWord或是TitleCaseWord。
				if (Character.isUpperCase(ch)) {
					int wordIndex = index + 1;

					while (wordIndex < length) {
						char wordChar = str.charAt(wordIndex);

						if (Character.isUpperCase(wordChar)) {
							wordIndex++;
						} else if (Character.isLowerCase(wordChar)) {
							wordIndex--;
							break;
						} else {
							break;
						}
					}

					// 1. wordIndex == length，说明最后一个字母为大写，以upperCaseWord处理之。
					// 2. wordIndex == index，说明index处为一个titleCaseWord。
					// 3. wordIndex > index，说明index到wordIndex -
					// 1处全部是大写，以upperCaseWord处理。
					if ((wordIndex == length) || (wordIndex > index)) {
						index = parseUpperCaseWord(buffer, str, index,
								wordIndex);
					} else {
						index = parseTitleCaseWord(buffer, str, index);
					}

					continue;
				}

				// 小写字母开始：LowerCaseWord。
				if (Character.isLowerCase(ch)) {
					index = parseLowerCaseWord(buffer, str, index);
					continue;
				}

				// 数字开始：DigitWord。
				if (Character.isDigit(ch)) {
					index = parseDigitWord(buffer, str, index);
					continue;
				}

				// 非字母数字开始：Delimiter。
				inDelimiter(buffer, ch);
			}

			return buffer.toString();
		}

		private int parseUpperCaseWord(StringBuffer buffer, String str,
				int index, int length) {
			char ch = str.charAt(index++);

			// 首字母，必然存在且为大写。
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// 后续字母，必为小写。
			for (; index < length; index++) {
				ch = str.charAt(index);
				inWord(buffer, ch);
			}

			return index - 1;
		}

		private int parseLowerCaseWord(StringBuffer buffer, String str,
				int index) {
			char ch = str.charAt(index++);

			// 首字母，必然存在且为小写。
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// 后续字母，必为小写。
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isLowerCase(ch)) {
					inWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		private int parseTitleCaseWord(StringBuffer buffer, String str,
				int index) {
			char ch = str.charAt(index++);

			// 首字母，必然存在且为大写。
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// 后续字母，必为小写。
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isLowerCase(ch)) {
					inWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		private int parseDigitWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// 首字符，必然存在且为数字。
			if (buffer.length() == 0) {
				startDigitSentence(buffer, ch);
			} else {
				startDigitWord(buffer, ch);
			}

			// 后续字符，必为数字。
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isDigit(ch)) {
					inDigitWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		protected boolean isDelimiter(char ch) {
			return !Character.isUpperCase(ch) && !Character.isLowerCase(ch)
					&& !Character.isDigit(ch);
		}

		protected abstract void startSentence(StringBuffer buffer, char ch);

		protected abstract void startWord(StringBuffer buffer, char ch);

		protected abstract void inWord(StringBuffer buffer, char ch);

		protected abstract void startDigitSentence(StringBuffer buffer, char ch);

		protected abstract void startDigitWord(StringBuffer buffer, char ch);

		protected abstract void inDigitWord(StringBuffer buffer, char ch);

		protected abstract void inDelimiter(StringBuffer buffer, char ch);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串分割函数。 */
	/*                                                                              */
	/* 将字符串按指定分隔符分割。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串按空白字符分割。
	 *
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.split(null)       = null
	 *    StringUtil.split(&quot;&quot;)         = []
	 *    StringUtil.split(&quot;abc def&quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 *    StringUtil.split(&quot;abc  def&quot;) = [&quot;abc&quot;, &quot;def&quot;]
	 *    StringUtil.split(&quot; abc &quot;)    = [&quot;abc&quot;]
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要分割的字符串
	 *
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 *
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.split(null, *)         = null
	 *    StringUtil.split(&quot;&quot;, *)           = []
	 *    StringUtil.split(&quot;a.b.c&quot;, '.')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 *    StringUtil.split(&quot;a..b.c&quot;, '.')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 *    StringUtil.split(&quot;a:b:c&quot;, '.')    = [&quot;a:b:c&quot;]
	 *    StringUtil.split(&quot;a b c&quot;, ' ')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要分割的字符串
	 * @param separatorChar
	 *            分隔符
	 *
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return null;
		}

		List<String> list = new ArrayList<String>();
		int i = 0;
		int start = 0;
		boolean match = false;

		while (i < length) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}

				start = ++i;
				continue;
			}

			match = true;
			i++;
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 将字符串按指定字符分割。
	 *
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.split(null, *)                = null
	 *    StringUtil.split(&quot;&quot;, *)                  = []
	 *    StringUtil.split(&quot;abc def&quot;, null)        = [&quot;abc&quot;, &quot;def&quot;]
	 *    StringUtil.split(&quot;abc def&quot;, &quot; &quot;)         = [&quot;abc&quot;, &quot;def&quot;]
	 *    StringUtil.split(&quot;abc  def&quot;, &quot; &quot;)        = [&quot;abc&quot;, &quot;def&quot;]
	 *    StringUtil.split(&quot; ab:  cd::ef  &quot;, &quot;:&quot;)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *    StringUtil.split(&quot;abc.def&quot;, &quot;&quot;)          = [&quot;abc.def&quot;]
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要分割的字符串
	 * @param separatorChars
	 *            分隔符
	 *
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str, String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 *
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 *
	 * <pre>
	 * 
	 *    StringUtil.split(null, *, *)                 = null
	 *    StringUtil.split(&quot;&quot;, *, *)                   = []
	 *    StringUtil.split(&quot;ab cd ef&quot;, null, 0)        = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *    StringUtil.split(&quot;  ab   cd ef  &quot;, null, 0)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *    StringUtil.split(&quot;ab:cd::ef&quot;, &quot;:&quot;, 0)        = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *    StringUtil.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)         = [&quot;ab&quot;, &quot;cdef&quot;]
	 *    StringUtil.split(&quot;abc.def&quot;, &quot;&quot;, 2)           = [&quot;abc.def&quot;]
	 * 
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str
	 *            要分割的字符串
	 * @param separatorChars
	 *            分隔符
	 * @param max
	 *            返回的数组的最大个数，如果小于等于0，则表示无限制
	 *
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str, String separatorChars, int max) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return null;
		}

		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;

		if (separatorChars == null) {
			// null表示使用空白作为分隔符
			while (i < length) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// 优化分隔符长度为1的情形
			char sep = separatorChars.charAt(0);

			while (i < length) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else {
			// 一般情形
			while (i < length) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串连接函数。 */
	/*                                                                              */
	/* 将多个对象按指定分隔符连接成字符串。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将数组中的元素连接成一个字符串。
	 *
	 * <pre>
	 * 
	 *    StringUtil.join(null)            = null
	 *    StringUtil.join([])              = &quot;&quot;
	 *    StringUtil.join([null])          = &quot;&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]) = &quot;abc&quot;
	 *    StringUtil.join([null, &quot;&quot;, &quot;a&quot;]) = &quot;a&quot;
	 * 
	 * </pre>
	 *
	 * @param array
	 *            要连接的数组
	 *
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Object[] array) {
		return join(array, null);
	}

	/**
	 * 将数组中的元素连接成一个字符串。
	 *
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)               = null
	 *    StringUtil.join([], *)                 = &quot;&quot;
	 *    StringUtil.join([null], *)             = &quot;&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], ';')  = &quot;a;b;c&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null) = &quot;abc&quot;
	 *    StringUtil.join([null, &quot;&quot;, &quot;a&quot;], ';')  = &quot;;;a&quot;
	 * 
	 * </pre>
	 *
	 * @param array
	 *            要连接的数组
	 * @param separator
	 *            分隔符
	 *
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		int arraySize = array.length;
		int bufSize = (arraySize == 0) ? 0 : ((((array[0] == null) ? 16
				: array[0].toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/**
	 * 将数组中的元素连接成一个字符串。
	 *
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)                = null
	 *    StringUtil.join([], *)                  = &quot;&quot;
	 *    StringUtil.join([null], *)              = &quot;&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;--&quot;)  = &quot;a--b--c&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null)  = &quot;abc&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;&quot;)    = &quot;abc&quot;
	 *    StringUtil.join([null, &quot;&quot;, &quot;a&quot;], ',')   = &quot;,,a&quot;
	 * 
	 * </pre>
	 *
	 * @param array
	 *            要连接的数组
	 * @param separator
	 *            分隔符
	 *
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}

		if (separator == null) {
			separator = EMPTY_STRING;
		}

		int arraySize = array.length;

		// ArraySize == 0: Len = 0
		// ArraySize > 0: Len = NofStrings *(len(firstString) + len(separator))
		// (估计大约所有的字符串都一样长)
		int bufSize = (arraySize == 0) ? 0
				: (arraySize * (((array[0] == null) ? 16 : array[0].toString()
						.length()) + ((separator != null) ? separator.length()
						: 0)));

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if ((separator != null) && (i > 0)) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/**
	 * 将 <code>Iterator</code> 中的元素连接成一个字符串。
	 *
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)                = null
	 *    StringUtil.join([], *)                  = &quot;&quot;
	 *    StringUtil.join([null], *)              = &quot;&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;--&quot;)  = &quot;a--b--c&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null)  = &quot;abc&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;&quot;)    = &quot;abc&quot;
	 *    StringUtil.join([null, &quot;&quot;, &quot;a&quot;], ',')   = &quot;,,a&quot;
	 * 
	 * </pre>
	 *
	 * @param iterator
	 *            要连接的 <code>Iterator</code>
	 * @param separator
	 *            分隔符
	 *
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Iterator iterator, char separator) {
		if (iterator == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(256); // Java默认值是16, 可能偏小

		while (iterator.hasNext()) {
			Object obj = iterator.next();

			if (obj != null) {
				buf.append(obj);
			}

			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}

		return buf.toString();
	}

	/**
	 * 将 <code>Iterator</code> 中的元素连接成一个字符串。
	 *
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)                = null
	 *    StringUtil.join([], *)                  = &quot;&quot;
	 *    StringUtil.join([null], *)              = &quot;&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;--&quot;)  = &quot;a--b--c&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null)  = &quot;abc&quot;
	 *    StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;&quot;)    = &quot;abc&quot;
	 *    StringUtil.join([null, &quot;&quot;, &quot;a&quot;], ',')   = &quot;,,a&quot;
	 * 
	 * </pre>
	 *
	 * @param iterator
	 *            要连接的 <code>Iterator</code>
	 * @param separator
	 *            分隔符
	 *
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Iterator iterator, String separator) {
		if (iterator == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(256); // Java默认值是16, 可能偏小

		while (iterator.hasNext()) {
			Object obj = iterator.next();

			if (obj != null) {
				buf.append(obj);
			}

			if ((separator != null) && iterator.hasNext()) {
				buf.append(separator);
			}
		}

		return buf.toString();
	}

    public static String ConvertCharset(String inStr,String srcCharset){
        return ConvertCharset(inStr,srcCharset,"UTF-8");
    }
    
    public static String ConvertCharset(String inStr){
        return ConvertCharset(inStr,"GBK");
    }
    public static String ConvertCharset(String inStr,String srcCharset,String targetCharset){
        String xstr =null;
        try {
            
            xstr = new String(inStr.getBytes(srcCharset),targetCharset);
        } catch (UnsupportedEncodingException e) {
            
            e.printStackTrace();
        }
        return xstr;
    }

	
	
//String String.prototype.replaceAll2 = function(String s1,String s2) { 
//    return this.replace(new RegExp(s1,"gm"),s2); 
//}
/*
 * 在进行文本分析的时候我们经常需要过滤掉停用词，标点等，本文给大家介绍一下如何识别并删除文本中的所有标点符号。下面是三个可行的正则表达式方案，童鞋们来试试吧^_^

(1) s.replaceAll( "\\p{Punct}", "" );      
(2) s.replaceAll("\\pP" , "");      
(3) s.replaceAll("\\p{P}" , ""); 



(1)不能完全清楚所有标点，(2)和(3)等价都能完全清楚标点（中文和英文都能清除） 需要注意的是，还是存在一些系统的不认为是标点的半角和全角符号无法清除，如下所示：

+~$`^=|<>～｀＄＾＋＝｜＜＞￥×


修改后：

(2) s.replaceAll( "[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "");      
(3) s.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , ""); 




上面的三个方案中给出的都是采用 Unicode 标点属性方式的正则表达式，可以去掉所有的标点符号，包括全角、半角、横排、竖排等的标点符号。

注意一下， JDK 5 或以下的环境中不支持全角单引号对、双引号替换。在 JDK 6 中由于加入了Pi 和 Pf 两个 Unicode 子属性，因此就能替换掉。

在 JDK 6 以下的版本中可以这样：

 s = s.replaceAll("[\\pP‘’“”]", "");
 */
	
}


