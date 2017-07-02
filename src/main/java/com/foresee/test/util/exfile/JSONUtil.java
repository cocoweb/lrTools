package com.foresee.test.util.exfile;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JSONUtil {

	private static Gson gson = new Gson();  
	
	/**
	 * lee 解析map<String,T>
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static Map<String, JsonElement> fromObjMap(JsonElement json) {
		Map<String,JsonElement> map = null;
		if (null != json){
			map = gson.fromJson(json.toString(), new TypeToken<Map<String,JsonElement>>() {}.getType());
		}
		
		return map;
	}
	
	public static  boolean getResultToValueIsString(String json){
		boolean isMap = false;
		try {
			//转换map
			gson.fromJson(json, new TypeToken<Map<String,String>>() {}.getType());
			isMap = true;
		} catch(Exception e) {
			
		}
		return isMap;
	}
	
	public static JSONObject toJSONObject(Object obj){
		JSONObject ret = null;		
		 
		try {
			String json = gson.toJson(obj);
			ret = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static JsonObject toJsonElement(Object obj){
		JsonObject json = (JsonObject) gson.toJsonTree(obj);
		return json;
	}
	
	
	public static String toString(Object obj){
		return toJSONObject(obj).toString();
	}
	/**
	 * wang.wu
	 * @param array
	 * @param classOfT
	 * @return
	 */
	public static <T> List<T> toList(JsonArray array, Class<T> classOfT){
		List<T> list = null;		
		
		if (null != array){
			list = new ArrayList<T>();
			for(JsonElement json:array){
					T obj = gson.fromJson(json, classOfT);
					list.add(obj);					
			}
		}
		
		return list;
	}
	/**
	 * wang.wu
	 * @param jsonElement
	 * @param classOfT
	 * @return
	 */
	public static <T> List<T> toList(JsonElement jsonElement, Class<T> classOfT) {
		List<T> list = null;
		if (jsonElement.isJsonArray()) {
			list = toList(jsonElement.getAsJsonArray(), classOfT);
		} else {
			System.err.println(String.format("Can not set %s to List", jsonElement.getClass().toString()));
		}

		return list;
	}
	
	/**
	 * wang.wu 
	 * @param jsonObj
	 * @param classOfT
	 * @return
	 */
	public static <T> Map<String, T> toMap(JsonObject jsonObj, Class<T> classOfT) {
		Map<String, T> resultMap = null;
		Iterator<Entry<String, JsonElement>> i = jsonObj.entrySet().iterator();

		if (i.hasNext())
			resultMap = new HashMap<String, T>();
		while (i.hasNext()) {
			Entry<String, JsonElement> e = i.next();
			resultMap.put(e.getKey(), JSONUtil.fromJson(e.getValue(), classOfT));
		}
		return resultMap;
	}
	
	public static Map<String, JsonElement> toMap(JsonElement jsonElement) {
			return toMap(jsonElement, JsonElement.class);
	}
	
	public static <T> Map<String, T> toMap(JsonElement jsonElement, Class<T> classOfT) {
		Map<String, T> resultMap = null;
		if (jsonElement.isJsonObject()) {
			resultMap = toMap(jsonElement.getAsJsonObject(), classOfT);
		} else {
			System.err.println(String.format("Can not set %s to Map", jsonElement.getClass().toString()));
		}
		return resultMap;
	}

	public static <T> JsonArray fromList(List<T> list){
		if (null == list){
			return null;
		}
		
		JsonArray array = new JsonArray();		
		for (T t:list){
			JsonElement e = gson.toJsonTree(t);
			array.add(e);
		}
		return array;
	}
	
	public static <T> T fromJSON(JSONObject json, Class<T> classOfT) {
		T obj = null;
		
		if (null != json){
			obj = gson.fromJson(json.toString(), classOfT);
		}
		
		return obj;
	}
	
	public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
		T obj = null;
		
		if (null != json){
			obj = gson.fromJson(json.toString(), classOfT);
		}
		
		return obj;
	}
	
	public static <T> T fromJson(JsonObject json, Class<T> classOfT) {
		T obj = null;
		
		if (null != json){
			obj = gson.fromJson(json.toString(), classOfT);
		}
		
		return obj;
	}
	
	public static List<NameValuePair> toNVPList(Object obj){
		JSONObject json = toJSONObject(obj);
		return toNVPList(json);
	}
	

	public static List<NameValuePair> toNVPList(JSONObject json){
		List<NameValuePair> nvpList = null;
		
		if (null != json){
			nvpList = new ArrayList<NameValuePair>();
			JSONArray names = json.names();
			for(int i=0; i<names.length(); i++){
				try {
					String name = names.get(i).toString();
					BasicNameValuePair nvp = new BasicNameValuePair(name, json.getString(name));
					nvpList.add(nvp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return nvpList;
	}
	

	
	public static JSONObject toJSON(List<NameValuePair> nvpList){
		JSONObject json = new JSONObject();
		for(NameValuePair nvp:nvpList){
			try {
				if (null != nvp.getValue())
					json.append(nvp.getName(), nvp.getValue());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	
	/**
	 * 字符串转换成json格式
	 * 
	 * 
	 * @param s
	 *            字符串
	 * 
	 * 
	 * @return String
	 */
	public static String stringToJson(String s) {
		if (s == null) {
			return nullToJson();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 空对象转换成空字符串
	 * 
	 * @return String
	 */
	public static String nullToJson() {
		return "";
	}

	/**
	 * 对象转换成json格式
	 * 
	 * 
	 * @param obj
	 *            任意对象
	 * 
	 * @return String
	 */
	public static String objectToJson(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof Number) {
			json.append(numberToJson((Number) obj));
		} else if (obj instanceof Boolean) {
			json.append(booleanToJson((Boolean) obj));
		} else if (obj instanceof String) {
			json.append("\"").append(stringToJson(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(arrayToJson((Object[]) obj));
		} else if (obj instanceof List<?>) {
			json.append(listToJson((List<?>) obj));
		} else if (obj instanceof Map<?, ?>) {
			json.append(mapToJson((Map<?, ?>) obj));
		} else if (obj instanceof Set<?>) {
			json.append(setToJson((Set<?>) obj));
		} else {
			json.append(beanToJson(obj));
		}
		return json.toString();
	}

	/**
	 * 数值型变量转换成json
	 * 
	 * 
	 * @param number
	 *            数值
	 * 
	 * 
	 * @return String
	 */
	public static String numberToJson(Number number) {
		return number.toString();
	}

	/**
	 * 布尔型变量转换成json
	 * 
	 * 
	 * @param bool
	 *            布尔变量
	 * 
	 * @return String
	 */
	public static String booleanToJson(Boolean bool) {
		return bool.toString();
	}

	/**
	 * bean对象转换成json 区别于object
	 * 
	 * @param bean
	 *            bean对象
	 * @return String
	 */
	public static String beanToJson(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJson(props[i].getName());
					String value = objectToJson(props[i].getReadMethod()
							.invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * List To Json
	 * 
	 * @param list
	 *            list对象
	 * @return String
	 */
	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.delete(json.length() - 1, json.length());
		}
		return json.append("]").toString();
	}

	/**
	 * 数组转换成json
	 * 
	 * @param array
	 *            对象数组
	 * @return String
	 */
	public static String arrayToJson(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * map字典转换成json
	 * 
	 * @param map
	 *            map对象
	 * @return String
	 */
	public static String mapToJson(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(objectToJson(key));
				json.append(":");
				json.append(objectToJson(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * set集合转换成json
	 * 
	 * @param set
	 *            集合对象
	 * @return String
	 */
	public static String setToJson(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

}
