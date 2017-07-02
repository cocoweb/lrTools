package com.foresee.test.util.http.response;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.foresee.test.util.code.EncodeUtils;
import com.foresee.test.util.http.HttpResponseFactory;
import com.foresee.test.util.http.URIUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JSONResponse {
	
	private static int statusCode;
	private static JSONObject jsonResult;
	private static HttpResponse response;

	
	
	public static JsonElement getJsonElement(URI request){
		response = HttpResponseFactory.get(request);
		Gson gson = new Gson();
		JsonElement result = gson.toJsonTree(getResult(response, false));
		return result;
	}
		
	public static JSONObject getJsonResult(URI request) {
		response = HttpResponseFactory.get(request);
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONObject getJsonResult(URI request,CookieStore cookiestore) {
		response = HttpResponseFactory.get(request,cookiestore);
		return parseJSONObject(getResult(response, false));
	}
	
	

	
	public static String getObjectResult(URI request) {
		response = HttpResponseFactory.get(request);
		return getResult(response, false);
	}
	/**
	 * url有参数 返回值是String 类似于[a,b,c]
	 * @param request
	 * @param content
	 * @return
	 */
	public static String getObjectResult(URI request, List<NameValuePair> parameters) {
		response = HttpResponseFactory.post(request, parameters);
		String result = getResult(response, false);
	if(result.startsWith("[")&&result.endsWith("]")){
			
			result = result.substring(1, result.length()-1);
		}
		return result;
	}
	
	public static JSONObject getJsonResult(URI request, Header[] headers){
		response = HttpResponseFactory.get(request, headers);
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONArray getJsonArrayResult(URI request, Header[] headers){
		response = HttpResponseFactory.get(request, headers);
		return parseJSONArray(getResult(response, false));
	}
	
	public static JSONArray getJsonArrayResult(URI request){
		response = HttpResponseFactory.get(request);
		return parseJSONArray(getResult(response, false));
	}
	
	public static JSONObject putJsonResult(URI request, JSONObject content, Header[] headers) {
		response = HttpResponseFactory.put(request, content.toString(), headers);
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONObject putJsonResult(URI request, JSONObject content) {
		response = HttpResponseFactory.put(request, content.toString());
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONObject postJsonResult(URI request) {
		response = HttpResponseFactory.post(request);
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONObject postJsonResult(URI request, String content) {
		response = HttpResponseFactory.post(request, content);
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONArray postJsonArray(URI request, String content) {
		response = HttpResponseFactory.post(request, content);
		return parseJSONArray(getResult(response, false));
	}
	
	public static JSONObject post(URI request, List<NameValuePair> parameters){
		response = HttpResponseFactory.post(request, parameters);
		return parseJSONObject(getResult(response, false));
	}
	
	public static JSONObject post(URI request, Header[] headers, List<NameValuePair> parameters){
		response = HttpResponseFactory.post(request, parameters);
		return parseJSONObject(getResult(response, true));
	}
	public static JSONArray postFormJsonArray(URI request, List<NameValuePair> parameters){
		response = HttpResponseFactory.post(request, parameters);			
		return parseJSONArray(getResult(response, true));
	}
		
	public static JSONObject postJsonResult(URI request, Header[] headers, boolean isURLEncoder) {
		response = HttpResponseFactory.post(request, headers);
		return parseJSONObject(getResult(response, isURLEncoder));
	}

	public static int getStatusCode() {
		return statusCode;
	}

	public static JSONObject getJsonResult() {
		return jsonResult;
	}

	public static HttpResponse getResponse() {
		return response;
	}

	private static String getResult (HttpResponse res, boolean isURLEncoder){
		statusCode = res.getStatusLine().getStatusCode();
		//System.out.println("StatusCode:"+statusCode);
		String result = null;
		
		// setup JSONArray
		try {
			if (200 == statusCode){
				InputStream content = res.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content, "UTF-8"));
				result = reader.readLine();
				
				if(isURLEncoder){
					result = URIUtil.decodeUrl(result);
				}
				
				System.out.println("Response:"+EncodeUtils.decodeUnicode(result));
			}
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
		
	private static JSONArray parseJSONArray(String result){
		JSONArray j = null; 
		if (null == result){
			return j;
		}
		
		try {
			j = new JSONArray(result);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return j;
	}
	
	private static JSONObject parseJSONObject(String result){
		JSONObject j = null; 
		
		try {
			j = new JSONObject(result);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return j;
	}

}
