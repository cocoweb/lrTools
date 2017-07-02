package com.foresee.test.loadrunner.helper;

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


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RegItem [ParaName=" + ParaName + ", LeftStr=" + LeftStr + ", RightStr=" + RightStr + ", type=" + type
                + "]";
    }
    

}
