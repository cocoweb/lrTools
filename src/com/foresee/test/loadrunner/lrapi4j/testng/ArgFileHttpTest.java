package com.foresee.test.loadrunner.lrapi4j.testng;

import java.util.Iterator;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.helper.Arguments;

public class ArgFileHttpTest {
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
    public void testIteraorData(Object oo1) {
        System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));

    }

    @AfterTest
    public void afterTest() {
    }
}
