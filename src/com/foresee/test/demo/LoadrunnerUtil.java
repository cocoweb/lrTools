package com.foresee.test.demo;
//package com.foresee.test.loadrunner;

import com.foresee.test.loadrunner.lrapi.lr;
import com.foresee.test.loadrunner.lrapi.web;

public class LoadrunnerUtil {
	/**
	 * �������
	 * 
	 * @param bSuccess   �ɹ���־
	 * @param transName   ������
	 * @param retXML      ���ص��ַ���
	 * @param beginTimer       ԭ������ʼʱ��
	 */
	public static void reportOut(boolean bSuccess, String transName, String retXML, long beginTimer) {
	    long ltime =System.currentTimeMillis() - beginTimer;
	    String stime = ltime<10000 ? Long.toString(ltime):"==too long=="+Long.toString(ltime)+" ";
	    if(bSuccess){
			lr.message(stime+" "+lr.eval_string(transName+" �ɹ�!:����:")+ retXML  );
			lr.end_transaction(transName, lr.AUTO);
	    }else{
			lr.error_message(stime+" "+lr.eval_string(transName+" ʧ��:����:["+retXML+"]"));
			lr.end_transaction(transName, lr.FAIL);
	
	    }
	}

}
