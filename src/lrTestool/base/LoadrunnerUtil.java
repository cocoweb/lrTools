package lrTestool.base;

import lrapi.lr;

public class LoadrunnerUtil {
	/**
	 * 输出报告
	 * 
	 * @param bSuccess   成功标志
	 * @param transName   事务名
	 * @param retXML      返回的字符串
	 * @param beginTimer       原来的起始时间
	 */
	public static void reportOut(boolean bSuccess, String transName, String retXML, long beginTimer) {
	    long ltime =System.currentTimeMillis() - beginTimer;
	    String stime = ltime<10000 ? Long.toString(ltime):"==too long=="+Long.toString(ltime)+" ";
	    if(bSuccess){
			lr.message(stime+" "+lr.eval_string(transName+" 成功!:NSRSBH:[{para_nsrsbh}]:返回:")+ retXML  );
			lr.end_transaction(transName, lr.AUTO);
	    }else{
			lr.error_message(stime+" "+lr.eval_string(transName+" 失败:NSRSBH:[{para_nsrsbh}]:返回:["+retXML+"]"));
			lr.end_transaction(transName, lr.FAIL);
	
	    }
	}

}
