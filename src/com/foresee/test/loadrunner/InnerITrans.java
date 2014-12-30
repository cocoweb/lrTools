package com.foresee.test.loadrunner;

import lrapi.lr;


/**  
 * @author Administrator
 *
 */
public abstract class InnerITrans {
	
	public String TransName="";
	public boolean isOK = true;
	public String OutString = "";
	private long timer;

	public InnerITrans(String trans) {
		TransName = trans;
	}
	
	public void Before() {
    	timer = lr.start_timer();
    	lr.start_transaction(TransName);
		
	}
	
	public boolean RunTrans() throws Throwable{
		Before();
		isOK = doTrans();
		
		After();

		return isOK;
		
	}
	
	public void After() {
		LoadrunnerUtil.reportOut(isOK,  TransName, OutString,timer);
		
	}
	

	
	protected abstract boolean doTrans() throws Throwable;

}
