package lrTestool.test;

import org.testng.annotations.Test;
import org.testng.Assert;
import lrTestool.XmlDefinition;

public class XmlDefinitionTest extends XmlDefinition {

	@Test
	public void testGetFileByName() {
		String sss = getFileByName("SWZJ.HXZG.SB.BCZZSXGMSB");
		System.out.println(sss);
	}

	@Test
	public void testGetParaByName() {
		Assert.fail("Not yet implemented");
	}
	@Test
	public void testGetDefaultParas() {
		String sss = getDefaultParas().toString();
	    System.out.println(sss);
	}

	@Test
	public void testGetDefault() {
		String sss = getDefault()[1];
	    System.out.println(sss);
	}

	@Test
	public void testGetValueByName() {
		Assert.fail("Not yet implemented");
	}

}
