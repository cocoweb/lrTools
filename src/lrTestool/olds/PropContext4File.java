package lrTestool.olds;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lrTestool.XmlDefinition;

import com.foresee.test.util.ExtProperties;

public class PropContext4File {
	//private  Properties prop = null;
	private  ExtProperties xprop = null;
	private static PropContext4File pc = null;
	
	private PropContext4File() {
		this(null);
	}
	
	private PropContext4File(String path) {
		if (path == null || path.equals("")) {
			path = "/file.properties";
		}
		initxProperties(path);
	}
	
	private void initxProperties(String path) {
		if (xprop==null){
			try {
			    //从用来加载类的搜索路径打开具有指定名称的资源，以读取该资源。此方法通过系统类加载器   
		        //InputStream in =Thread.defaultThread().getContextClassLoader().getResourceAsStream(path);  
	
				InputStream in = XmlDefinition.class.getResourceAsStream(path);
				if (in == null) {
					System.out.println("未找到"+path+"文件！");
				}
				//转换下，避免中文乱码
				System.out.println("==Load"+path+"文件！");

				xprop = new ExtProperties();
				xprop.load(new InputStreamReader(in,"UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	//静态方法访问时，直接访问不需要实例化   
    public static PropContext4File getInstance(){
    	//synchronized表示同时只能一个线程进行实例化
        if(pc == null){  //如果两个进程同时进入时，同时创建很多实例，不符合单例
        	synchronized(PropContext4File.class){
        		if(pc==null){
        			pc = new PropContext4File();
        		}
        	}
        }  
        return pc;
	}
    
	public String getProp(String key) {
		if (xprop != null) {
			return xprop.getProperty(key);
		}
		return null;
	}
	
	/**
	 * @return  返回唯一的Properties对象
	 */
	public ExtProperties getProperties() {

		try {
			if (xprop == null) {
				throw new Exception("file.properties文件尚未加载");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xprop;
	}
}