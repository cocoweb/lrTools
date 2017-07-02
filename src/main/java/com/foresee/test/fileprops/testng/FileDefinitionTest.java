package com.foresee.test.fileprops.testng;



import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.util.exfile.ExtProperties;

public class FileDefinitionTest {
  @BeforeClass
  public void beforeClass() {
  }

  @BeforeTest
  public void beforeTest() {
  }


  @Test
  public void getDefault() {
	  AssertJUnit.assertEquals(FileDefinition.getDefault()[1], "SWZJ.HXZG.SB.BCZZSXGMSB");
    
  }

  @Test
  public void getDefaultParaKey() {
	  AssertJUnit.assertEquals(FileDefinition.getDefaultParaKey(0), "skssqz");
  }

  @Test
  public void getDefaultParaValue() {
	  AssertJUnit.assertEquals(FileDefinition.getDefaultParaValue(1), "2015-01-10");
	
  }

  @Test
  public void getDefaultParaValueByKey() {
	  AssertJUnit.assertEquals(FileDefinition.getDefaultParaValueByKey("sbrq"), "2015-01-10");
  }

  @Test
  public void getDefaultParas() {
	  AssertJUnit.assertEquals(FileDefinition.getDefaultParas().toString(),"{skssqz=2021-01-31, sbrq=2015-01-10, skssqq=2021-01-01}");
  }

  @Test
  public void getExtPropertiesInstance() {
    ExtProperties xx = FileDefinition.getExtPropertiesInstance();
    AssertJUnit.assertNotNull(xx);
  }

  @Test
  public void getFileByName() {
	  AssertJUnit.assertEquals(FileDefinition.getFileByName("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ"), "SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ-增值税小规模申报事前监控及获取期初数据-request.xml");
 }

  @Test
  public void getParaByName() {
	  AssertJUnit.assertEquals(FileDefinition.getParaByName("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ"), "tran_seq,djxh,skssqq:para_skssqq,skssqz:para_skssqz");
  }

  @Test
  public void getValueByName() {
	  AssertJUnit.assertEquals(FileDefinition.getValueByName("default.sbrq"), "2015-01-10");
  }
  
  @Test 
  public void getExcelRow(){
      System.out.println(FileDefinition.getFileByName("keyvalue.excel"));
      System.out.println(FileDefinition.getValueByName("keyvalue.excel"));
  }
  
  @Test
  public void getParasMapByName(){
      System.out.println(FileDefinition.getParasMapByName("keyvalue.excel").toString());
      System.out.println(FileDefinition.getParasMapByName("apitestcase.excel").toString());
  }

}
