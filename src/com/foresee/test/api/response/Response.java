package com.foresee.test.api.response;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.vipshop.api.factory.HttpResponseFactory;

public class Response {

	public static HttpResponse execute(URI request, String method, String output, Header[] headers){
		if ("GET".equals(method.toUpperCase())){
			return HttpResponseFactory.get(request, headers);
		}
		if ("POST".equals(method.toUpperCase())){
			return HttpResponseFactory.post(request);
		}
		if ("PUT".equals(method.toUpperCase())){
			return HttpResponseFactory.put(request, output);
		}
		
		return null;
	}
	
	public static void get(String url){
		try {
			URI request = new URI(url);
			execute(request, "GET",null, null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HttpResponse get(URI request){
		return execute(request, "GET", null, null);
	}
	
	public static HttpResponse post(URI request){
		return execute(request, "POST", null, null);
	}
	
	public static HttpResponse put(URI request, String output){
		return execute(request, "PUT", output, null);
	}
	
	public static HttpResponse get(URI request, Header[] headers){
		return execute(request, "GET", null, headers);
	}

}
