package com.foresee.test.loadrunner.sc;

import java.io.File;
import java.util.Date;

import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.exfile.POIExcelMakerUtil;
import com.foresee.test.util.io.FileUtil;
import com.foresee.test.util.lang.DateUtil;

public class ActionGRSDS {
    static String FileRoot="D:\\tmp\\";
    static String GRSDS_Template =FileRoot+ "扣缴个人所得税报告表模板(2016)-测试8657 -bak2.xls";

    public ActionGRSDS() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @return 返回个人所得税上报的文件名
     */
    public static String genGRSDSFileName(){  

        String sDate = lr.eval_string("{p_date}");
        return lr.eval_string("扣缴个人所得税报告表{para_nsrsbh}"+sDate+".xls");

    }

    /**
     * 生成发票认证的文件,并散列保存
     * @param fileName
     * @return
     */
    public static String genGRSDSFile(String fileName){
        //散列文件存放 
        
        String grsdsFile = FileRoot+
                distDir(fileName,27)+
                fileName;

        if (!FileUtil.FileExist(grsdsFile)) {   //如果文件不存在,就创建
            FileUtil.Copy(GRSDS_Template,
                      grsdsFile);
            lr.message("==生成个人所得税文件: "+grsdsFile);
        }else{
            lr.message("==个人所得税文件已经存在: "+grsdsFile);

        }

        return grsdsFile; 

    }
    public static String distDir(String sourceStr, int index) {
        int li = sourceStr.lastIndexOf(".");
        sourceStr.substring(li-3, li-1);

        return sourceStr.substring(li-3, li-1) + "\\" + sourceStr.substring(li-5, li-3) + "\\";
       //return sourceStr.substring(index, index + 2) + "\\" + sourceStr.substring(index + 2, index + 4) + "\\";

    }
    public static String distDir(String sourceStr) {
        int li = sourceStr.lastIndexOf(".");
        sourceStr.substring(li-3, li-1);

        return sourceStr.substring(li-4, li-2) + "\\" + sourceStr.substring(li-2, li) + "\\";
       //return sourceStr.substring(index, index + 2) + "\\" + sourceStr.substring(index + 2, index + 4) + "\\";

    }
    
    public static void updateExcel(String fileName){
        try {
            POIExcelMakerUtil excelutil = new POIExcelMakerUtil(new File(fileName));
            excelutil.writeToExcel("扣缴个人所得税报告表", "D3", lr.eval_string("{para_nsrsbh}"));
            excelutil.writeToExcel("扣缴个人所得税报告表", "G3", lr.eval_string("{para_nsrmc}"));
            
            excelutil.writeAndClose();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    
    

    public static void main(String[] args) {
        
//        lr.save_string(DateUtil.format(new Date(),"yyyyMMddHHmmss"), "p_date");  //扣缴个人所得税报告表abcdefg20151214163452.xls
//        lr.save_string("abcdefg","para_nsrsbh");
//        lr.save_string("上下左右","para_nsrmc");
//        
//        String rzfileName = genGRSDSFileName();
//        String rzfilePath = genGRSDSFile(rzfileName);
//        updateExcel(rzfilePath);
//        
// 
//        System.out.println(rzfileName);
        
        System.out.println(distDir("扣缴个人所得税报告表abcdefg20151214171836.xls"));
        

    }

}
