package com.foresee.test.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

//import com.vipshop.api.model.TypeDefinition;

public class CommonUtil {
	
	public static List<NameValuePair> parseRequestParameter(Object request){
		PropertyDescriptor[] props = null;
		List<NameValuePair> nvplist = null;
		try {
			props = Introspector.getBeanInfo(request.getClass(), Object.class)
			        .getPropertyDescriptors();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (props != null) {
			nvplist = new ArrayList<NameValuePair>();
            for (int i = 0; i < props.length; i++) {
            	String value = null;
            	String name = null;
				try {
					name = keyWordRequestName(props[i].getName());
					value = (String) props[i].getReadMethod().invoke(request);
					System.out.println(name + ":" + value);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	if (null != value && !"".equals(value))
            		nvplist.add(new BasicNameValuePair(name, value));
            }
            
        }
		
		return nvplist;
		
	}

	
	
	public static boolean isEmpty(String str) {
		if (null != str && str.trim().length() > 0) {
			return false;
		}
		return true;
	}

	public static String appendParams(String param, String append) {
		if (isEmpty(param)) {
			param = "?" + append;
		} else {
			param = param + "&" + append;
		}

		return param;
	}

	public static boolean isEmpty(String[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return true;
		}
		return false;
	}

	public static String assembleArray(String[] strArray, boolean fixedSeparator) {

		if (strArray.length == 0) {
			return null;
		}

		String retStr = strArray[0];

		for (int i = 1; i < strArray.length; i++) {
			String str = strArray[i];

			if (fixedSeparator) {
				retStr = retStr + "|";
			} else {
				if (!CommonUtil.isEmpty(retStr) && !CommonUtil.isEmpty(str)) {
					retStr = retStr + "|";
				}
			}

			retStr = retStr + str;
		}

		return retStr;
	}

	public static String GBKEncode(String moviename) {
		String str = moviename;
		String strGBK = null;
		try {
			strGBK = URLEncoder.encode(str, "GBK");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strGBK;
	}

	public static String GBKEncodeReplacePercent(String moviename) {
		String str = moviename;
		String strGBK = null;
		try {
			strGBK = URLEncoder.encode(str, "GBK");
			strGBK = strGBK.replaceAll("%", "__");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strGBK;
	}

	public static <T extends Enum<T>> Object[][] enumToArray(T[] t) {
		int length = t.length;
		Object[][] retObj = new Object[length][1];

		for (int i = 0; i < length; i++) {
			retObj[i][0] = t[i].toString();
		}

		return retObj;
	}

	public static <T extends Enum<T>> T searchEnum(T[] enumValues, String value) {
		T ret = null;
		if (null == enumValues)
			return ret;

		for (T t : enumValues) {
			if (value.equals(t.toString())) {
				return t;
			}
		}
		return ret;
	}

	public static String[] getAllAlphabet() {
		String[] ret = new String[52];

		int index = 0;

		for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
			ret[index] = String.valueOf((char) i);
			index++;
		}

		for (int i = (int) 'a'; i <= (int) 'z'; i++) {
			ret[index] = String.valueOf((char) i);
			index++;
		}

		return ret;
	}

	public static Object[][] arrayToDataProvider(String[] array) {
		int length = array.length;
		Object[][] retObj = new Object[length][1];

		for (int i = 0; i < length; i++) {
			retObj[i][0] = array[i];
		}

		return retObj;
	}

	public static <T> Object[][] combineData(Object[][] data, T[] array) {
		List<Object[]> rl = new ArrayList<Object[]>();

		int col = data[0].length + 1;

		List<Object[]> dl = Arrays.asList(data);
		for (int j = 0; j < array.length; j++) {
			for (Object[] d : dl) {
				Object[] r = new Object[col];
				for (int i = 0; i < d.length; i++) {
					r[i] = d[i];
				}
				r[col - 1] = array[j].toString();
				rl.add(r);
			}
		}

		Object[][] ret = listToDataProvider(rl);

		return ret;

	}

	public static <T> Object[][] combineDataObject(Object[][] data, T[] array) {
		List<Object[]> rl = new ArrayList<Object[]>();

		int col = data[0].length + 1;

		List<Object[]> dl = Arrays.asList(data);
		for (int j = 0; j < array.length; j++) {
			for (Object[] d : dl) {
				Object[] r = new Object[col];
				for (int i = 0; i < d.length; i++) {
					r[i] = d[i];
				}
				r[col - 1] = array[j];
				rl.add(r);
			}
		}

		Object[][] ret = listToDataProvider(rl);

		return ret;

	}

	private static Object[][] listToDataProvider(List<Object[]> l) {
		int row = l.size();
		Object[] tmp = l.get(0);
		int col = tmp.length;

		Object[][] ret = new Object[row][col];

		for (int i = 0; i < row; i++) {
			tmp = l.get(i);
			for (int j = 0; j < col; j++) {
				ret[i][j] = tmp[j];
			}
		}

		return ret;

	}

	public static <T> boolean isEmptyList(List<T> list) {
		if (null == list)
			return true;
		if (list.size() <= 0)
			return true;

		return false;
	}

	public static long getUTCTime(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		long timeOffset = cal.getTimeInMillis();
		return timeOffset;
	}
	
	public static String getRandomNumber(int length){
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; i++) {
			buffer.append(random.nextInt(10));
		}
		return buffer.toString();
	}
	
	public static <T> T getRandomInArray(T[] array){
		int length = array.length;
		Random random = new Random();
		int index = random.nextInt(length);
		return array[index];
	}
	
	/**   
	 * unicode 转换成 中文   
	 */   
	  
	public static String decodeUnicode(String theString) {    
	  
		char aChar;    
		int len = theString.length();  
		
		StringBuffer outBuffer = new StringBuffer(len);    
		
		for (int x = 0; x < len;) {    
	  
			aChar = theString.charAt(x++);    
			if (aChar == '\\') {    
	  
				aChar = theString.charAt(x++);    
	  
				if (aChar == 'u') {    
					// Read the xxxx  
					int value = 0;  
					
					for (int i = 0; i < 4; i++) {  
						aChar = theString.charAt(x++);   
						switch (aChar) {    
							case '0':  
							case '1':   
							case '2': 
							case '3':  
							case '4': 
							case '5':
							case '6':    
				          	case '7':    
				          	case '8':    
				          	case '9':    
				          		value = (value << 4) + aChar - '0';    
				          		break;    
				          	case 'a':    
				          	case 'b':    
				          	case 'c':    
				          	case 'd':    
				          	case 'e':    
				          	case 'f':    
				          		value = (value << 4) + 10 + aChar - 'a';    
				          		break;    
				          	case 'A':    
				          	case 'B':    
				          	case 'C':    
				          	case 'D':    
				          	case 'E':    
				          	case 'F':    
				          		value = (value << 4) + 10 + aChar - 'A';    
				          		break;    
				          	default:    
				          		throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");    
						}    
	  
					}    
					
					outBuffer.append((char) value);    
					
				} else {    
					if (aChar == 't')    
						aChar = '\t';    
					else if (aChar == 'r')    
						aChar = '\r';    
					else if (aChar == 'n')    
						aChar = '\n';    
					else if (aChar == 'f')    
						aChar = '\f';    
	  
					outBuffer.append(aChar);    
	  
				}    
	  
	        } else   
	  
	        	outBuffer.append(aChar);    
	  
	       	}    
	  
	       	return outBuffer.toString();    
	  } 
	//
	public static String keyWordRequestName(String name){
		String keywords= "abstract,assert,boolean,break,byte"+
				",case,catch,char,class,const,continue,"+
				"default,do,double,else,enum,extends,final,"+
				"finally,float,for,if,implements,import,"+
				"instanceof,int,interface,long,native,new,"+
				"package,private,protected,public,return,short,"+
				"static,strictfp,super,switch,synchronized,"+
				"this,throw,throws,transient,try,void,"+
				"volatile,while,false,true,goto,null" ;
		Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywords.split(",")));
		if(name.endsWith("_") && keywordSet.contains(name.substring(0,name.length()-1))){
				name=name.substring(0,name.length()-1);
		}
		return name;
	}
	
	private static String addr = "http://10.100.90.66:8080/datagen";
	private static final String ARRAY_TO_JSONARRAY = "/auto/api/arrayToRequest";
	private static final String ARRAY_TO_JSON = "/auto/api/phpArrayToJson";
/*	public static String buildApiSign(Object request,TypeDefinition api ) {
		Map<String, String> jsonArrayMap = new HashMap<String, String>();
		String baseUrl = api.getBaseUrl();
		int encryptType = api.getEncryptType();
		String apiSecretName = "";
		String apiSecretValue = "";
		String dictionary = "";
		boolean useArrayForApiSign = (baseUrl.contains("//i.api") || baseUrl.contains("//address.api"));
		boolean useArrayForJson = baseUrl.contains("//message.api");
		
		PropertyDescriptor[] props = null;
		List<NameValuePair> nvplist = null;
		try {
			//获取request的值
			props = Introspector.getBeanInfo(request.getClass(), Object.class)
			        .getPropertyDescriptors();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (props != null) {
			nvplist = new ArrayList<NameValuePair>();
            for (int i = 0; i < props.length; i++) {
            	String value = null;
            	String name = null;
				try {
					name = keyWordRequestName(props[i].getName());
					value = (String) props[i].getReadMethod().invoke(request);

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if (name == null ||name.equals("")) {
					continue;
				}
				long timestamp = System.currentTimeMillis() / 1000;
				name = name.trim();
				if (value == null ||value.equals("")) {
					continue;
				}
				value = replaceParam(value.trim(), "timestamp", timestamp + "");
            	
				if(name.equals("api_secret")){
					apiSecretName=name;
					apiSecretValue = value;
				}
				
				String jsonValue = getJsonArrayFromPhpArray(value,name);//参数值为数组时，返回json
				
				boolean isArray = isArray(value);//是否为数组
				if (isArray && useArrayForApiSign) {  //如果参数时数组，并且是i或者address域时
					value = "Array";
					jsonArrayMap.put(name, jsonValue);
				} else if(isArray &&useArrayForJson){
					value = getJsonFromPhpArray(value,name) ;
					jsonArrayMap.put(name, value);
				}else {
					
					if (jsonValue == null) {
						throw new RuntimeException("field " + name + " is null!");
					}
					if (!jsonValue.equals(value)) {
						jsonArrayMap.put(name, value);
					}
					value = jsonValue;
				}
				value = ParamUtils.eval(value);
				if (!name.equals("api_secret") && !name.equals("api_key")) {
					value = value.replace("#", "%23");
				}
				value = value.replace("\r\n", "");
				
				if(!name.equals("api_secret")){
					if (null != value && !"".equals(value))
						
						nvplist.add(new BasicNameValuePair(name, value));
					}
            }
		
		}
		if(apiSecretName.equals("api_secret")){
			nvplist.add(new BasicNameValuePair("api_secret", apiSecretValue));
		}
		for (int i = 0; i < nvplist.size(); i++) {
			String name = nvplist.get(i).getName();
			if (name.equals("vipcore_c")
					|| name.equals("vipcore_a")) {
				continue;
			} 
			String value = nvplist.get(i).getValue();
			//System.err.println(name +": " + value);
			if (value == null) {
				throw new RuntimeException(name + " field must be specified!");
			}
			if (encryptType == 0) {
				dictionary += value;
			} 
			
		}
		String api_sign = "";
		try {
			api_sign = MD5.getMD5(dictionary.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return api_sign;
	}*/
	/**
	 * @param input   参数的值
	 * @param name   参数名
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String replaceParam(String input, String name, String value) {
		return input.replaceAll("\\u0024\\u007B" + name + "}", value);  //${xxxxx}
	}
	
	//参数为数组的情况   php数组获取jsonArray
	public static String getJsonArrayFromPhpArray(String input,String param) {
		//如果不是数组，则返回string
		if (!isArray(input)) {
			return input;
		}
		//如果是参数为数组，则将数组转化为组合后的json
		try {
			String url = addr + ARRAY_TO_JSONARRAY;
			return HttpRequester.sendDataPost(url, "array=" + URLEncoder.encode(input, "UTF-8")+"&param="+param);
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse php arry to json!");
		}
	}
	//参数为数组的情况   php数组获取jsonArray
	public static List<String> getJsonArrayFromMapArray(Map<String, String> map) {
			//如果不是数组，则返回string
		List<String> list = new ArrayList<String>();
		
		Iterator<Entry<String, String>> data = map.entrySet().iterator();
		while (data.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) data.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			//list.add(value);
			if (!isArray(value)) {
				list.add("");
				return list;
			}
			//如果是参数为数组，则将数组转化为组合后的json
			try {
				String url = addr + ARRAY_TO_JSONARRAY;
				list.add(HttpRequester.sendDataPost(url, "array=" + URLEncoder.encode(value, "UTF-8")+"&param="+key));
			} catch (Exception e) {
				throw new RuntimeException("Fail to parse php arry to json!");
			}
		}
		return list;
	}
	//参数为数组的情况   php数组获取json
		public static String getJsonFromPhpArray(String input,String param) {
			//如果不是数组，则返回string
			if (!isArray(input)) {
				return input;
			}
			//如果是参数为数组，则将数组转化为组合后的json
			try {
				String url = addr + ARRAY_TO_JSON;
				return HttpRequester.sendDataPost(url, "array=" + URLEncoder.encode(input, "UTF-8"));
			} catch (Exception e) {
				throw new RuntimeException("Fail to parse php arry to json!");
			}
		}
	
	public static boolean isArray(String input) {
		Pattern pattern1 = Pattern.compile("array\\u0028([\\s\\S]*?)\\u0029");
		Matcher matcher1 = pattern1.matcher(input);
		return matcher1.find();
	}
	
}
