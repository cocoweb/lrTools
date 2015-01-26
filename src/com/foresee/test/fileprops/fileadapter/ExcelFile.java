package com.foresee.test.fileprops.fileadapter;

import java.util.ArrayList;
import java.util.List;

import com.foresee.test.util.exfile.POIExcelUtil;

public class ExcelFile extends BaseFileLoader {

    public ExcelFile(String keyName) {
        super(keyName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<ArrayList<String>> loadFile() {
        try {
            return POIExcelUtil.loadExcelFile(
                    this.filePath
                    , localMap.get("sheet"));
            
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
