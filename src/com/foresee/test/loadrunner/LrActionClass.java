package com.foresee.test.loadrunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loadrunner Action 继承父类
 * 
 * init() 需要override，用来进行初始化
 * 
 *
 * 
 * @author Administrator
 *  
 */

public abstract class LrActionClass {
 
	static final String ENDFORM = "ENDFORM";
	static final String LAST = "LAST";
	static final String ENDITEM = "ENDITEM";
	static final String ITEMDATA = "ITEMDATA";
	static final String STARTHIDDENS = "STARTHIDDENS";
	static final String ENDHIDDENS = "ENDHIDDENS";
	static final String CONNECT = "CONNECT";
	static final String RECEIVE = "RECEIVE";
	static final String RESOLVE = "RESOLVE";
	static final String REQUEST = "REQUEST";
	static final String RESPONSE = "RESPONSE";
	static final String EXTRARES = "EXTRARES";
	static int _webresult;
	
	static Map<String, InnerITrans> cacheMap = new ConcurrentHashMap<String, InnerITrans>();

    /**
     * 添加事务对象，
     * 可以使用InnerITrans 的匿名继承对象
     * @param xITrans
     * @return
     */
    public InnerITrans addTrans(InnerITrans xITrans){
    	if (!cacheMap.containsKey(xITrans.TransName)){  //避免并发时，保存多个
    	    return cacheMap.put(xITrans.TransName, xITrans);
    	}
    	return cacheMap.get(xITrans.TransName);
    	
    }
    
    /**
     * 使用事务的名字查找、并执行
     * 避免每次执行的时候创建匿名对象
     * @param transName
     * @return
     * @throws Throwable
     */
    public boolean runTrans(String transName) throws Throwable{
    	if(cacheMap.containsKey(transName)){
	    	return cacheMap.get(transName).RunTrans();
    	}else{
    		throw new Exception("没有找到这个Transaction："+transName);
    	}
    	//return false;
    }
    public boolean runTrans(String transName,String more) throws Throwable{
    	return cacheMap.get(transName).RunTrans();
    }

    public LrActionClass() {
        super();
        init();
        
    }
   
    /**
     * 初始化，并且加入LoadRunner的脚本类
     */
    public abstract void init();

	/**
	 * 执行一个匿名定义的transaction
	 * @param xDo  InnerITrans的实例对象,匿名方式实现为主
	 * @return  返回事务是否成功
	protected boolean runTransaction(InnerITrans xDo) throws Throwable {
		    	//before transaction code
		    	
		    	//run transaction
		    	boolean ret = xDo.RunTrans();
		    	
				//after transaction code
	
			    
			    return ret;
		    }	 */

	/**
     * 调用模板,直接Copy,然后填空
     * 此方法可能在多次循环时，创建多个匿名对象
     * @throws Throwable
     */
    public  void run_Temp() throws Throwable{
		new InnerITrans(""){
			@Override
			public boolean doTrans() throws Throwable {
	     
		       //here you can do something 
		        
		        return true;
		}}.RunTrans();
		//这里必须调用RunTrans()，因为封装了一些三明治的代码
	}
    
    public void run_Demo() throws Throwable{
    	//init 中添加匿名类
    	addTrans(new InnerITrans("aaa"){
			@Override
			public boolean doTrans() throws Throwable {
	     
		       //here you can do something 
		        
		        return true;
		}});
    	
    	//然后执行
    	runTrans("aaa");
    	
    }

}