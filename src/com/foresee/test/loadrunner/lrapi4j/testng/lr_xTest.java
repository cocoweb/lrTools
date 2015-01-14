package com.foresee.test.loadrunner.lrapi4j.testng;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.lrapi4j.lr;

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
	  lr.save_int(100,"i1");
	  lr.save_int(200, "i2");
	  System.out.println(lr.eval_int("{i1}"));
	  AssertJUnit.assertEquals(lr.eval_int("{i1}")+lr.eval_int("{i2}"), 300);
  }

  @Test
  public void eval_string() {
	  lr.save_string("AAA", "aaa");
	  lr.save_string("BBB", "bbb");
	  lr.save_string("b", "ccc");
	  //暂时不支持部分嵌套参数
	  //"sdf,{bb{ccc}}dfasdf,{aaa}" 不支持
	  //"sdf,{{ccc}bb}dfasdf,{aaa}"支持
	  //"sdf,{ccc}dfasdf,{aaa}"
	  System.out.println(lr.eval_string(lr.eval_string("sdf,{{ccc}bb}dfasdf,{aaa}")));
	  AssertJUnit.assertEquals(lr.eval_string(lr.eval_string("sdf,{{ccc}bb}dfasdf,{aaa}")), 
			  "sdf,BBBdfasdf,AAA");

	  lr.save_string("AAA1", "aaa");

	  System.out.println(lr.eval_string(lr.eval_string("sdf,{ccc}dfasdf,{aaa}")));
	  AssertJUnit.assertEquals(lr.eval_string(lr.eval_string("sdf,{ccc}dfasdf,{aaa}")), 
			  "sdf,bdfasdf,AAA1");
  }

  @Test
  public void message() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void save_int() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void save_string() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void start_transaction() {
      lr.start_transaction("afdlajsdflkjasldfkj");
      
      lr.think_time(0.5);
      lr.end_transaction("afdlajsdflkjasldfkj", lr.PASS);
      
  }
}
