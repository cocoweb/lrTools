package com.foresee.test.fileprops.fileadapter;

public class FileLoaderFactory {
    public static BaseFileLoader getFileLoader(String keyName) {
        try {
            if (keyName.indexOf(BaseFileLoader.FILETYPE_Excel) > 0) {
                return new ExcelFile(keyName);
            } else if (keyName.indexOf(BaseFileLoader.FILETYPE_CSVtext) > 0) {
                return new CSVTextFile(keyName);
            } else {
                return new XMLFile(keyName);
            }
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null;
    }

}
