package com.foresee.test.fileprops.fileadapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.foresee.test.util.io.FileUtil;

public class XMLFile extends BaseFileLoader {

    public XMLFile(String keyName) {
        super(keyName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<ArrayList<String>> loadFile() {
        return null;
    }

    @Override
    public String loadFile2String() {
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
