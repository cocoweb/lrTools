package com.foresee.test.loadrunner.lrapi4j;

public class Transaction {
    String transName;
    long beginTime;
    long endTime = 0;
    long time = 0;

    boolean isOK = false;

    public Transaction(String paramString) {
        transName = paramString;
        beginTime = System.currentTimeMillis();
        
    }
    
    public String startMessage(){
        return ">>>Start transaction:"+transName+" <<<";
    }
    
    public String endMessage(){
        return ">>>End transaction:"+transName+" <<<" +
                (isOK?"成功":"失败")+
                ">>>耗时:"+Long.toString(time)+"ms";
    }

}
