package com.foresee.test.loadrunner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;

import lrapi.lr;

/**
 * Loadrunner Action 继承父类
 * 
 * @author Administrator
 *  
 */
public abstract class LrActionClass {
 
	static final String ENDFORM = "ENDFORM";
	static final String LAST = "LAST";
	static final String ENDITEM = "ENDITEM";
	static final String ITEMDATA = "ITEMDATA";
	static final String STARTHIDDENS = "STARTHIDDENS";
	static final String ENDHIDDENS = "ENDHIDDENS";
	static final String CONNECT = "CONNECT";
	static final String RECEIVE = "RECEIVE";
	static final String RESOLVE = "RESOLVE";
	static final String REQUEST = "REQUEST";
	static final String RESPONSE = "RESPONSE";
	static final String EXTRARES = "EXTRARES";
	static int _webresult;

    public LrActionClass() {
        super();
    }
    public static String ConvertCharset(String inStr,String srcCharset){
        return ConvertCharset(inStr,srcCharset,"UTF-8");
    }
    
    public static String ConvertCharset(String inStr){
        return ConvertCharset(inStr,"GBK");
    }
    public static String ConvertCharset(String inStr,String srcCharset,String targetCharset){
        String xstr =null;
        try {
            
            xstr = new String(inStr.getBytes(srcCharset),targetCharset);
        } catch (UnsupportedEncodingException e) {
            
            e.printStackTrace();
        }
        return xstr;
    }

    public String distDir(String sourceStr, int index) {

        return sourceStr.substring(index, index + 2) + "\\" + sourceStr.substring(index + 2, index + 4) + "\\";

    }

    public boolean fileExist(String filePath) {
        // 判断文件or目录是否存在，如果不存在，则把目录都创建起来
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        } else {
            return true;
        }
        return false;

    }

    public int newFile(String strxml, String path) {
        File file = new File(path);
        if (fileExist(path)) {
            return -1;
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(strxml);
            bw.flush();
            bw.close();
            fw.close();
            // file.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return 0;
    }
	/**
	 * 输出报告
	 * 
	 * @param bSuccess   成功标志
	 * @param transName   事务名
	 * @param retXML      返回的字符串
	 * @param beginTimer       原来的起始时间
	 */
	public void reportOut(boolean bSuccess, String transName, String retXML, long beginTimer) {
	    long ltime = lr.end_timer(beginTimer);
	    String stime = ltime<10000 ? Long.toString(ltime):"==too long=="+Long.toString(ltime)+" ";
	    if(bSuccess){
			lr.message(stime+" "+lr.eval_string(transName+" 成功!:NSRSBH:[{para_nsrsbh}]:返回:")+ retXML  );
			lr.end_transaction(transName, lr.AUTO);
	    }else{
			lr.error_message(stime+" "+lr.eval_string(transName+" 失败:NSRSBH:[{para_nsrsbh}]:返回:["+retXML+"]"));
			lr.end_transaction(transName, lr.FAIL);
	
	    }
	}
	/**
	 * 执行一个匿名定义的transaction
	 * @param xDo  InnerITrans的实例对象,匿名方式实现为主
	 * @return  返回事务是否成功
	 */
	protected boolean runTransaction(InnerITrans xDo) throws Throwable {
		    	//before transaction code
		    	
		    	//run transaction
		    	boolean ret = xDo.RunTrans();
		    	
				//after transaction code
	
			    
			    return ret;
		    }
    /**
     * 调用模板,直接Copy,然后填空
     * @throws Throwable
     */
    public  void run_Temp() throws Throwable{
		(new InnerITrans(""){
			@Override
			public boolean doTrans() throws Throwable {
	     
		        
		        
		        return true;
		    }}).RunTrans();
	}

}