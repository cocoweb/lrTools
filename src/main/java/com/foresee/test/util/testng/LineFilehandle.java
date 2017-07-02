package com.foresee.test.util.testng;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.codehaus.mojo.unix.util.line.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class LineFilehandle {
	LineFile xlineFile;
  @Test
  public void f() {
	  Iterator<String> iter = xlineFile.iterator();
	  
	  while(iter.hasNext()){
		  
		  System.out.println(iter.next());
		  
	  }
	  
	  
	  
  }
  @BeforeTest
  public void beforeTest() throws IOException {
	File file = new File("f:/xxxx.txt");
	  
	  xlineFile = LineFile.fromFile(file);
  }

  @AfterTest
  public void afterTest() {
	  
  }

}
