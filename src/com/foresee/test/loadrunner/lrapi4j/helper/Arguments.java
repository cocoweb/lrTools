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

    static final String KEYVALUE = "KEYVALUE";
    static Map<String, ArgsSet> xlistmap = new HashMap<String, ArgsSet>();

    public static void loadKEYVALUE() throws Exception {
        Map<String, String> localMap = FileDefinition.getParasMapByName("keyvalue.excel");
        // FileUtil.lookupFileInClasspath(localMap.get("filename")).getAbsolutePath()

        if (localMap.get("parameter").equals(KEYVALUE)) {

            List<ArrayList<String>> keyvalues = POIExcelUtil.loadExcelFile(
                    FileUtil.lookupFileInClasspath(localMap.get("filename")), localMap.get("sheet"));

            for (ArrayList<String> array : keyvalues) { // 不保存到listmap,
                                                        // 直接存入eval_string
                lr.save_string(array.get(1), array.get(0));
            }
        }
    }

    public static void loadExcelArgumentsByKey(String skey) {
        Map<String, String> localMap = FileDefinition.getParasMapByName(skey);

        try {
            List<ArrayList<String>> args = POIExcelUtil.loadExcelFile(
                    FileUtil.lookupFileInClasspath(localMap.get("filename")), localMap.get("sheet"));

            xlistmap.put(skey, new ArgsSet(skey, FileDefinition.getParaByName(skey), args));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Iterator<ArrayList<String>> getArgsIteratorByKey(String skey) {
        return xlistmap.get(skey).dataList.iterator();
    }

    public static ArrayList<ArgItem> getArgsNameByKey(String skey) {
        return xlistmap.get(skey).fieldList;
    }

    public static int getArgsNameIndex(String skey, String argName) {
        return getArgsNameByKey(skey).indexOf(argName);
    }


    public static Iterator<Object[]> getArgsIterator(String skey) {
        return  xlistmap.get(skey).getArgsIterator();
    }

 
}
