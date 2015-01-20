package com.foresee.test.loadrunner.lrapi4j.testng;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.helper.ArgsSet.ArgItem;
import com.foresee.test.loadrunner.lrapi4j.helper.Arguments;

public class ArgumentsTest {
    String key = "apitestcase0.excel";
    String key1 = "apitestcase.excel";
    Arguments xargs;

    @BeforeTest
    public void beforeTest() {
        xargs=Arguments.getInstance();
        Arguments.loadExcelArgumentsByKey(key);
        Arguments.loadExcelArgumentsByKey(key1);
        try {
            Arguments.loadExcelArgumentsByKey("keyvalue.excel");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @DataProvider(name = "iterator")
    public Iterator<Object[]> getData() {
        return Arguments.getInstance().getArgsIterator(key1);
    }

    @Test(dataProvider = "iterator")
    public void testIteraorData(Object oo1) {
        System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));

    }

    @AfterTest
    public void afterTest() {
    }

    @Test
    public void ff() throws Exception {

        System.out.println(("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}"));
        System.out.println("{casename},{method},{api},{params},{catchparams}");

        Iterator<?> iters = xargs.getArgsIterator(key);
        Iterator<?> iters1 = xargs.getArgsIterator(key1);
        while (iters.hasNext()) {
            iters.next();
            iters1.next();
            System.out.println(lr.eval_string(lr
                    .eval_string("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}")));
            System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));

        }

        for (int i = 1; i < 5; i++) {
            // Arguments.save_paramStringByKey(key, i);
            // System.out.println(lr.eval_string("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}"));
        }
    }

    @Test
    public void loadExcelArgumentsByName() throws Exception {
        Iterator<ArrayList<String>> xiters = xargs.getArgsIteratorByKey(key);
        // while(xiters.hasNext()){
        // // System.out.println(xiters.next().toString());
        // }

        ArrayList<ArgItem> argsnames = xargs.getArgsNameByKey(key);
        System.out.println(argsnames.toString());
    }

    @Test
    public void loadKEYVALUE() throws Exception {
        Arguments.loadExcelArgumentsByKey("keyvalue.excel");
        System.out.println(lr.eval_string("{admin_phone}{admin_wb}{wb_name}"));
    }


}
