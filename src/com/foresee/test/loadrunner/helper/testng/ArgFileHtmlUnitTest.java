package com.foresee.test.loadrunner.helper.testng;

import java.util.Iterator;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.helper.ApiMethod;
import com.foresee.test.loadrunner.helper.Arguments;
import com.foresee.test.loadrunner.helper.HtmlUnitHelper;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.http.HttpException;
import com.foresee.test.util.lang.StringUtil;

public class ArgFileHtmlUnitTest {
    String key = "foreseeapitestcase.excel";
    //String key1 = "apitestcase.excel";
    Arguments xargs;


    @BeforeTest
    public void beforeTest() {
        xargs=Arguments.getInstance();
        xargs.load(key);
         
        try {
            xargs.load("keyvalue.excel");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        lr.save_string("440100675694178","para_nsrsbh");
       // lr.save_string("http://61.146.43.162:9999","p_tycxURL");
        //lr.save_string("http://61.146.43.162:9595","p_tycxURL");
        
        HtmlUnitHelper.getInstance().setProxy("localhost", 8888);
        
    }
    
    
    @DataProvider(name = "iterator")
    public Iterator<Object[]> getData() {
        return xargs.getArgsIterator(key);
    }

    @Test(dataProvider = "iterator")
    public void testIteraorData(Object oo1) throws HttpException {
        if (lr.eval_string("{isenabled}").equals("YES")){
             System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams},{saveparams},{isenabled}")));
             
             if (StringUtil.isNotEmpty(lr.eval_string("{saveparams}"))){
                 if (lr.eval_string("{catchparams}").contains("ALL")){
                     web.reg_save_param(lr.eval_string("{saveparams}"), new String []{ 
                         "LB=", 
                         "RB=" });
                 }else{
                     web.reg_save_param(lr.eval_string("{saveparams}"), new String []{ 
                         lr.eval_string("LB=<{catchparams}>"), 
                         lr.eval_string("RB=</{catchparams}>") });
                 }
             }
             
             //web.add_cookie("gt3nf_tycx=MQ0LJQvQnpn26k1RrzlT6CQ2YppM4znG4dzGJTpPLBlBJs20r0rS!572503777");
             String xurl = lr.eval_string("{p_tycxURL}").contains("{")
                     ? lr.eval_string("URL={api}")
                     : lr.eval_string("URL={p_tycxURL}{api}");
             
             if(lr.eval_string("{method}").indexOf("GET")>=0){
                  HtmlUnitHelper.custom_request(""
                         , xurl +(StringUtil.isNotEmpty(lr.eval_string("{params}"))? lr.eval_string( lr.eval_string("?{params}")):"")
                         , new String[]{
                             lr.eval_string("Method={method}"),
                             "RecContentType=text/html", 
                             "Mode=HTML", 
                             "EncType=text/xml; charset=UTF-8",
                             "Accept=text/plain;charset=UTF-8" 
                        }); 
             }else if(ApiMethod.isEquals(lr.eval_string("{method}"),ApiMethod.POST)){
                  HtmlUnitHelper.custom_request(""
                         , xurl
                         , new String[]{
                             lr.eval_string("Method={method}"),
                             "RecContentType=text/html", 
                             "Mode=HTML", 
                             "EncType=text/xml; charset=UTF-8", 
                             "Accept=text/plain;charset=UTF-8" ,
                             StringUtil.isNotEmpty(lr.eval_string("{params}"))? lr.eval_string( lr.eval_string("Body={params}")):""
                         });
                
             }else if(ApiMethod.isEquals(lr.eval_string("{method}"),ApiMethod.LINK)){
                 HtmlUnitHelper.link(""
                         , lr.eval_string("{api}")
                         , new String[]{});
                 
             }
             if (StringUtil.isNotEmpty(lr.eval_string("{saveparams}"))){
                  System.out.println(lr.eval_string(lr.eval_string("{{saveparams}}")));
             }
            lr.think_time(1);
       }
        
    }

    @AfterTest
    public void afterTest() {
    }

}

