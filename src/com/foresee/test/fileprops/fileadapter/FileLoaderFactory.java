package com.foresee.test.fileprops.fileadapter;

public class FileLoaderFactory {
    public static BaseFileLoader getFileLoader(String keyName){
        if (keyName.indexOf("excel")>0){
            return new ExcelFile(keyName);
        }else if (keyName.indexOf("csvtext")>0){
            return new CSVTextFile(keyName);
        }else{
            return new XMLFile(keyName);
        }
        
    }

}
