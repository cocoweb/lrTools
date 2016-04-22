package com.foresee.test.fileprops.fileadapter;

import java.util.ArrayList;
import java.util.List;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.util.exfile.POIExcelUtil;

public class ExcelFile extends BaseFileLoader {

    public ExcelFile(String keyName) throws Exception {
        super(keyName);
       
    }

    @Override
    public List<ArrayList<String>> loadFile() {
        try {
            System.out.println("==Load Excel文件！"+ filePath);
            return POIExcelUtil.loadExcelFile( filePath, localMap.get(FileDefinition.FILEField_Sheet));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String loadFile2String() {
        // TODO Auto-generated method stub
        return null;
    }

}
