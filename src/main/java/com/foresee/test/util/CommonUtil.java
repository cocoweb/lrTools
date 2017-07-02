package com.foresee.test.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.foresee.test.util.http.HttpRequester;

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
	
    public static void showJVM() {
        Properties p = System.getProperties();
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }
        public static void showSystemProp(){
            Properties props=System.getProperties(); //系统属性
            System.out.println("Java的运行环境版本："+props.getProperty("java.version"));
            System.out.println("Java的运行环境供应商："+props.getProperty("java.vendor"));
            System.out.println("Java供应商的URL："+props.getProperty("java.vendor.url"));
            System.out.println("Java的安装路径："+props.getProperty("java.home"));
            System.out.println("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version"));
            System.out.println("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor"));
            System.out.println("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name"));
            System.out.println("Java的虚拟机实现版本："+props.getProperty("java.vm.version"));
            System.out.println("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor"));
            System.out.println("Java的虚拟机实现名称："+props.getProperty("java.vm.name"));
            System.out.println("Java运行时环境规范版本："+props.getProperty("java.specification.version"));
            System.out.println("Java运行时环境规范供应商："+props.getProperty("java.specification.vender"));
            System.out.println("Java运行时环境规范名称："+props.getProperty("java.specification.name"));
            System.out.println("Java的类格式版本号："+props.getProperty("java.class.version"));
            System.out.println("Java的类路径："+props.getProperty("java.class.path"));
            System.out.println("加载库时搜索的路径列表："+props.getProperty("java.library.path"));
            System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));
            System.out.println("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs"));
            System.out.println("操作系统的名称："+props.getProperty("os.name"));
            System.out.println("操作系统的构架："+props.getProperty("os.arch"));
            System.out.println("操作系统的版本："+props.getProperty("os.version"));
            System.out.println("文件分隔符："+props.getProperty("file.separator"));   //在 unix 系统中是＂／＂
            System.out.println("路径分隔符："+props.getProperty("path.separator"));   //在 unix 系统中是＂:＂
            System.out.println("行分隔符："+props.getProperty("line.separator"));   //在 unix 系统中是＂/n＂
            System.out.println("用户的账户名称："+props.getProperty("user.name"));
            System.out.println("用户的主目录："+props.getProperty("user.home"));
            System.out.println("用户的当前工作目录："+props.getProperty("user.dir"));
        }
	
}
