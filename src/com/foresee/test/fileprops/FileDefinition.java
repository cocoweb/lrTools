package com.foresee.test.fileprops;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.foresee.test.util.exfile.ExtProperties;
import com.foresee.test.util.io.FileUtil;

/**
 * 在 /file.properties 中读取文件名字、参数定义
 * 
 * @author allan.xie
 *
 */
public  class FileDefinition {
    public final static String FILEField_FileName="filename";
    public final static String FILEField_Parameter="parameter";
    public final static String FILEField_Sheet="sheet";
    
    private static ExtProperties extProp = null;
    /**
     * 默认报文协议 字符串数组
     */
    private static String[] defaultTrans = new String[] {};

    /**
     * 默认参数列表 key ， value
     */
    private static Map<String, String> paraMap = new ConcurrentHashMap<String, String>();

    /**
     * 
     * @param sKey
     * @return
     */
    public static String getFileByName(String sKey) {
        if (extProp == null)
            extProp = getExtPropertiesInstance();
        return extProp.getSectionItem(sKey, FILEField_FileName);

    }

    /**
     * @param sKey
     * @return  返回某section的parameter的字符串（逗号分隔）
     */
    public static String getParaByName(String sKey) {
        if (extProp == null)
            extProp = getExtPropertiesInstance();
        return extProp.getSectionItem(sKey, FILEField_Parameter);

    }

    public static String getValueByName(String sKey) {
        if (extProp == null)
            extProp = getExtPropertiesInstance();
        return extProp.getProperty(sKey);

    }
    
    /**
     * @param sKey
     * @return 返回某section的map定义
     */
    public static Map<String, String> getParasMapByName(String sKey) {
       // Map<String, String> paraMap = new ConcurrentHashMap<String, String>();
        if (extProp == null)
            extProp = getExtPropertiesInstance();
        return extProp.getSectionItems(sKey);
        
    }

    public static String[] getDefault() {
        if (defaultTrans.length < 1) {
            defaultTrans = getValueByName("default").split(",");
        }
        return defaultTrans;

    }

    public static Map<String, String> getDefaultParas() {
        if (paraMap.size() < 1) {
            if (extProp == null)
                extProp = getExtPropertiesInstance();

            paraMap = extProp.getSectionItems("default");

        }
        return paraMap;
    }

    public static String getDefaultParaKey(int iNo) {
        return getDefaultParas().keySet().toArray()[iNo].toString();
    }

    public static String getDefaultParaValue(int iNo) {
        return getDefaultParas().values().toArray()[iNo].toString();
    }

    public static String getDefaultParaValueByKey(String skey) {
        String sresult = "";
        if (getDefaultParas().containsKey(skey)) {
            sresult = getDefaultParas().get(skey).toString();
        }
        return sresult;
    }

    private static void initxProperties(String path) {
        if (extProp == null) {
            try {
                // 从用来加载类的搜索路径打开具有指定名称的资源，以读取该资源。此方法通过系统类加载器
                // InputStream in
                // =Thread.defaultThread().getContextClassLoader().getResourceAsStream(path);
                
                File xfile = FileUtil.lookupFileInClasspath(path);

                
                InputStream in =new FileInputStream(xfile.getAbsolutePath());
                        //FileDefinition.class.getResourceAsStream(path);
                if (in == null) {
                    System.out.println("未找到 " + xfile.getAbsolutePath() + "文件！");
                }
                // 转换下，避免中文乱码
                System.out.println("==Load " + xfile.getAbsolutePath() + "文件！success");

                extProp = new ExtProperties();
                extProp.load(new InputStreamReader(in, "UTF-8"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // 静态方法访问时，直接访问不需要实例化
    public static ExtProperties getExtPropertiesInstance() {
        // synchronized表示同时只能一个线程进行实例化
        if (extProp == null) { // 如果两个进程同时进入时，同时创建很多实例，不符合单例
            synchronized (ExtProperties.class) {
                if (extProp == null) {
                    initxProperties("/file.properties");
                }
            }
        }
        return extProp;
    }

    public static void main(String[] args) {
        String sss = getFileByName("SWZJ.HXZG.SB.BCZZSXGMSB");
        System.out.println(sss);
    }

}
