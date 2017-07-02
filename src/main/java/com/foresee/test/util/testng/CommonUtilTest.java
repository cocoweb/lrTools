package com.foresee.test.util.testng;

import org.testng.annotations.Test;

import com.foresee.test.util.CommonUtil;

public class CommonUtilTest {
  @Test
  public void f() {
      CommonUtil.showJVM();
      CommonUtil.showSystemProp();
  }
}
