package com.foresee.test.demo;
//package com.foresee.test.loadrunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loadrunner Action �̳и���
 * 
 * init() ��Ҫoverride���������г�ʼ��
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
     * ����������
     * ����ʹ��InnerITrans �������̳ж���
     * @param xITrans
     * @return
     */
    public InnerITrans addTrans(InnerITrans xITrans){
    	if (!cacheMap.containsKey(xITrans.TransName)){  //���Ⲣ��ʱ��������
    	    return cacheMap.put(xITrans.TransName, xITrans);
    	}
    	return cacheMap.get(xITrans.TransName);
    	
    }
    
    /**
     * ʹ����������ֲ��ҡ���ִ��
     * ����ÿ��ִ�е�ʱ�򴴽���������
     * @param transName
     * @return
     * @throws Throwable
     */
    public boolean runTrans(String transName) throws Throwable{
    	if(cacheMap.containsKey(transName)){
	    	return cacheMap.get(transName).RunTrans();
    	}else{
    		throw new Exception("û���ҵ����Transaction��"+transName);
    	}
    	//return false;
    }
    public boolean runTrans(String transName,String more) throws Throwable{
    	return cacheMap.get(transName).RunTrans();
    }

    public LrActionClass() {
        super();
        //Init();
        
    }
   
    /**
     * ��ʼ�������Ҽ���LoadRunner�Ľű���
     */
    public abstract void Init();

	/**
	 * ִ��һ�����������transaction
	 * @param xDo  InnerITrans��ʵ������,������ʽʵ��Ϊ��
	 * @return  ���������Ƿ�ɹ�
	protected boolean runTransaction(InnerITrans xDo) throws Throwable {
		    	//before transaction code
		    	
		    	//run transaction
		    	boolean ret = xDo.RunTrans();
		    	
				//after transaction code
	
			    
			    return ret;
		    }	 */

	/**
     * ����ģ��,ֱ��Copy,Ȼ�����
     * �˷��������ڶ��ѭ��ʱ�����������������
     * @throws Throwable
     */
    public  void run_Temp() throws Throwable{
		new InnerITrans(""){
			@Override
			public boolean onTrans() throws Throwable {
	     
		       //here you can do something 
		        
		        return true;
		}}.RunTrans();
		//����������RunTrans()����Ϊ��װ��һЩ�����εĴ���
	}
    
    public void run_Demo() throws Throwable{
    	//init �����������
    	addTrans(new InnerITrans("aaa"){
			@Override
			public boolean onTrans() throws Throwable {
	     
		       //here you can do something 
		        
		        return true;
		}});
    	
    	//Ȼ��ִ��
    	runTrans("aaa");
    	
    }

}