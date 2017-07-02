package com.foresee.test.util.testng;

import java.io.File;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.foresee.test.util.io.FileUtil;

public class FileUtilTest {
  @Test
  public void lookupFileInClasspath() {
      
      File xx = FileUtil.lookupFileInClasspath("接口测试用例.xls");
      System.out.println( xx.getAbsolutePath());
  }
  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

}
