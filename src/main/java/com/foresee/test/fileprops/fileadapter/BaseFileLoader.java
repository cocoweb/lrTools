package com.foresee.test.fileprops.fileadapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.util.io.FileUtil;
import com.foresee.test.util.lang.StringUtil;
import com.gargoylesoftware.htmlunit.util.StringUtils;

public abstract class BaseFileLoader {
    public final static String FILETYPE_Excel="excel";
    public final static String FILETYPE_CSVtext="csvtext";
    public final static String FILETYPE_XML="";
    
    public String keyName = "";
    public String filePath="";
    public Map<String, String> localMap = null;
    
    public  BaseFileLoader(String keyName) throws Exception {
        super();
        this.keyName = keyName;
        this.localMap = FileDefinition.getParasMapByName(keyName);
        String file = localMap.get(FileDefinition.FILEField_FileName);
        if (StringUtil.isBlank(file)){
            throw new Exception("<<<参数文件名filename为空>>>"+keyName+localMap);
        }else{
            File xfile = FileUtil.lookupFileInClasspath(localMap.get(FileDefinition.FILEField_FileName));
            if (xfile==null){
                throw new Exception("<<<文件不存在>>>"+keyName+localMap);
            }else
                filePath = xfile.getAbsolutePath();
        }
          
    }

    public boolean isExcel() {
        return keyName.indexOf(FILETYPE_Excel)>0;
    }
    
    public boolean isCSVText() {
        return keyName.indexOf(FILETYPE_CSVtext)>0;
    }

    public abstract List<ArrayList<String>> loadFile();
    public abstract String loadFile2String();
}
