package com.foresee.test.util;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.List;


public class AssertionUtil {
	
	public static void assertStrEqual(String result, String expected){
		
		if (CommonUtil.isEmpty(expected))
			return;
		
		String input = result.toLowerCase().trim();
		String output = expected.toLowerCase().trim();
		
		assertEquals(input, output);
	}
	
	public static <E> void assertListNotEmpty(List<E> l){
		assertNotNull(l);
		assertTrue(l.size()>0);
	}
	
	public static <E> void assertListEmpty(List<E> l){
		assertNotNull(l);
		assertTrue(l.size()<=0);
	}
		
	public static void assertStringNotNull(String str){
		assertTrue(!CommonUtil.isEmpty(str));
	}
	
	public static void assertStringNull(String str){
		assertTrue(CommonUtil.isEmpty(str));
	}
	
	public static <T> void assertValueInAarry(T value, T[] array){
		for(int i=0; i<array.length; i++){
			if (value.equals(array[i])) {
				assertTrue(true);
				return;
			}
		}
	}
	
	public static <T> void assertValueNotInAarry(T value, T[] array){
		for(int i=0; i<array.length; i++){
			if (value.equals(array[i])) {
				assertTrue(false);
				return;
			}
		}
	}
	
	public static void assertArrayInOrder(String[] array, boolean isDesc) {
		// TODO Auto-generated method stub
		assertNotNull(array);
		assertFalse(array.length == 0);
		
		for (int i=0; i<array.length-1; i++){
			String cur = array[i];
			String next = array[i+1];
			
			if (isDesc) 
				assertTrue("Cur:"+cur+", Next:"+next, Double.parseDouble(cur) >= Double.parseDouble(next));
			else
				assertTrue("Cur:"+cur+", Next:"+next, Double.parseDouble(cur) <= Double.parseDouble(next));
		}
	}

	// 双重排序：先按array1，再按array2
	public static void assertArrayInOrder(String[] array1, String[] array2,
			boolean isDesc) {
		// TODO Auto-generated method stub
		assertNotNull(array1);
		assertNotNull(array2);
		
		assertFalse(array1.length == 0);
		assertFalse(array2.length == 0);
		assertTrue(array1.length == array2.length);
		
		for (int i=0; i<array1.length-1; i++){
			long a1_cur = Long.parseLong(array1[i]);
			long a1_next = Long.parseLong(array1[i+1]);
			long a2_cur = Long.parseLong(array2[i]);
			long a2_next = Long.parseLong(array2[i+1]);
			
			if (isDesc) {
				assertTrue("A1_Cur:"+a1_cur+", A1_Next:"+a1_next, a1_cur >= a1_next);
				if (a1_cur == a1_next)
					assertTrue("A2_Cur:"+a2_cur+", A2_Next:"+a2_next, a2_cur >= a2_next );
			} else {
				assertTrue("A1_Cur:"+a1_cur+", A1_Next:"+a1_next, a1_cur >= a1_next);
				if (a1_cur == a1_next)
					assertTrue("A2_Cur:"+a2_cur+", A2_Next:"+a2_next, a2_cur >= a2_next);
			}
		}
	}

}
