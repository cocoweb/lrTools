package com.foresee.test.util.jodd.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jodd.props.Props;
import jodd.props.PropsUtil;

public class PropsTest {
	Props xprop;
	
	  @BeforeClass
	  public void beforeClass() {
		  
		  xprop =new Props();
		  
		  //xprop.load(new File("D:\Java\workspace\LrTools\src\file.properties"));
		  PropsUtil.loadFromClasspath(xprop, "/file.properties");
		  
	  }
	  @Test
	  public void f() {
		  System.out.println(xprop.getValue("SWZJ"));
		  System.out.println(xprop.countTotalProperties());
		  
	  }

}
