package com.foresee.xdeploy.utils.base.testng;

import org.testng.annotations.Test;

import com.foresee.test.util.exfile.ExtProperties;
import com.foresee.xdeploy.utils.base.ExtPropertiesFactory;
import com.foresee.xdeploy.utils.base.ParamPropValue;

public class ExtPropertiesFactoryTest {
	ExtProperties exprop;

  @Test
  public void getExtPropertiesInstance() {
	  String propFileName = "/file.properties";
	  exprop = ExtPropertiesFactory.getExtPropertiesInstance(propFileName,exprop);
	  
	  System.out.println(exprop.size());
	  System.out.println(exprop.toString());
	  
	  exprop = ExtPropertiesFactory.loadxProperties("/ejbclient.properties", exprop);
	  System.out.println(exprop.size());
	  System.out.println(exprop.toString());
	  
  }
  
  @Test
  public void ParamPropValueLoad(){
	  ParamPropValue ppv = new ParamPropValue("/file.properties");
	  
	  System.out.println(ppv.toString());
	  
	  ppv.loadPropFile("/ejbclient.properties");
	  System.out.println(ppv.toString());
  }
}
