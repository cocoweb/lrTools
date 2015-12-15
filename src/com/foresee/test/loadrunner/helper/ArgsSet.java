package com.foresee.test.loadrunner.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.foresee.test.fileprops.ArgsFile;
import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.StringUtil;

/**
 * 参数集模式： 1、KEYVALUE模式 暂时不进入该类 
 * 2、参数串模式 
 * 3、指定字段及序号模式，即field:index...
 * 4、xml文件模式
 * @author Administrator
 *
 */
public class ArgsSet {
    public String keyName;
    private ArrayList<ArgItem> fieldList =null;
    private List<ArrayList<String>> dataList=null;
    private String fileString="";

    /**
     * @param keyName
     * @param fields
     * @param dataList
     */
    public ArgsSet(String keyName, String fields, List<ArrayList<String>> dataList) {
        this(keyName, fields.split(","), dataList);
    }

    public ArgsSet(String keyName, String[] arrfields, List<ArrayList<String>> dataList) {
        super();
        this.keyName = keyName;
        this.dataList = dataList;
        this.fieldList = new ArrayList<ArgItem>();

        try {
            for (int i = 0; i < arrfields.length; i++) {
                fieldList.add(new ArgItem(arrfields[i], i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArgsSet(String keyName,  List<ArrayList<String>> dataList){
        this(keyName,FileDefinition.getParaByName(keyName),dataList);
    }
    public ArgsSet(String keyName){
        super();
        this.keyName = keyName;
        
    }
    
    @SuppressWarnings("unchecked")
    public ArgsSet(ArgsFile argsfile){
            this(argsfile.keyName,
                    (List<ArrayList<String>>) argsfile.loadFile());
       
    }
    
    public static ArgsSet getInstance(ArgsFile argsfile) throws Exception{
        Object oo = argsfile.loadFile();
        //oo.getClass().getName()=="List"
        if (oo instanceof List){
            @SuppressWarnings("unchecked")
            ArgsSet argset = new ArgsSet(argsfile.keyName,(List<ArrayList<String>>)oo);
            return argset;
        }else if (oo instanceof String){
            ArgsSet argset = new ArgsSet(argsfile.keyName);
            argset.setFileString(paserXmlWithPara((String)oo,
                    FileDefinition.getParaByName(argsfile.keyName)));
            return argset;
        }else{
            throw new Exception("Unknown Args File content." + argsfile.keyName);
        }
        
        
    }

 
    /**
     * @param skey
     * @param index
     * @return 参数值的数组
     */
    public ArrayList<String> save_paramStringByKey(String skey, int index) {
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<String> values = dataList.get(index);
       
        Iterator<ArgItem> iter = fieldList.iterator();
        while (iter.hasNext()) {
            ArgItem item = iter.next();
            lr.save_string(values.get(item.index), item.Name);

            ret.add(values.get(item.index));

        }

        return ret;

    }
    
    /**
     * 替换XML中匹配参数为  <aaa>{p_xxxxx}</aaa>
     * @param xXml
     * @param xParas  String[] 数组类型的参数名字表
     * @return
     */
    public static String paserXmlWithPara(String xXml, String[] xParas) {
        // 循环替换参数为 <aaa>{xxxxx}</aaa>
        for (String sKey : xParas) {
            String sTmp = "";
            //解析替换参数
            
            //判断参数是否使用<> 包围
            if(sKey.startsWith("<") && sKey.endsWith(">")){             
                //兼容XML节点中包含:等其他特别字符的情况
                sKey = StringUtil.trimEnd(StringUtil.trimStart(sKey, "<"), ">");
                
                //特殊字符使用 _ 替换
                sTmp = StringUtil.createXMLNote(sKey,
                            "{p_" +
                            sKey.replaceAll("\\p{Punct}","_") +
                            "}");
                
            }else if(sKey.contains(":")){  
                String[] aKeyParas = sKey.split(":");    //0=key  1=value
                //如果包含:，代表要给出自定义的参数名
                sTmp = StringUtil.createXMLNote(aKeyParas[0],"{" + aKeyParas[1] + "}");
                sKey =aKeyParas[0];
            }else{
                //判断有没有给出默认参数值
                String sValue= FileDefinition.getDefaultParaValueByKey(sKey);
                        //aKeyParas.length >= 1 ?
                        //getDefaultParaValueByKey(aKeyParas[0]):"";
                if(sValue.isEmpty()){
                    //默认参数格式  {p_xxxx}
                    sTmp = StringUtil.createXMLNote(sKey,"{p_" + sKey + "}");                   
                }else{
                    //默认参数值  default.xxx = sValue
                    sTmp = StringUtil.createXMLNote(sKey,sValue);
                }
            }
            
            String soldValue = StringUtil.createXMLNote(sKey,StringUtil.getXMLNote(xXml, sKey));

            xXml = xXml.replaceAll(soldValue, sTmp);
        }
        return xXml;

    }

    /**
     * 替换XML中的节点为 <aaa>{p_xxxxx}</aaa>
     * @param xXml
     * @param xPara   ,逗号分隔的多个参数名字 字符串
     * @return  返回替换完成的xml
     */
    public static String paserXmlWithPara(String xXml, String xPara) {
        if(StringUtil.isNotEmpty(xPara)){
            return paserXmlWithPara(xXml, xPara.split(","));
            
        }else{
            return xXml;
        }
    }

    public Iterator<Object[]> getArgsIterator() {
        return new ArgsIterator();
    }

    /**
     * @return the fileString
     */
    public String getFileString() {
        return fileString;
    }

    /**
     * @param fileString the fileString to set
     */
    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    class ArgsIterator implements Iterator<Object[]> {
        private String skey;

        private int MAX;// xlistmap.get(skey).size() - 1;
        private int index = 0;

        public ArgsIterator() {
            super();
            this.skey = keyName;
            this.MAX = dataList.size();
        }

        public boolean hasNext() {
            return index + 1 < MAX;
        }

        public Object[] next() {
            index++;
            // save_paramStringByKey(skey, index);
            // ArrayList<String> ret = dataList.get(index);

            return new Object[]{save_paramStringByKey(skey, index)};
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            
        }
    }

    public class ArgItem {
        public String Name;
        public int index;

        /**
         * @param name
         *            形如 casename:2,method:4
         * 
         * @throws Exception
         */
        public ArgItem(String name, int index) {
            super();

            // 解析参数名字
            if (name.indexOf(":") > 0) {
                this.Name = name.split(":")[0];
                this.index = Integer.parseInt(name.split(":")[1]) - 1; // -1避免以0序号开始

            } else {
                this.Name = name;
                this.index = index;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ArgItem [Name=" + Name + ", index=" + index + "]";
        }

    }

}
