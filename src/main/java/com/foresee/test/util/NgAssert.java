package com.foresee.test.util;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class NgAssert extends Assertion {
	  @Override
	  public void onBeforeAssert(IAssert<?> a) {
	    System.out.println(a.getActual());
	  }

}
