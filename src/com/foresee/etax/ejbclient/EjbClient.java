package com.foresee.etax.ejbclient;

import gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EjbClient {
	
	private static IEsbXmlMessageReceiver esbClient = null;
	
	private void envInit(String contextFactory, String providerUrl, String principal, String crenentials, String ejbHome) {
		Properties properties = new Properties();
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		properties.setProperty(Context.PROVIDER_URL, providerUrl);
		properties.setProperty(Context.SECURITY_PRINCIPAL, principal);
		properties.setProperty(Context.SECURITY_CREDENTIALS, crenentials);
		
		try {
			Context context = new InitialContext(properties);
			esbClient = (IEsbXmlMessageReceiver) context.lookup(ejbHome);
			
		} catch (NamingException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IEsbXmlMessageReceiver getEjb(String contextFactory, String providerUrl, String principal, String crenentials, String ejbHome) {
		if (esbClient == null) {
			envInit(contextFactory, providerUrl, principal, crenentials, ejbHome);
		}
		return esbClient;
	}
	
	public IEsbXmlMessageReceiver getEsb() {
		PropertiesContext prop = PropertiesContext.getInstance();
		return getEjb(prop.getProperties("esb.contextFactory"), prop.getProperties("esb.providerUrl"),prop.getProperties("esb.principal"),prop.getProperties("esb.crenentials"),prop.getProperties("esb.ejbHome"));
	}
	
	public IEsbXmlMessageReceiver getOsb() {
		PropertiesContext prop = PropertiesContext.getInstance();
		return getEjb(prop.getProperties("osb.contextFactory"), prop.getProperties("osb.providerUrl"),prop.getProperties("osb.principal"),prop.getProperties("osb.crenentials"),prop.getProperties("osb.ejbHome"));
	}
	
	public IEsbXmlMessageReceiver getCurrentEsb() {
		PropertiesContext prop = PropertiesContext.getInstance();
		if (prop.getProperties("current.esb").equals("oracle")) {
			return getOsb();
		} else {
			return getEsb();
		}
	}
}
