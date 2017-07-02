package lrTestool.base;

import lrapi.lr;



/**
 * loadrunner 内部匿名类的父类
 * @author allan.xie
 *
 */
public abstract class InnerITrans extends com.foresee.test.loadrunner.base.InnerITrans{

    public InnerITrans(String trans) {
        super(trans);
        
    }
    @Override
    public void Before() {
      timer = System.currentTimeMillis();
      lr.start_transaction(TransName);

  }
    @Override
    public void After() {
        LoadrunnerUtil.reportOut(isOK, TransName, OutString, timer);

    }

    
}
//public abstract class InnerITrans {
//
//	public String TransName = "";
//	public boolean isOK = true;
//	public String OutString = "";
//	private long timer;
//	
//	public int _webresult;
//
//	public InnerITrans(String trans) {
//		TransName = trans;
//	}
//
//	public void Before() {
//		timer = System.currentTimeMillis();
//		lr.start_transaction(TransName);
//
//	}
//
//	public boolean RunTrans() throws Throwable {
//		Before();
//		isOK = onTrans();
//
//		After();
//
//		return isOK;
//
//	}
//

//
//	/**
//	 * 回调方法
//	 * @return
//	 * @throws Throwable
//	 */
//	public abstract boolean onTrans() throws Throwable;
//
//}
