package com.foresee.test.http.testng;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

public class httpDemotest {
	void showHeader(HttpResponse response){
		HeaderIterator iter = response.headerIterator();
		
		while (iter.hasNext()){
			System.out.println(iter.next().toString());
		}

	}
	
	void showEntity(HttpResponse response) throws ParseException, IOException{
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
	
	void showContent(HttpResponse response) throws IllegalStateException, IOException{
        InputStream inSm = response.getEntity().getContent();  
        Scanner inScn = new Scanner(inSm);  
        while (inScn.hasNextLine()) {  
            System.out.println(inScn.nextLine());  
        }  
		
	}
	
	@Test
	
	public void setheader() {  
	    HttpClient httpClient = new DefaultHttpClient();  
	    try {  
	        HttpGet httpget = new HttpGet("http://www.iteye.com");  
	  
	        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
	        httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");  
	        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1)");  
	        httpget.setHeader("Accept-Encoding", "gzip, deflate");  
	        httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
	        httpget.setHeader("Host", "www.iteye.com");  
	        httpget.setHeader("Connection", "Keep-Alive");  
	  
	        HttpResponse response = httpClient.execute(httpget);  
	        HttpEntity entity = response.getEntity();  
	  
	        System.out.println("----------------------------------------");  
	        System.out.println(response.getStatusLine());  
	        if (entity != null) {  
	            System.out.println("Response content length: " + entity.getContentLength());  
	        }  
	        System.out.println("----------------------------------------");  
	  
	        showContent(response);
	        // Do not feel like reading the response body  
	        // Call abort on the request object  
	        httpget.abort();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        // When HttpClient instance is no longer needed,  
	        // shut down the connection manager to ensure  
	        // immediate deallocation of all system resources  
	        httpClient.getConnectionManager().shutdown();  
	    }  
	}  
	@Test
	
    public  void eapache( ) {  
        //创建默认的 HttpClient 实例  
        HttpClient httpClient = new DefaultHttpClient();  
        try {  
            //创建 httpUriRequest 实例  
            HttpGet httpGet = new HttpGet("http://www.apache.org/");  
            System.out.println("uri=" + httpGet.getURI());  
  
            //执行 get 请求  
            HttpResponse httpResponse = httpClient.execute(httpGet);  
  
            //获取响应实体  
            HttpEntity httpEntity = httpResponse.getEntity();  
            //打印响应状态  
            System.out.println(httpResponse.getStatusLine());  
            if (httpEntity != null) {  
                //响应内容的长度  
                long length = httpEntity.getContentLength();  
                //响应内容  
                String content = EntityUtils.toString(httpEntity);  
  
                System.out.println("Response content length:" + length);  
                System.out.println("Response content:" + content);  
            }  
  
            //有些教程里没有下面这行  
            httpGet.abort();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            //关闭连接，释放资源  
            httpClient.getConnectionManager().shutdown();  
        }  
    }  

	
	@Test
	public void f() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://www.baidu.com/");
		
		
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			
			showHeader(response);
			
			showEntity(response);
			
			//System.out.println(response.toString());
			showContent(response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
