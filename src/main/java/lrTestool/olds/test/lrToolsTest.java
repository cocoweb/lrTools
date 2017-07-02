package lrTestool.test;

import org.testng.annotations.Test;
import org.testng.Assert;
import lrTestool.lrTools;

public class lrToolsTest extends lrTools {
	
	@Test
	public void testMain(){
		String sDefault =lrTools.getDefault();
		System.out.println(lrTools.loadXmlByKey(sDefault));
		System.out.println(lrTools.loadXmlByKey(lrTools.getDefaults()[1]));
		
	}

	@Test
	public void testLoadXmlByKey() {
		String sXML = lrTools.loadXmlByKey("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ");
		
		System.out.println(sXML);
		
		System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.SB.BCZZSXGMSB"));
		System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.SB.SBQKCXSS"));
		System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.ZS.CXNSRWQJQSFXX"));

	}

	@Test
	public void testPaserXmlWithParaStringStringArray() {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testPaserXmlWithParaStringString() {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetXMLNote() {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testLoadFile() {
		Assert.fail("Not yet implemented");
	}

}
