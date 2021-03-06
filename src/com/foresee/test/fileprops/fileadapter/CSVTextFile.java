package com.foresee.test.fileprops.fileadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.foresee.test.util.exfile.CSVAnalysis;

public class CSVTextFile extends BaseFileLoader {

    public CSVTextFile(String keyName) throws Exception {
        super(keyName);
    }

    @Override
    public List<ArrayList<String>> loadFile() {
        try {
            CSVAnalysis csvfile = new CSVAnalysis(filePath);
            System.out.println("==Load CSVText文件！"+ filePath);
            return csvfile.readCSVFile();
        } catch (IOException e) {
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
