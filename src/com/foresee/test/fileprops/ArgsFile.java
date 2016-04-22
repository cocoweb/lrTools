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
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ArgsFile [keyName=" + keyName + ", getFileName()=" + getFileName() + ", getSheetName()=" + getSheetName()
                + ", getParameter()=" + getParameter() + "]";
    }


    public String getFileName() {
        return xfile.localMap.get(FileDefinition.FILEField_FileName);
    }
    public String getSheetName() {
        return xfile.localMap.get(FileDefinition.FILEField_Sheet);
    }
    
    public String getParameter(){
        return xfile.localMap.get(FileDefinition.FILEField_Parameter);
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
