package com.foresee.test.loadrunner.helper.testng;

import java.util.Iterator;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.helper.Arguments;
import com.foresee.test.loadrunner.helper.HttpHelper;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.http.HttpException;
import com.foresee.test.util.lang.StringUtil;

public class ArgFileHttpTest {
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
        lr.save_string("http://61.146.43.162:9999","p_tycxURL");
        //lr.save_string("http://61.146.43.162:9595","p_tycxURL");
        HttpHelper.setProxy("localhost", 8888);
        HttpHelper.setHost("61.146.43.162", 9999);
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
                 web.reg_save_param(lr.eval_string("{saveparams}"), new String []{ 
                     lr.eval_string("LB=<{catchparams}>"), 
                     lr.eval_string("RB=</{catchparams}>") });
             }
             
             //web.add_cookie("gt3nf_tycx=MQ0LJQvQnpn26k1RrzlT6CQ2YppM4znG4dzGJTpPLBlBJs20r0rS!572503777");
             
             if(lr.eval_string("{method}").indexOf("GET")>=0){
                  web.custom_request(""
                         , lr.eval_string("URL={p_tycxURL}{api}") +(StringUtil.isNotEmpty(lr.eval_string("{params}"))? lr.eval_string( lr.eval_string("?{params}")):"")
                         , new String[]{
                             lr.eval_string("Method={method}"),
                             "RecContentType=text/html", 
                             "Mode=HTML", 
                             "EncType=text/xml; charset=UTF-8",
                             "Accept=text/plain;charset=UTF-8" 
                        });
             }else{
                  web.custom_request(""
                         , lr.eval_string("URL={p_tycxURL}{api}")
                         , new String[]{
                             lr.eval_string("Method={method}"),
                             "RecContentType=text/html", 
                             "Mode=HTML", 
                             "EncType=text/xml; charset=UTF-8", 
                             "Accept=text/plain;charset=UTF-8" ,
                             StringUtil.isNotEmpty(lr.eval_string("{params}"))? lr.eval_string( lr.eval_string("Body={params}")):""
                         });
                
             }
            lr.think_time(0.5);
       }
        
    }

    @AfterTest
    public void afterTest() {
    }
}

