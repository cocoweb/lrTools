package com.foresee.test.loadrunner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;

import lrapi.lr;

/**
 * Loadrunner Action 继承父类
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

    public LrActionClass() {
        super();
    }

	/**
	 * 执行一个匿名定义的transaction
	 * @param xDo  InnerITrans的实例对象,匿名方式实现为主
	 * @return  返回事务是否成功
	 */
	protected boolean runTransaction(InnerITrans xDo) throws Throwable {
		    	//before transaction code
		    	
		    	//run transaction
		    	boolean ret = xDo.RunTrans();
		    	
				//after transaction code
	
			    
			    return ret;
		    }
    /**
     * 调用模板,直接Copy,然后填空
     * @throws Throwable
     */
    public  void run_Temp() throws Throwable{
		(new InnerITrans(""){
			@Override
			public boolean doTrans() throws Throwable {
	     
		        
		        
		        return true;
		    }}).RunTrans();
	}

}