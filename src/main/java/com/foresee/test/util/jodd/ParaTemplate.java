package com.foresee.test.util.jodd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jodd.util.StringTemplateParser;
import jodd.util.StringTemplateParser.MacroResolver;

/**
 * 使用Jodd库，实现模版参数字符串
 * 如："adf {nsr} asdfad" = "adf AAAAAAAAAAA asdfad"
 * @author allan
 *
 */
public class ParaTemplate {
	
	protected StringTemplateParser stp;
	protected Map<String, String> xmap ;
	protected MacroResolver mr;

	private static ParaTemplate pt=null;

	private ParaTemplate() {
		  stp=StringTemplateParser.create().setMacroStart("{").setStrictFormat();
		  xmap = new ConcurrentHashMap<String, String>();
		  mr=StringTemplateParser.createMapMacroResolver(xmap);

	}
	
	public static ParaTemplate getInstance(){
		if (null == pt) {
			synchronized (ParaTemplate.class) {
				if (null == pt) {
					pt = new ParaTemplate();
				}
			}
		}
		return  pt;
		
	}
	
	public String parse(String templateStr) {
		//return stp.parse(templateStr,mr);
		return stp.parse(templateStr,new MacroResolver(){

			@Override
			public String resolve(String macroName) {
				Object value = xmap.get(macroName);

				if (value == null) {
					return "<"+macroName+">";
				}

				return value.toString();
			}
			  
		  });
	}
	
	public String putString(String skey, String svalue) {
		if( xmap.containsKey(skey)){
			return ((ConcurrentHashMap<String, String>) xmap).replace(skey, svalue).toString();
		}
		return xmap.put(skey, svalue);
	}
	

	public static String eval_string(String paramString) {
		return getInstance().parse(paramString);
		
	}
	
	public static int eval_int(String paramString) {
		return Integer.parseInt(eval_string(paramString));
		
	}

	public static int save_string(String sValue, String ParaName) {
		getInstance().putString(ParaName, sValue);
		
		return 0;
		
	}
	
    public static int save_int(int sValue, String ParaName) {
    	getInstance().putString(ParaName, Integer.toString(sValue));
		return 0;
    	
    }



}
