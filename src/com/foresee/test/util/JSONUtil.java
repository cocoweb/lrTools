package com.foresee.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
}
