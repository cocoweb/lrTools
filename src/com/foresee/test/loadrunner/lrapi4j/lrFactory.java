package com.foresee.test.loadrunner.lrapi4j;

import com.foresee.test.loadrunner.base.I_lr;
import com.foresee.test.loadrunner.base.I_web;

import lrTestool.lrapi.web;

public final class lrFactory {
    
    public static I_lr getLRObj(String type){
        return new lrTestool.lrapi.lr();
    }
    
    public static I_web getWEBObj(){
        return new lrTestool.lrapi.web();
    }
	

}
