package com.foresee.test.api.factory;


import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpResponseFactory {
	
	private static HttpClient httpclient = null;
	private static DefaultHttpClient defaulthttpclient = null;
	
	private static HttpResponse response = null;
	
	private static final int TIMEOUT = 10000;
		
	private static final String CHARSET_ENCODING = "UTF-8";
	
	public static HttpResponse getInstance (){
		if (null == response){
			response = new BasicHttpResponse(
					new ProtocolVersion("HTTP_ERROR", 1, 1), 500, "ERROR");
		}		
		return response;
	}
	
	public static String getHeader(URI request, Header[] headers, String key){
		String value = null;
		
		HttpResponse response = HttpResponseFactory.get(request, headers);
		if (null != key){
			Header[] header = response.getHeaders(key);
			value = header[0].getValue();
		}
		
		return value;
	}
	
	private static HttpResponse execute(URI request, String method, String output, Header[] headers, List<NameValuePair> parameters) {
		
		System.out.println(request.toString());
		
		if(null != output)
			System.out.println(output);

		HttpParams params = new BasicHttpParams();
		
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
		
		httpclient = new DefaultHttpClient(params);
		response = getInstance();
		
		try {

			if ("GET".equals(method.toUpperCase())){
				HttpGet hg = new HttpGet(request);
				if (null != headers)
					hg.setHeaders(headers);
				response = (HttpResponse) httpclient.execute(hg);
			} 
			
			if ("POST".equals(method.toUpperCase())){
				HttpPost httppost = new HttpPost(request);
				
				if (null != headers)
					httppost.setHeaders(headers);
				
				if (null != output){
					byte[] b = output.getBytes(CHARSET_ENCODING);
					ByteArrayEntity requestEntity = new ByteArrayEntity( b );
					httppost.setEntity(requestEntity);
				}
				
				if (null != parameters){
					UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
					requestEntity.setContentType("application/x-www-form-urlencoded");
					httppost.setEntity(requestEntity);
				}
				
				response = httpclient.execute(httppost);
				
			} 
			
			if ("PUT".equals(method.toUpperCase())){
				HttpPut p = new HttpPut(request);
				if (null != headers)
					p.setHeaders(headers);
				if (null != output){
					byte[] b = output.getBytes(CHARSET_ENCODING);
					ByteArrayEntity requestEntity = new ByteArrayEntity( b );
					p.setEntity(requestEntity);
				}
				response = httpclient.execute(p);
			}
			
			if ("DELETE".equals(method.toUpperCase())){
				HttpDelete hd = new HttpDelete(request);
				if (null != headers)
					hd.setHeaders(headers);
				response = httpclient.execute(hd);
			}
			
//			System.out.println("Status Code:" + response.getStatusLine().getStatusCode());
//			assertTrue("HTTP Status Code is: " + response.getStatusLine().getStatusCode() + " - " + request.toString(),200 == response.getStatusLine().getStatusCode());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			httpclient.getConnectionManager().shutdown();
		}
		
		return response;
		
	}
	
	private static HttpResponse execute(URI request,  String method, String output, Header[] headers, List<NameValuePair> parameters,CookieStore cookieStore) {
		
		System.out.println(request.toString());
		
		if(null != output)
			System.out.println(output);

		HttpParams params = new BasicHttpParams();
		
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
		
		
		defaulthttpclient=new DefaultHttpClient();
		defaulthttpclient.setCookieStore(cookieStore);
		
		response = getInstance();
				
		try {

			if ("GET".equals(method.toUpperCase())){
				HttpGet hg = new HttpGet(request);
				if (null != headers)
					hg.setHeaders(headers);
				response = (HttpResponse) defaulthttpclient.execute(hg);
				System.out.println("get");
				
			    //获取所有Cookies
	            List<Cookie> cookies = cookieStore.getCookies();
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("Local cookie: " + cookies.get(i));
	            }

			} 

			
			System.out.println("Status Code:" + response.getStatusLine().getStatusCode());
			//assertTrue("HTTP Status Code is: " + response.getStatusLine().getStatusCode() + " - " + request.toString(),200 == response.getStatusLine().getStatusCode());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			defaulthttpclient.getConnectionManager().shutdown();
		}
		
		return response;
		
	}
	

	
	public static HttpResponse delete(URI request){
		return execute(request,"DELETE", null, null, null);
	}
	
	public static HttpResponse delete(URI request, Header[] headers){
		return execute(request,"DELETE", null, headers, null);
	}
	
	public static HttpResponse post(URI request){		
		return execute(request,"POST", null, null, null);
	}
	
	public static HttpResponse post(URI request, Header[] headers){		
		return execute(request,"POST", null, headers, null);
	}
	
	public static HttpResponse post(URI request, List<NameValuePair> parameters){
		return execute(request, "POST", null, null, parameters);
	}
	
	public static HttpResponse post(URI request, Header[] headers, List<NameValuePair> parameters){
		return execute(request, "POST", null, headers, parameters);
	}
	
	public static HttpResponse post(URI request, String output){		
		return execute(request,"POST", output, null, null);
	}
	
	public static HttpResponse post(URI request, String output, Header[] headers){		
		return execute(request,"POST", output, headers, null);
	}
	
	public static HttpResponse put(URI request, String output){
		return execute(request,"PUT", output, null, null);
	}
	
	public static HttpResponse put(URI request, String output, Header[] headers){
		return execute(request,"PUT", output, headers, null);
	}
	
	public static HttpResponse get(URI request){		
		return execute(request,"GET", null, null, null);
	}
	
	public static HttpResponse get(URI request,CookieStore cookieStore){		
		return execute(request,"GET", null, null, null,cookieStore);
	}
	

	
	public static HttpResponse get(URI request, Header[] headers){		
		return execute(request,"GET", null, headers, null);
	}

}
