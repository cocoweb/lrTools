package com.foresee.test.util.http.response;

import static org.testng.AssertJUnit.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.foresee.test.util.http.HttpResponseFactory;


public class XMLResponse {
	
	private static Element execute(URI request, String method, String output, Header[] headers){
		HttpResponse res = null;
		Element xml = null;
		if ("GET".equals(method.toUpperCase())){
			res = HttpResponseFactory.get(request, headers);
		}
		if ("POST".equals(method.toUpperCase())){
			res = HttpResponseFactory.post(request, headers);
		}
		if ("PUT".equals(method.toUpperCase())){
			res = HttpResponseFactory.put(request, output, headers);
		}
		
		try {
			
			// initiate responseXML
			SAXReader reader = new SAXReader();
			
			InputStream xmlStr = res.getEntity().getContent();
			assertNotNull(xmlStr);
			
			Document xmlDoc = reader.read(xmlStr);
			assertNotNull(xmlDoc);
			
			xml = xmlDoc.getRootElement();
			assertNotNull(xml);
			//System.out.println(rootXML.asXML());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(xml.asXML());
		return xml;
		
	}
	
	public static Element get(URI request){
		return execute(request, "GET", null, null);
	}
	
	public static Element post(URI request){
		return execute(request, "POST", null, null);
	}
	
	public static Element post(URI request, String output){
		return execute(request, "POST", output, null);
	}
	
	public static Element put(URI request, String output){
		return execute(request, "PUT", output, null);
	}
	
	public static Element get(URI request, Header[] headers){
		return execute(request, "GET", null, headers);
	}

}
