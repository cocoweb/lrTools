package com.foresee.test.loadrunner.helper.testng;

import org.testng.annotations.Test;

import com.foresee.test.loadrunner.helper.ApiMethod;
import com.gargoylesoftware.htmlunit.HttpMethod;

public class ApiMethodTest {


  @Test
  public void fromHttpMethod() {
      ApiMethod xx = ApiMethod.fromHttpMethod(HttpMethod.POST);
      System.out.println(xx);
  }

  @Test
  public void fromString() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void toHttpMethod() {
    throw new RuntimeException("Test not implemented");
  }
}
