package com.foresee.test.fileprops.testng;



import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.fileprops.lrTools;

public class lrToolsTest {
	@BeforeTest
	public void beforeTest() {
	}

	@Test
	public void getDefault() {
		AssertJUnit.assertEquals(lrTools.getDefault(),
				"SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ");
	}

	@Test
	public void getDefaultParaKey() {
		AssertJUnit.assertEquals(lrTools.getDefaultParaKey(1), "sbrq");
	}

	@Test
	public void getDefaultParaValue() {
		AssertJUnit.assertEquals(lrTools.getDefaultParaValue(1), "2015-01-10");
	}

	@Test
	public void getDefaultParaValueByKey() {
		AssertJUnit.assertEquals(lrTools.getDefaultParaValueByKey("sbrq"),
				"2015-01-10");
	}

	@Test
	public void getDefaults() {
		AssertJUnit.assertEquals(lrTools.getDefaults()[1], "SWZJ.HXZG.SB.BCZZSXGMSB");
	}

	@Test
	public void getTranNameByKey() {
		AssertJUnit.assertEquals(
				lrTools.getTranNameByKey("SWZJ.HXZG.SB.BCZZSXGMSB"),
				"保存增值税小规模申报");
	}

	// @Test
	// public void loadFile() {
	// Assert.assertEquals(lrTools.loadFile("SWZJ.HXZG.SB.BCZZSXGMSB-保存增值税小规模申报-request.xml"),
	// "保存增值税小规模申报");
	// }

	@Test
	public void loadXmlByKey() {
		String sDefault = lrTools.getDefault();
		System.out.println(lrTools.loadXmlByKey(sDefault));
	}

}
