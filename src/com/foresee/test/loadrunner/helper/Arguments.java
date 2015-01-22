package com.foresee.test.loadrunner.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.foresee.test.fileprops.ArgsFile;
import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.loadrunner.lrapi4j.lr;

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
    
    public void load(String skey){
        
        ArgsFile argsfile= new ArgsFile(skey);
        
        if (FileDefinition.getParaByName(skey).equals(KEYVALUE)) {
            @SuppressWarnings("unchecked")
            List<ArrayList<String>> list = (List<ArrayList<String>>)(argsfile.loadFile());
            for (ArrayList<String> array : list) { // 不保存到listmap,
                                                    // 直接存入eval_string
                lr.save_string(array.get(1), array.get(0));
            }
        }else{
            ArgsSet argset = null;
            try {
                argset = ArgsSet.getInstance(argsfile);
                xlistmap.put(skey, argset);
            } catch (Exception e) {
    
                e.printStackTrace();
            }

        }

    }


    
    public ArgsSet getArgsSet(String skey){
        return xlistmap.get(skey);
    }
    public String getString(String skey){
        return xlistmap.get(skey).getFileString();
        
    }

//    public  Iterator<ArrayList<String>> getArgsIteratorByKey(String skey) {
//        return xlistmap.get(skey).dataList.iterator();
//    }
//
//    public  ArrayList<ArgItem> getArgsNameByKey(String skey) {
//        return xlistmap.get(skey).fieldList;
//    }

//    public  int getArgsNameIndex(String skey, String argName) {
//        return getArgsNameByKey(skey).indexOf(argName);
//    }


    public  Iterator<Object[]> getArgsIterator(String skey) {
        return  xlistmap.get(skey).getArgsIterator();
    }

 
}
