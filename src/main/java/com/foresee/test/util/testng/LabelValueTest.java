package com.foresee.test.util.testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.foresee.test.util.LabelValue;

public class LabelValueTest {
  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }


  @Test
  public void BuildFromString() {
      LabelValue xx = LabelValue.BuildFromString("aaa=  AAA");
      System.out.println(xx);
      
      System.out.println(LabelValue.BuildListFromString("aaa=AAA AA;bbb=B BBBBB;cccc=CCC CCCC"));
      System.out.println(LabelValue.BuildListFromString("aaa=AAA AA|bbb=B BBBBB|cccc=CCC CCCC"));
      System.out.println(LabelValue.BuildListFromString("aaa=AAA AA,bbb=B BBBBB,cccc=CCC CCCC"));
      System.out.println(LabelValue.BuildListFromString("aaa=AAA AA,bbb=B BBBBB|cccc=CCC CCCC;d:99999;;;"));
      
  }
}
