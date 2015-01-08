package com.foresee.test.demo;
//package com.foresee.test.loadrunner;

import com.foresee.test.loadrunner.lrapi.lr;
import com.foresee.test.loadrunner.lrapi.web;

/**
 * loadrunner �ڲ�������ĸ���
 * @author allan.xie
 *
 */
public abstract class InnerITrans {

	public String TransName = "";
	public boolean isOK = true;
	public String OutString = "";
	private long timer;

	public InnerITrans(String trans) {
		TransName = trans;
	}

	public void Before() {
		timer = System.currentTimeMillis();
		lr.start_transaction(TransName);

	}

	public boolean RunTrans() throws Throwable {
		Before();
		isOK = onTrans();

		After();

		return isOK;

	}

	public void After() {
		LoadrunnerUtil.reportOut(isOK, TransName, OutString, timer);

	}

	protected abstract boolean onTrans() throws Throwable;

}
