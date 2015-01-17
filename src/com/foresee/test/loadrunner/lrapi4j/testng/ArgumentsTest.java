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

    @BeforeTest
    public void beforeTest() {
        Arguments.loadExcelArgumentsByKey(key);
        Arguments.loadExcelArgumentsByKey(key1);
        try {
            Arguments.loadKEYVALUE();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @DataProvider(name = "iterator")
    public Iterator<Object[]> getData() {
        return Arguments.getArgsIterator(key1);
    }

    @Test(dataProvider = "iterator")
    public void testIteraorData(Object oo1, Object oo2, Object oo3, Object oo4, Object oo5) {
        System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));

    }

    @AfterTest
    public void afterTest() {
    }

    @Test
    public void ff() throws Exception {

        System.out.println(("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}"));
        System.out.println("{casename},{method},{api},{params},{catchparams}");

        Iterator<?> iters = Arguments.getArgsIterator(key);
        Iterator<?> iters1 = Arguments.getArgsIterator(key1);
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
        Iterator<ArrayList<String>> xiters = Arguments.getArgsIteratorByKey(key);
        // while(xiters.hasNext()){
        // // System.out.println(xiters.next().toString());
        // }

        ArrayList<ArgItem> argsnames = Arguments.getArgsNameByKey(key);
        System.out.println(argsnames.toString());
    }

    @Test
    public void loadKEYVALUE() throws Exception {
        Arguments.loadKEYVALUE();
        System.out.println(lr.eval_string("{admin_phone}{admin_wb}{wb_name}"));
    }


}
