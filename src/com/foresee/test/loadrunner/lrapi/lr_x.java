package com.foresee.test.loadrunner.lrapi;

import com.foresee.test.util.ParamUtils;

/**
 * 实现包装LoadRunner的方法
 * 
 * @author Administrator
 *
 */

public class lr_x extends I_lr {
	public static String eval_string(String paramString) {
		return ParamUtils.replaceVariables(paramString, ParameterCache.getInstance().getMapCache());
	}

	public static int eval_int(String paramString) {
		return Integer.parseInt(ParamUtils.replaceVariables(paramString, ParameterCache.getInstance().getMapCache()));
	}

	public static int save_string(String sValue, String ParaName) {
		ParameterCache.getInstance().put(ParaName, sValue);
		return 0;
	}
	public static int save_int(int sValue, String ParaName) {
		ParameterCache.getInstance().put(ParaName,Integer.toString(sValue));
		return 0;
	}


	public static int start_transaction(String paramString) {
		// TODO 包装LoadRunner
		return lrapi.lr.start_transaction(paramString);
	}

	public static int end_transaction(String paramString, int paramInt) {
		// TODO 包装LoadRunner
		return lrapi.lr.end_transaction(paramString, paramInt);
	}

	public static int message(String paramString) {
		System.out.println(paramString);
		return 0;
	}

	public static int error_message(String paramString) {
		System.out.println("ERROR: " + paramString);
		return 0;
	}


}
