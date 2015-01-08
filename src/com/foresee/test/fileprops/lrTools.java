package com.foresee.test.fileprops;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.foresee.test.util.StringUtil;
import com.foresee.test.util.cache.StringCacheManager;

//import javax.xml.parsers.*;
//import lrapi.lr;

public class lrTools {
	private static StringCacheManager xCache = StringCacheManager.getInstance();
	
 
	/**
	 * 从配置文件 file.Properties 中取出xml定义以及 参数定义
	 * 并转换为可参数化的xml
	 * 
	 * @param sKey  配置的xml标识
	 * @return  返回替换处理后的xml
	 */
	public static String loadXmlByKey(String sKey) {
		String sXml="";
		if( !xCache.containsKey(sKey)){
			synchronized(lrTools.class){
				if(!xCache.containsKey(sKey)){
					String sPara = XmlDefinition.getParaByName(sKey);
					sXml = loadFile(XmlDefinition.getFileByName(sKey));
				
					return xCache.putString(sKey, paserXmlWithPara(sXml, sPara));
				}
			}
		}//判断如果已经有数据了就不再读取文件
		return xCache.getString(sKey);
			
	}
	public static String getTranNameByKey(String sKey){
		return XmlDefinition.getValueByName(sKey);
	}
	
	
	public static String getDefault(){
		return XmlDefinition.getDefault()[0];
	}
	
	public static String[] getDefaults(){
		return XmlDefinition.getDefault();
	}

	
	/**
	 * 替换XML中匹配参数为  <aaa>{p_xxxxx}</aaa>
	 * @param xXml
	 * @param xParas  String[] 数组类型的参数名字表
	 * @return
	 */
	public static String paserXmlWithPara(String xXml, String[] xParas) {
		// 循环替换参数为 <aaa>{xxxxx}</aaa>
		for (String sKey : xParas) {
			String sTmp = "";
			//解析替换参数
			
			//判断参数是否使用<> 包围
			if(sKey.startsWith("<") && sKey.endsWith(">")){				
				//兼容XML节点中包含:等其他特别字符的情况
				sKey = StringUtil.trimEnd(StringUtil.trimStart(sKey, "<"), ">");
				
				//特殊字符使用 _ 替换
				sTmp = StringUtil.createXMLNote(sKey,
							"{p_" +
							sKey.replaceAll("\\p{Punct}","_") +
							"}");
				
			}else if(sKey.contains(":")){  
				String[] aKeyParas = sKey.split(":");    //0=key  1=value
				//如果包含:，代表要给出自定义的参数名
				sTmp = StringUtil.createXMLNote(aKeyParas[0],"{" + aKeyParas[1] + "}");
				sKey =aKeyParas[0];
			}else{
				//判断有没有给出默认参数值
				String sValue= getDefaultParaValueByKey(sKey);
						//aKeyParas.length >= 1 ?
						//getDefaultParaValueByKey(aKeyParas[0]):"";
				if(sValue.isEmpty()){
					//默认参数格式  {p_xxxx}
				    sTmp = StringUtil.createXMLNote(sKey,"{p_" + sKey + "}");					
				}else{
					//默认参数值  default.xxx = sValue
					sTmp = StringUtil.createXMLNote(sKey,sValue);
				}
			}
			
			String soldValue = StringUtil.createXMLNote(sKey,StringUtil.getXMLNote(xXml, sKey));

			xXml = xXml.replaceAll(soldValue, sTmp);
		}
		return xXml;

	}

	/**
	 * 替换XML中的节点为 <aaa>{p_xxxxx}</aaa>
	 * @param xXml
	 * @param xPara   ,逗号分隔的多个参数名字 字符串
	 * @return  返回替换完成的xml
	 */
	public static String paserXmlWithPara(String xXml, String xPara) {
		if(StringUtil.isNotEmpty(xPara)){
			return paserXmlWithPara(xXml, xPara.split(","));
			
		}else{
			return xXml;
		}
	}

	public static String getDefaultParaKey(int iNo){
		return XmlDefinition.getDefaultParaKey(iNo);
	}
	public static String getDefaultParaValue(int iNo){
		return XmlDefinition.getDefaultParaValue(iNo);
	}
	
	public static String getDefaultParaValueByKey(String skey){
		return XmlDefinition.getDefaultParaValueByKey(skey);
	}

	public static String loadFile(String sFileName) {
		//文件在Class path 的目录下 /
		InputStream inputStream = lrTools.class.getResourceAsStream(
				"/"	+ sFileName);
		if (inputStream == null) {
			
			System.out.println("未找到xml文件！"+"/"	+ sFileName);
		}

		String xml = "";
		try {
			System.out.println("==Load xml文件！"+"/"	+ sFileName);
			int length = inputStream.available();
			byte[] bytes = new byte[length];
			inputStream.read(bytes);
			xml = new String(bytes, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return xml;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sXML = lrTools.loadXmlByKey("SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ");
		
		//System.out.println(XmlDefinition.getValueByName("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ"));

		System.out.println(sXML);
		
		//System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.SB.BCZZSXGMSB"));
		//System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.SB.SBQKCXSS"));
		//System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.ZS.CXNSRWQJQSFXX"));
		
	}
}

