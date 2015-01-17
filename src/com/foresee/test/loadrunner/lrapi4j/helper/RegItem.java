package com.foresee.test.loadrunner.lrapi4j.helper;

import com.foresee.test.util.lang.StringUtil;


public class RegItem {
    public enum ItemType {SAVE,FIND};
    public String ParaName="";
    public String LeftStr="";
    public String RightStr="";
    public String Value ="";
    public ItemType type = ItemType.SAVE ;
    
    
    public String locateString(String sSrc){
        return StringUtil.locateString(sSrc,LeftStr,RightStr);
    }
    

}
