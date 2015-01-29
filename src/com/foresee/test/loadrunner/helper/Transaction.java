package com.foresee.test.loadrunner.helper;

public class Transaction {
    public String transName;
    public long beginTime;
    public long endTime = 0;
    public long time = 0;

    public boolean isOK = false;

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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Transaction [transName=" + transName 
                + ", beginTime=" + beginTime 
                + ", time=" + time 
                + ", isOK=" + isOK
                + "]";
    }

}
