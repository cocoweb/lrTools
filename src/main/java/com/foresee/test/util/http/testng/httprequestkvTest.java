package com.foresee.test.util.http.testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.foresee.test.util.http.HttpRequestKV;

public class httprequestkvTest {
  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

  @Test
  public void f() {
	 // HttpRequestKV request = HttpRequestKV.get("https://google.com");
	//Configure proxy
	//request.useProxy("localhost", 8888);
	//Optional proxy basic authentication
	//request.proxyBasic("username", "p4ssw0rd");
	  String contentType = HttpRequestKV
			  .get("http://baidu.com")
              .accept("application/json") //Sets request header
              .contentType(); //Gets response header
System.out.println("Response content type was " + contentType);
  }
}
