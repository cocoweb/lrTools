package com.foresee.test.loadrunner.helper;

import com.gargoylesoftware.htmlunit.HttpMethod;

public enum ApiMethod {
        /** GET. */
        GET ("GET",1),
        /** POST. */
        POST("POST",2),
        /** Link. */
        LINK("LINK",3),
        FILE("FILE",4);

    private String Name;
    private int Index;
    
    private ApiMethod(String name, int ordinal) {
            this.Name = name;
            this.Index = ordinal;
    }

    public static ApiMethod fromString(String smethod){
        if (smethod.indexOf("GET")>=0){
            return GET;
        }else if (smethod.indexOf("POST")>=0){
            return POST;
        }else if (smethod.indexOf("LINK")>=0){
            return LINK;
            
        }else if (smethod.indexOf("FILE")>=0){
            return FILE;
        }else{
            return null;
        }
        
    }
    
    public HttpMethod toHttpMethod(){
        //HttpMethod result;
        return HttpMethod.valueOf(Name);
//        switch (Index) {
//        case 1:
//            break;
//        case YELLOW:
//            color = Signal.RED;
//            break;
//        case GREEN:
//            color = Signal.YELLOW;
//            break;
//        }
    }
    
    public static ApiMethod fromHttpMethod(HttpMethod method){
        
        return ApiMethod.valueOf(method.name());
        
    }

    public static boolean isEquals(String mstring, ApiMethod method) {
       
        return  fromString(mstring).equals(method);
    }

}
