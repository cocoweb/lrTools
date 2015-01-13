package com.foresee.test.loadrunner.lrapi.testng;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.lrapi.lr_x;

public class lr_xTest {
	//ParameterCache xcache = ParameterCache.getInstance();
	  @BeforeTest
	  public void beforeTest() {
		  
	   }

	  @AfterTest
	  public void afterTest() {
	  }

  @Test
  public void end_transaction() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void error_message() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void eval_int() {
	  lr_x.save_int(100,"i1");
	  lr_x.save_int(200, "i2");
	  System.out.println(lr_x.eval_int("{i1}"));
	  AssertJUnit.assertEquals(lr_x.eval_int("{i1}")+lr_x.eval_int("{i2}"), 300);
  }

  @Test
  public void eval_string() {
	  lr_x.save_string("AAA", "aaa");
	  lr_x.save_string("BBB", "bbb");
	  lr_x.save_string("b", "ccc");
	  //暂时不支持部分嵌套参数
	  //"sdf,{bb{ccc}}dfasdf,{aaa}" 不支持
	  //"sdf,{{ccc}bb}dfasdf,{aaa}"支持
	  //"sdf,{ccc}dfasdf,{aaa}"
	  System.out.println(lr_x.eval_string(lr_x.eval_string("sdf,{{ccc}bb}dfasdf,{aaa}")));
	  AssertJUnit.assertEquals(lr_x.eval_string(lr_x.eval_string("sdf,{{ccc}bb}dfasdf,{aaa}")), 
			  "sdf,BBBdfasdf,AAA");

	  lr_x.save_string("AAA1", "aaa");

	  System.out.println(lr_x.eval_string(lr_x.eval_string("sdf,{ccc}dfasdf,{aaa}")));
	  AssertJUnit.assertEquals(lr_x.eval_string(lr_x.eval_string("sdf,{ccc}dfasdf,{aaa}")), 
			  "sdf,bdfasdf,AAA1");
  }

  @Test
  public void message() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void save_int(int i, String string) {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void save_string() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void start_transaction() {
    throw new RuntimeException("Test not implemented");
  }
}
