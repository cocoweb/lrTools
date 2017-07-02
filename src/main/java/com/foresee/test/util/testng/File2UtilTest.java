package com.foresee.test.util.testng;

import java.io.File;
import java.util.Collection;

import org.testng.annotations.Test;

import com.foresee.test.util.io.File2Util;

public class File2UtilTest {

  @Test
  public void getAllFiles() {
      String sFilter = "20150823";
      Collection<File> clFiles = File2Util.getAllFiles("p:/tmp/xls", "xls,xlsx,"+sFilter);
      
      System.out.println(clFiles);
  }
}
