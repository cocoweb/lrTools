package com.foresee.test.loadrunner.lrapi4j;

import com.foresee.test.util.lang.StringUtil;


public class RegItem {
    public enum ItemType {SAVE,FIND};
    public String ParaName="";
    public String LeftStr="";
    public String RightStr="";
    public String Value ="";
    public ItemType type = ItemType.SAVE ;
    
    
    public String locateString(String sSrc){
        int fromIndex = 0;
        int indexLeft=sSrc.indexOf(LeftStr);
        int indexRight = sSrc.indexOf(RightStr);
        
        
        if(StringUtil.isNotEmpty(LeftStr)&&sSrc.indexOf(LeftStr)>=0){
            if(indexRight == -1){  //无右边界
                return sSrc.substring(indexLeft + LeftStr.length());
            }else{
                
                while(indexRight < indexLeft && indexRight != -1){  
                    fromIndex++;
                    indexRight = sSrc.indexOf(RightStr, fromIndex);
                }
                if (indexRight!=-1 && indexRight > indexLeft){ //找到右边界
                    return sSrc.substring(indexLeft + LeftStr.length(), indexRight);
                }else{
                    //return "";
                }
            }
            
        }else{  //无左边界
            if(StringUtil.isEmpty(RightStr)){  //无右边界
                return sSrc;
            }else if(indexRight == -1){ //有右边界
                
            }else{ 
                return sSrc.substring(0, indexRight);
                
            }
           
        }
        
        return "";
        
    }
    

}
