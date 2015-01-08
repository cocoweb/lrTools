package com.foresee.test.loadrunner.lrapi;

public abstract class I_lr {

	public static final int PASS = 0;
	public static final int FAIL = 1;
	public static final int AUTO = 2;
	public static final int STOP = 3;

	public static String eval_string(String paramString) {
		return lrapi.lr.eval_string(paramString);
	
	}

	public static int eval_int(String paramString) {
		// TODO 包装LoadRunner
		return lrapi.lr.eval_int(paramString);
	}

	public static int save_string(String sValue, String ParaName) {
		// TODO 包装LoadRunner
		return lrapi.lr.save_string(sValue, ParaName);
	}
	public static int save_int(int sValue, String ParaName) {
		return lrapi.lr.save_int(sValue, ParaName);
	}

	public static long start_timer() {
		return System.currentTimeMillis();
	}

	public static long end_timer(long startTimeMilis) {
		return System.currentTimeMillis() - startTimeMilis;
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
		// TODO 包装LoadRunner
		return lrapi.lr.message(paramString);
	}

	public static int error_message(String paramString) {
		return lrapi.lr.error_message(paramString);
	}

	public static void think_time(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public I_lr() {
		super();
	}

}