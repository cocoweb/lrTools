package com.foresee.test.api.model;


public class TypeDefinition {
	private String baseUrl;     //
	private int encryptType;
	private String dictionary;
	private int method;
	
	
	public int getMethod() {
		return method;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	public String getDictionary() {
		return dictionary;
	}
	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}
	public int getEncryptType() {
		return encryptType;
	}
	public void setEncryptType(int encryptType) {
		this.encryptType = encryptType;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
