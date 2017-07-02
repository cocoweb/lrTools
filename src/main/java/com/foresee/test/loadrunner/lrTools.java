package com.foresee.test.loadrunner;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.loadrunner.helper.Arguments;
import com.foresee.test.util.cache.StringCacheMgr;


public class lrTools {
	private static StringCacheMgr xCache = StringCacheMgr.getInstance();

	/**
	 * 从配置文件 file.Properties 中取出xml定义以及 参数定义
	 * 并转换为可参数化的xml
	 * 
	 * @param sKey  配置的xml标识
	 * @return  返回替换处理后的xml
	 */
    public static String loadXmlByKey(String sKey) {
        if( !xCache.containsKey(sKey)){
           synchronized(lrTools.class){
               if(!xCache.containsKey(sKey)){
                   Arguments xargs=Arguments.getInstance();
                   try {
                    xargs.load(sKey);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                   return "<!--loadxml:"+sKey+"-->"+xCache.putString(sKey,xargs.getString(sKey) );

               }
           }
       }//判断如果已经有数据了就不再读取文件
       return xCache.getString(sKey);
   }

	
	public static String getTranNameByKey(String sKey){
		return FileDefinition.getValueByName(sKey);
	}
	
	
	public static String getDefault(){
		return FileDefinition.getDefault()[0];
	}
	
	public static String[] getDefaults(){
		return FileDefinition.getDefault();
	}


	public static String getDefaultParaKey(int iNo){
		return FileDefinition.getDefaultParaKey(iNo);
	}
	public static String getDefaultParaValue(int iNo){
		return FileDefinition.getDefaultParaValue(iNo);
	}
	
	public static String getDefaultParaValueByKey(String skey){
		return FileDefinition.getDefaultParaValueByKey(skey);
	}


	public static void main(String[] args) {
	    lrTools lr = new lrTools();
	    
		String sXML = lr.loadXmlByKey("SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ");
		
		//System.out.println(XmlDefinition.getValueByName("SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ"));

		System.out.println(sXML);
		
		//System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.SB.BCZZSXGMSB"));
		//System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.SB.SBQKCXSS"));
		//System.out.println(lrTools.loadXmlByKey("SWZJ.HXZG.ZS.CXNSRWQJQSFXX"));
		
	}
}

