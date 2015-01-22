package com.foresee.test.fileprops;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.foresee.test.util.exfile.CSVAnalysis;
import com.foresee.test.util.exfile.POIExcelUtil;
import com.foresee.test.util.io.FileUtil;

/**
 * @author Administrator
 * 不同的参数文件类型，包括excel、txt、xml...
 */
public class ArgsFile {
    public String keyName = "";
    
    public Map<String, String> localMap = null;
            
    public  ArgsFile(String keyName) {
        super();
        this.keyName = keyName;
        this.localMap = FileDefinition.getParasMapByName(keyName);
    }
    

    public String getFileName() {
        return localMap.get("filename");
    }
    public String getSheetName() {
        return localMap.get("sheet");
    }
    
    public boolean isExcel() {
        return keyName.indexOf("excel")>0;
    }
    
    public boolean isCSVText() {
        return keyName.indexOf("csvtext")>0;
    }
    public String getParameter(){
        return localMap.get("parameter");
    }

    public Object loadFile(){
        if (keyName.indexOf("excel")>0){
            return loadExcelFile();
        }else if (keyName.indexOf("csvtext")>0){
            return loadCSVFile();
        }else{
            return loadXMLFile();
        }
        //return null;

    }
    
    public List<ArrayList<String>> loadExcelFile(){
        try {
            return POIExcelUtil.loadExcelFile(
                    FileUtil.lookupFileInClasspath(localMap.get("filename"))
                    , localMap.get("sheet"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<ArrayList<String>> loadCSVFile(){
        try {
            CSVAnalysis csvfile = new CSVAnalysis(FileUtil.lookupFileInClasspath(localMap.get("filename")));
            return csvfile.readCSVFile();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return null;
    }
    
    public String loadXMLFile(){
        String xml = "";
        File xfile = FileUtil.lookupFileInClasspath(localMap.get("filename"));
        //String sFileName =xfile.getAbsolutePath();
        InputStream inputStream=null ;
        try {
            inputStream = new FileInputStream(xfile);
            System.out.println("==Load xml文件！"+ xfile.getAbsolutePath());
            int length = inputStream.available();
            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            xml = new String(bytes, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }
        return xml;
    }
    
    

}
