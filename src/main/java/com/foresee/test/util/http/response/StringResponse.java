package com.foresee.test.util.http.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.foresee.test.util.http.HttpResponseFactory;


public class StringResponse {
	
	private static String execute(URI request, String method, String output){
		HttpResponse response = HttpResponseFactory.getInstance();
		String ret = null;
		
		if ("GET".equals(method.toUpperCase())){
			response = HttpResponseFactory.get(request);
		}
		if ("POST".equals(method.toUpperCase())){
			response = HttpResponseFactory.post(request);
		}
		if ("PUT".equals(method.toUpperCase())){
			response = HttpResponseFactory.put(request, output);
		}
		
		try {
			InputStream content = response.getEntity().getContent();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(content, "UTF-8"));
			ret = IOUtils.toString(content).trim();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(ret);
		return ret;
	}
	
	
	public static String getInstance(URI request) {
		return execute(request, "GET", null);
	}
	
	public static String postInstance(URI request) {
		return execute(request, "POST", null);

	}

}
