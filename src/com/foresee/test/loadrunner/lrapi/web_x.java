package com.foresee.test.loadrunner.lrapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.foresee.test.http.HttpException;
import com.foresee.test.util.lang.StringUtil;

import static com.foresee.test.loadrunner.lrapi.lr_x.eval_string;

public class web_x extends I_web {
	
	

	public web_x() {
		// TODO Auto-generated constructor stub
	}
	public static int link(String stepName, String textName, String[] options)
			throws HttpException, lrapi.exceptions.HttpException {
		// TODO 直接转发LoadRunner的方法

		return 0;
	}
	
//	public static HttpRequest createRequest(String[] optionsAndData){
//		
//	}
	
	
	public static <T extends HttpMessage> void setHeaders (T httpget, String[] options){
		
		for(int i=0;i< options.length;i++){
			if (options[i].indexOf(EXTRARES)==0 ||options[i].indexOf(LAST)==0){
				break;
			}else{
				String[] strs = StringUtil.parsarKVStr(options[i]);
				if( strs.length>1){
				    ((HttpMessage) httpget).setHeader(eval_string(strs[0]),eval_string(strs[1]));
				}else{
					((HttpMessage) httpget).setHeader(eval_string(strs[0]), "");
				}
			}
		}
		
	}
	public static void showHeader(HttpResponse response){
		HeaderIterator iter = response.headerIterator();
		
		while (iter.hasNext()){
			System.out.println(iter.next().toString());
		}

	}
	
	public static void showEntity(HttpResponse response) throws ParseException, IOException{
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			long len = entity.getContentLength();
            System.out.println("Response content length: " + entity.getContentLength());  
			if (len != -1 && len < 2048) {
				System.out.println(EntityUtils.toString(entity));
			} else {
				 InputStream inSm = entity.getContent();  
		        Scanner inScn = new Scanner(inSm);  
		        while (inScn.hasNextLine()) {  
		            System.out.println(inScn.nextLine());  
		        }  
			}
		}
		
	}
	
	public static void showContext(HttpContext localContext){
		CookieOrigin cookieOrigin = (CookieOrigin) localContext.getAttribute(
				ClientContext.COOKIE_ORIGIN);
		System.out.println("Cookie origin: " + cookieOrigin);
//		CookieSpec cookieSpec = (CookieSpec) localContext.getAttribute(
//				ClientContext.COOKIE_SPEC);
//		System.out.println("Cookie spec used: " + cookieSpec);
		
		
		CookieStore cookieStore = (CookieStore) localContext.getAttribute(
				ClientContext.COOKIE_STORE);
		System.out.println("Cookie Store used: " + cookieStore);

	}
	
	public static void showContent(HttpResponse response) throws IllegalStateException, IOException{
        InputStream inSm = response.getEntity().getContent();  
        Scanner inScn = new Scanner(inSm);  
        while (inScn.hasNextLine()) {  
            System.out.println(inScn.nextLine());  
        }  
        
        //response.headerIterator()
		
	}
	static HttpClient httpClient = new DefaultHttpClient();
	// Create a local instance of cookie store
	static CookieStore cookieStore = new BasicCookieStore();
	// Create local HTTP context
	static HttpContext localContext = new BasicHttpContext();
	
	public static int url(String stepName, String urlAddress, String[] options)
			throws HttpException {
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		//cookieStore.addCookie(cookie);
		
	    try {  
	        HttpGet httpget = new HttpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
	        setHeaders(httpget,options);

	        HttpResponse response = httpClient.execute(httpget,localContext);  
	        HttpEntity entity = response.getEntity();  
	  
	        System.out.println("----------------------------------------");  
	        System.out.println(response.getStatusLine());  
	        if (entity != null) {  
	            System.out.println("Response content length: " + entity.getContentLength());  
	        }  
	        System.out.println("----------------------------------------");  
	        showHeader(response);
	        showContent(response);
	        showContext(localContext);
	        httpget.abort();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        // When HttpClient instance is no longer needed,  
	        // shut down the connection manager to ensure  
	        // immediate deallocation of all system resources  
	        //httpClient.getConnectionManager().shutdown();  
	    } 		
		
		return 0;
	}


	
	public static int custom_request(String stepName, String urlAddress,
			String[] optionsAndData) throws HttpException,
			lrapi.exceptions.HttpException {
		HttpClient httpClient = new DefaultHttpClient();  
	    try {  
	        //HttpGet httpget = new HttpGet(StringUtil.parsarKVStrValue(urlAddress));  
	        HttpPost httpost = new HttpPost(StringUtil.parsarKVStrValue(urlAddress));
	        
	        setHeaders(httpost, optionsAndData);
	  
	        HttpResponse response = httpClient.execute(httpost,localContext);  
	        HttpEntity entity = response.getEntity();  
	  
	        System.out.println("----------------------------------------");  
	        System.out.println(response.getStatusLine());  
	        if (entity != null) {  
	            System.out.println("Response content length: " + entity.getContentLength());  
	        }  
	        System.out.println("----------------------------------------");  
	  
	        //showContent(response);
	        // Do not feel like reading the response body  
	        // Call abort on the request object  
	        httpost.abort();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        // When HttpClient instance is no longer needed,  
	        // shut down the connection manager to ensure  
	        // immediate deallocation of all system resources  
	        httpClient.getConnectionManager().shutdown();  
	    } 		
		
		
		
		// TODO 包装LoadRunner
		//return lrapi.web.custom_request(stepName, urlAddress, optionsAndData);
		
		return 0;

	}

}
