package com.foresee.test.loadrunner;

import lrapi.lr;


/**  
 * @author Administrator
 *
 */
public abstract class InnerITrans extends LrActionClass {
	
	public String TransName="";
	public boolean isOK = true;
	public String OutString = "";

	public InnerITrans(String trans) {
		TransName = trans;
	}
	
	public boolean RunTrans() throws Throwable{
		
    	long timer = lr.start_timer();
    	//String transName=xDo.TransName;
    	//String retXML=xDo.OutString;
    	lr.start_transaction(TransName);
		isOK = doTrans();
		
	   	reportOut(isOK,  TransName, OutString,timer);

		return isOK;
		
	}

	
	public abstract boolean doTrans() throws Throwable;

}
