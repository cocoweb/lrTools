package com.foresee.etax.ejbclient;

public class StringUtil {
	
	public static boolean findXML(String xxml,String substr){
	   return (xxml.indexOf(substr) != -1);
	
	}
	
	public  static String getXMLNote(String xxml,String xNote){
	    String value = "";
	    if (xxml.indexOf("<" + xNote + ">") != -1 && xxml.indexOf("</" + xNote + ">") != -1) {
		value = xxml.substring(xxml.indexOf("<" + xNote + ">") + ("<" + xNote + ">").length(), xxml.indexOf("</" + xNote + ">"));
	    }
	    return value;
	}
	
}
