package com.foresee.test.util.exfile;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 扩展Properties
 * 允许读取形如以下的分段Properties文件
 * 
#####指定当前的 报文列表      #指定参数的默认值
default=SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ,SWZJ.HXZG.SB.BCZZSXGMSB
default.skssqq=2014-11-01
default.skssqz=2014-11-30
default.sbrq=2015-01-10


#####以下定义XML文件名字以及参数列表

SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ=增值税小规模申报事前监控及获取期初数据
SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ.filename=SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ-增值税小规模申报事前监控及获取期初数据-request.xml
SWZJ.HXZG.SB.ZZSXGMSBSQJKJHQQCSJ.parameter=tran_seq,djxh,skssqq,skssqz
 * @author Administrator
 *
 */
public class ExtProperties extends Properties {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 452787411189291036L;
	
	private static Map<String, Map<String,String>> sectionMap 
				= new ConcurrentHashMap<String, Map<String,String>>();

	public ExtProperties() {
		// TODO Auto-generated constructor stub
	}

	public ExtProperties(Properties defaults) {
		super(defaults);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		return super.getProperty(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getProperty(String key, String defaultValue) {
		// TODO Auto-generated method stub
		return super.getProperty(key, defaultValue);
	}

	/* (non-Javadoc)
	 * @see java.util.Properties#propertyNames()
	 */
	@Override
	public Enumeration<?> propertyNames() {
		// TODO Auto-generated method stub
		return super.propertyNames();
	}
	
	private Map<String, String> loadSection(String sectionName) {
		//检查是否已经解析过sectionName的值集
		if(sectionMap.containsKey(sectionName)){
			//如果已经解析过，直接返回sectionMap中的值集
			return sectionMap.get(sectionName);
		}
		
		Map<String, String> xMap = new ConcurrentHashMap<String, String>();
		
		for(Object xkey:this.keySet()){ //寻找某行包含 sectionName
			
			if(xkey.toString().indexOf(sectionName+".")==0){
				xMap.put(xkey.toString().substring((sectionName+".").length()),
						this.getProperty(xkey.toString()));
			} else if(xkey.toString().indexOf(sectionName )==0){
				//暂时不填入section默认值 .
				//xMap.put(".",	this.getProperty(xkey.toString()));
				
			} 
		}
		
		try {
			if(xMap.size()>=1){
				sectionMap.put(sectionName, xMap);
				return xMap;
				
			}else{
				throw new Exception("没有对应的section="+sectionName);
				
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * 返回如下的 default段
	 * #####指定当前的 报文列表      #指定参数的默认值
		default=SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ,SWZJ.HXZG.SB.BCZZSXGMSB <br/>
		default.skssqq=2014-11-01 <br/>
		default.skssqz=2014-11-30 <br/>
		default.sbrq=2015-01-10 <br/>
	 *
	 * @return  
	 * {skssqq=2014-11-01, <br/>
	 * sbrq=2015-01-10, <br/>
	 * skssqz=2014-11-30} <br/>
	 * 
	 * .=SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ,SWZJ.HXZG.SB.BCZZSXGMSB, 
	 */
	public Map<String, String> getSectionItems(String sectionName){
		return loadSection(sectionName);

	}
	
	/**
	 * 检查是否包含某个sectionName
	 * @param sectionName
	 * @return
	 */
	public boolean containSection(String sectionName){
		return loadSection(sectionName)!=null;
	}
	
	/**
	 * 返回某个section下的，键值
	 * 
	 * @param sectionName
	 * @param sKey
	 * @return
	 */
	public String getSectionItem(String sectionName,String sKey){
		return  getSectionItems(sectionName).get(sKey) ;
	}
	
	/**
	 * 返回某个Section的默认值
	 * 
	 * @param sectionName
	 * @return
	 */
	public String getSectionDefault(String sectionName){
		return  getProperty(sectionName) ;
	}

}
