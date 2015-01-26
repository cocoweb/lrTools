package com.foresee.test.fileprops;

import com.foresee.test.fileprops.fileadapter.BaseFileLoader;
import com.foresee.test.fileprops.fileadapter.FileLoaderFactory;

/**
 * @author Administrator
 * 不同的参数文件类型，包括excel、txt、xml...
 */
public class ArgsFile {
    public String keyName = "";
    
    //public Map<String, String> localMap = null;
    
    public BaseFileLoader xfile = null;
            
    public  ArgsFile(String keyName) {
        super();
        this.keyName = keyName;
        this.xfile = FileLoaderFactory.getFileLoader(keyName);
;
    }
    

    public String getFileName() {
        return xfile.localMap.get("filename");
    }
    public String getSheetName() {
        return xfile.localMap.get("sheet");
    }
    
    public String getParameter(){
        return xfile.localMap.get("parameter");
    }

    public Object loadFile(){
        if (xfile.isExcel()||xfile.isCSVText()){
            return xfile.loadFile();//loadExcelFile();
        }else{
            return xfile.loadFile2String();//loadXMLFile();
        }
        //return null;

    }
    

}
