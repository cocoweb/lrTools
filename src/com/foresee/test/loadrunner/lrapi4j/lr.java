package com.foresee.test.loadrunner.lrapi4j;

import java.util.HashMap;
import java.util.Map;

import com.foresee.test.loadrunner.lrapi.I_lr;
import com.foresee.test.util.exfile.ParamUtils;
import com.foresee.test.util.lang.StringUtil;

/**
 * 实现包装LoadRunner的方法 //暂时不支持部分嵌套参数 //"sdf,{bb{ccc}}dfasdf,{aaa}" 不支持
 * //"sdf,{{ccc}bb}dfasdf,{aaa}"支持 //"sdf,{ccc}dfasdf,{aaa}"
 * 
 * @author Administrator
 *
 */
public class lr extends I_lr {
    public static String eval_string(String paramString) {
        return ParamUtils.replaceVariables(paramString, ParameterCache.getInstance().getMapCache());
    }

    public static int eval_int(String paramString) {
        return Integer.parseInt(ParamUtils.replaceVariables(paramString, ParameterCache.getInstance().getMapCache()));
    }

    public static int save_string(String sValue, String ParaName) {
        ParameterCache.getInstance().put(ParaName, sValue);
        return 0;
    }

    public static int save_int(int sValue, String ParaName) {
        ParameterCache.getInstance().put(ParaName, Integer.toString(sValue));
        return 0;
    }
    
    static Map<String,Transaction> transactionMap= new HashMap<String, Transaction>();
    
    static String currentTransaction = "";
    
    /**
     * @return 获取当前的事务对象
     */
    public static Transaction getCurrentTransaction(){
        if(StringUtil.isEmpty(currentTransaction)){
            return null;
        }
        return transactionMap.get(currentTransaction);
        
    }

    /**
     * 启动事务，并创建一个事务对象
     * @return
     */
    public static int start_transaction(String paramString) {
        Transaction xtran = new Transaction(paramString);
        transactionMap.put(paramString, xtran);
        currentTransaction = paramString;
       
        message(xtran.startMessage());

        return 0;
    }
    
    
    public static int end_transaction(String paramString, int paramInt) {
        Transaction xtran = transactionMap.get(paramString);
        xtran.time = end_timer(xtran.beginTime);
        
        switch(paramInt){
            case FAIL:
                break;
            case STOP:
                break;
            case AUTO:
                xtran.isOK = true;
                break;
            case PASS:
                xtran.isOK = true;
                break;
            default:
                
        
        }
        
        message(xtran.endMessage());
        currentTransaction = "";

        return 0;
    }

    public static int message(String paramString) {
        System.out.println(paramString);
        return 0;
    }

    public static int error_message(String paramString) {
        System.out.println("ERROR: " + paramString);
        return 0;
    }

}
