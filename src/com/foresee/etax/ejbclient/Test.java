package com.foresee.etax.ejbclient;

import gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Test {
	
	public static void main(String[] args) {
		EjbClient ejb = new EjbClient();
//		PropertiesContext prop = new PropertiesContext();
//		IEsbXmlMessageReceiver esb = ejb.getEjb(prop.getProperties("esb.contextFactory"), prop.getProperties("esb.providerUrl"),prop.getProperties("esb.principal"),prop.getProperties("esb.crenentials"),prop.getProperties("esb.ejbHome"));
//		IEsbXmlMessageReceiver esb = ejb.getEjb("weblogic.jndi.WLInitialContextFactory", "t3://86.12.112.14:8001,86.12.112.14:8002,86.12.112.14:8003,86.12.112.14:8004,86.12.112.16:8001,86.12.112.16:8002,86.12.112.16:8003,86.12.112.16:8004", "", "","gt3.esb.ejb.AipEJBAdapter.NF.JEJB#gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver");
//		IEsbXmlMessageReceiver esb = ejb.getEjb("weblogic.jndi.WLInitialContextFactory", "t3://86.12.73.10:8001,86.12.73.10:8002,86.12.73.11:8001,86.12.73.11:8002,86.12.73.11:8003,86.12.73.12:8001,86.12.73.12:8002,86.12.73.12:8003", "", "","gt3.esb.ejb.AipEJBAdapterXml");
		IEsbXmlMessageReceiver esb = ejb.getCurrentEsb();
		
		InputStream inputStream = Test.class.getResourceAsStream("/SWZJ.HXZG.FP.BCFPYJJXX-保存发票验交旧信息-request.xml");
		if (inputStream == null) {
			System.out.println("未找到文件！");
		}
		
		String xml = "";
		try {
			int length = inputStream.available();
			byte[] bytes = new byte[length];
			inputStream.read(bytes);
			xml = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (int i=1; i<10; i++){ 
			long beginTime = System.currentTimeMillis();
			//String retXml = esb.receiveMessageXML(xml);
//			System.out.println(retXml);
			System.out.println(xml);
			System.out.println("Time"+i+":" + String.valueOf(System.currentTimeMillis() - beginTime));
	    }
	}
}
