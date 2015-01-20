package com.foresee.test.loadrunner.lrapi4j.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.helper.ArgsSet.ArgItem;
import com.foresee.test.util.exfile.POIExcelUtil;
import com.foresee.test.util.io.FileUtil;

public class Arguments {
    static Arguments argument=null;

    static final String KEYVALUE = "KEYVALUE";
    static Map<String, ArgsSet> xlistmap = new HashMap<String, ArgsSet>();
    
    /**
     * 采用单例模式 
     * 
     * @return
     *  */
    public static Arguments getInstance() {
        if (null == argument) {
            synchronized (Arguments.class) {
                if (null == argument) {
                    argument = new Arguments();
                }
            }
        }
        return argument;
    }
 
    private Arguments() {
        // TODO Auto-generated constructor stub
    }
    
    public void Reset(){
        argument = null;
        xlistmap = new HashMap<String, ArgsSet>();
    }




    public static void loadExcelArgumentsByKey(String skey) {
        Map<String, String> localMap = FileDefinition.getParasMapByName(skey);
        try {
            List<ArrayList<String>> args = POIExcelUtil.loadExcelFile(
                    FileUtil.lookupFileInClasspath(localMap.get("filename")), localMap.get("sheet"));

            if (localMap.get("parameter").equals(KEYVALUE)) {
                for (ArrayList<String> array : args) { // 不保存到listmap,
                                                            // 直接存入eval_string
                    lr.save_string(array.get(1), array.get(0));
                }
            }else{
    
                xlistmap.put(skey, new ArgsSet(skey, FileDefinition.getParaByName(skey), args));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  Iterator<ArrayList<String>> getArgsIteratorByKey(String skey) {
        return xlistmap.get(skey).dataList.iterator();
    }

    public  ArrayList<ArgItem> getArgsNameByKey(String skey) {
        return xlistmap.get(skey).fieldList;
    }

    public  int getArgsNameIndex(String skey, String argName) {
        return getArgsNameByKey(skey).indexOf(argName);
    }


    public  Iterator<Object[]> getArgsIterator(String skey) {
        return  xlistmap.get(skey).getArgsIterator();
    }

 
}
