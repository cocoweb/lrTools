package com.foresee.test.fileprops.fileadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.util.io.FileUtil;

public abstract class BaseFileLoader {
    public String keyName = "";
    public String filePath="";
    public Map<String, String> localMap = null;
    
    public  BaseFileLoader(String keyName) {
        super();
        this.keyName = keyName;
        this.localMap = FileDefinition.getParasMapByName(keyName);
        this.filePath = FileUtil.lookupFileInClasspath(localMap.get("filename")).getAbsolutePath();
    }

    public boolean isExcel() {
        return keyName.indexOf("excel")>0;
    }
    
    public boolean isCSVText() {
        return keyName.indexOf("csvtext")>0;
    }

    public abstract List<ArrayList<String>> loadFile();
    public abstract String loadFile2String();
}
