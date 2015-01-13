package com.foresee.test.loadrunner.lrapi.testng;


import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.foresee.test.loadrunner.lrapi.ParameterCache;

public class ParameterCacheTest {
	ParameterCache xcache = ParameterCache.getInstance();

@BeforeTest
  public void beforeTest() {
      xcache.put("aaa", "AAA");
      xcache.put("bbb", "BBB");
   }

  @AfterTest
  public void afterTest() {
  }


  @Test
  public void getInstance() {
	  
	  System.out.println(xcache.getMapCache().toString());
      AssertJUnit.assertEquals(xcache.get("aaa"), "AAA");
    
  }
}
