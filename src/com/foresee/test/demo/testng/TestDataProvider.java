package com.foresee.test.demo.testng;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.*;

public class TestDataProvider extends TestData {

    @Test(dataProvider="providerMethod")
    public void testmethod1(Map<?, ?> param){
        System.out.println("method1 received:"+param.get("input"));
    }
     
    @Test(dataProvider="providerMethod")
    public void testmethod2(Map<?, ?> param){
        System.out.println("method2 received:"+param.get("input"));
    }
     
    @Test(dataProvider="providerMethod")
    public void testmethod3(Map<?, ?> param){
        System.out.println("method3 received:"+param.get("input"));
    }
    
    @Test
    public void testmethod4(){
        System.out.println("method4 received:4");
    }

    @Test
    @Parameters ({"config.file","sPassword"})
    public void testparas(String config_file, String sPassword){
        System.out.println("config.file is "+ config_file);
        System.out.println("password is "+ sPassword);
    }

    
	/**
	 * �����ڵ�ÿ��Ԫ�ض�����Ϊһ���������ݱ�ִ�� On execution testEmployeeData() will be executed 4
	 * times,
	 * 
	 * ����Դ������Java���������ļ������ݿ�
	 * 
	 * @return
	 */
	@DataProvider(name = "DP1")
	public Object[][] createData() {
		Object[][] retObjArr = { { "001", "Jack", "London" },
				{ "002", "John", "New York" }, { "003", "Mary", "Miami" },
				{ "004", "George", "california" } };
		return (retObjArr);
	}

	@Test(dataProvider = "DP1")
	public void testEmployeeData(String empid, String empName, String city) {
		System.err.println(empid);
		System.err.println(empName);
		System.err.println(city);

	}
	@Test(dataProvider = "providerMethod")
	public void testMapData(String empid, String empName, String city) {
		System.err.println(empid);
		System.err.println(empName);
		System.err.println(city);

	}

	@DataProvider(name = "iterator")
	public Iterator<Object[]> getData() {
		Set<Object[]> set = new HashSet<Object[]>();
		set.add(new String[]{ "hello"});
		set.add(new String[]{"world"});
		set.add(new String[]{"china" });
		Iterator<Object[]> iterator = set.iterator();
		return iterator;
	}

	@Test(dataProvider = "iterator")
	public void testIteraorData(String iterator) {
		System.err.println("iterator  .. " + iterator);

	}
}