package com.foresee.test.util.testng;

import org.testng.annotations.Test;

import com.foresee.test.util.lang.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;

public class StringUtilTest {
  @BeforeClass
  public void beforeClass() {
  }
  
  public List aslist(String ss){
	  ss = ss.replaceAll("[、，,;；/\n]", ",");
	  String[] astr = StringUtil.split(ss,",");
	  return Arrays.asList(astr);
  }


  @Test
  public void split() {
	  System.out.println(aslist("aaa,bbb,ccc"));
	  
	  System.out.println(aslist("aaa，bbb，ccc"));
	  System.out.println(aslist("aaa、bbb、ccc"));
	  System.out.println(aslist("aaa；bbb；ccc"));
	  System.out.println(aslist("aaa;bbb;ccc"));
	  
  }
}
