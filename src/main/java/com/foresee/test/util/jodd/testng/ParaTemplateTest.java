package com.foresee.test.util.jodd.testng;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.foresee.test.util.NgAssert;
import com.foresee.test.util.jodd.ParaTemplate;

import jodd.util.StringTemplateParser;
import jodd.util.StringTemplateParser.MacroResolver;

public class ParaTemplateTest {
	StringTemplateParser stp;
	Map xmap ;
	MacroResolver mr;
  @BeforeClass
  public void beforeClass() {
	  stp=StringTemplateParser.create();
	  xmap = new ConcurrentHashMap();
	  
//	  xmap.put("aaa", "AAA");
      xmap.put("bbb", "BBBB");
//	  xmap.put("ccc", "a");
	  xmap.put("ddd", "cc");
	  
	  mr = StringTemplateParser.createMapMacroResolver(xmap);
	  
  }
  @Test
  public void f() {
	  
//	  new NgAssert().assertEquals(stp.setMacroStart("{").setStrictFormat().setParseValues(false)
//			  .parse("afdadf{a{c{ddd}}a}ffff{bbb}dfdsfdsffbbb", mr)
//			  , "afdadfAAAffffBBBBdfdsfdsffbbb");
	  new NgAssert().assertEquals(stp.setMacroStart("{").setStrictFormat().setParseValues(false)
			  .parse("afdadf{a{c{ddd}}a}ffff{bbb}dfdsfdsffbbb", new MacroResolver(){

				@Override
				public String resolve(String macroName) {
					Object value = xmap.get(macroName);

					if (value == null) {
						return "<"+macroName+">";
					}

					return value.toString();
				}
				  
			  })
			  , "afdadfAAAffffBBBBdfdsfdsffbbb");

//	  System.out.println(stp.setMacroStart("{").setStrictFormat()
//			  .parse("afdadf{a{c{ddd}}a}ffff{bbb}dfdsfdsffbbb", mr));
	  
  }
  
  @Test
  public void eval_string(){
	  ParaTemplate.save_string("AAAAAAAAAAA", "nsr");
	  ParaTemplate.save_string("AAid", "id");
	  ParaTemplate.save_string("AAuser", "user");
	  ParaTemplate.save_string("AAbbbbbbb", "nsr");
	  
	  new NgAssert().assertEquals(ParaTemplate.eval_string("adf {nsr} asdf{djxh}ad"),"adf AAAAAAAAAAA asdfad");
	  
  }

}
