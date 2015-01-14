package com.foresee.test.loadrunner.lrapi4j;

import com.foresee.test.loadrunner.lrapi.web;

public final class lrFactory {
	
	public static Class  getLRClass(){
		return lr.class;
	}
	
	public static Class  getWEBClass(){
		return web.class;
	}

}
