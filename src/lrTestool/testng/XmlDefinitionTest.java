package lrTestool.testng;

import lrTestool.XmlDefinition;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.util.exfile.ExtProperties;

public class XmlDefinitionTest {
  @BeforeClass
  public void beforeClass() {
  }

  @BeforeTest
  public void beforeTest() {
  }


  @Test
  public void getDefault() {
	  AssertJUnit.assertEquals(XmlDefinition.getDefault()[1], "SWZJ.HXZG.SB.BCZZSXGMSB");
    
  }

  @Test
  public void getDefaultParaKey() {
	  AssertJUnit.assertEquals(XmlDefinition.getDefaultParaKey(0), "skssqz");
  }

  @Test
  public void getDefaultParaValue() {
	  AssertJUnit.assertEquals(XmlDefinition.getDefaultParaValue(1), "2015-01-10");
	
  }

  @Test
  public void getDefaultParaValueByKey() {
	  AssertJUnit.assertEquals(XmlDefinition.getDefaultParaValueByKey("sbrq"), "2015-01-10");
  }

  @Test
  public void getDefaultParas() {
	  AssertJUnit.assertEquals(XmlDefinition.getDefaultParas().toString(),"{skssqz=2021-01-31, sbrq=2015-01-10, skssqq=2021-01-01}");
  }

  @Test
  public void getExtPropertiesInstance() {
    ExtProperties xx = XmlDefinition.getExtPropertiesInstance();
    AssertJUnit.assertNotNull(xx);
  }

  @Test
  public void getFileByName() {
	  AssertJUnit.assertEquals(XmlDefinition.getFileByName("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ"), "SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ-增值税小规模申报事前监控及获取期初数据-request.xml");
 }

  @Test
  public void getParaByName() {
	  AssertJUnit.assertEquals(XmlDefinition.getParaByName("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ"), "tran_seq,djxh,skssqq:para_skssqq,skssqz:para_skssqz");
  }

  @Test
  public void getValueByName() {
	  AssertJUnit.assertEquals(XmlDefinition.getValueByName("default.sbrq"), "2015-01-10");
  }

}
