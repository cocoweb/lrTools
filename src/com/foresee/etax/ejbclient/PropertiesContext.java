package com.foresee.etax.ejbclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author jinweibo jinweibo@foresee.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class PropertiesContext {
	private static Properties prop = null;
	private static PropertiesContext pc = null;

	private PropertiesContext() {
		initProperties(null);
	}

	private PropertiesContext(String path) {
		initProperties(path);
	}

	private void initProperties(String path) {
		if (path == null || path.equals("")) {
			path = "/ejbclient.properties";
		}
		InputStream inputStream = Test.class.getResourceAsStream(path);
		if (inputStream == null) {
			System.out.println("未找到ejbclient.properties文件！");
		}

		prop = new Properties();
		try {
			System.out.println("==Load ejbclient.properties文件！");
			prop.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 静态方法访问时，直接访问不需要实例化
	public static PropertiesContext getInstance() {
		// synchronized表示同时只能一个线程进行实例化
		if (null ==pc) { // 如果两个进程同时进入时，同时创建很多实例，不符合单例
			synchronized (PropertiesContext.class) {
				if ( null==pc) {
					pc = new PropertiesContext();
				}
			}
		}
		return pc;
	}

	public String getProperties(String key) {
		if (prop != null) {
			return prop.getProperty(key);
		}
		return null;
	}
}
