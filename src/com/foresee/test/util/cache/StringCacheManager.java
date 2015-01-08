package com.foresee.test.util.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class StringCacheManager {   //extends CacheMgr {
	
	protected volatile static CacheMgr<String> xcachemgr = new CacheMgr<String>() ;

	protected volatile static StringCacheManager stringCacheObject;// 缓存实例对象

	protected StringCacheManager() {
		super();

	}


	/**
	 * 采用单例模式获取缓存对象实例
	 * 
	 * @return
	 *  */
	public static StringCacheManager getInstance() {
		if (null == stringCacheObject) {
			synchronized (StringCacheManager.class) {
				if (null == stringCacheObject) {
					stringCacheObject = new StringCacheManager();
				}
			}
		}
		return  stringCacheObject;
	}

	public Collection<String> stringValues() {
		//Collection<Object> cc =  getInstance().values();
		return xcachemgr.values();
	}


	/**
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#keySet()
	 */
	public Set<String> keySet() {
		return xcachemgr.keySet();
	}


	/**
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#values()
	 */
	public Collection<String> values() {
		return xcachemgr.values();
	}


	/**
	 * @param key
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#get(java.lang.String)
	 */
	public Object get(String key) {
		return xcachemgr.get(key);
	}


	/**
	 * @param key
	 * @param value
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#put(java.lang.String, java.lang.Object)
	 */
	public String put(String key, String value) {
		return xcachemgr.put(key, value);
	}


	/**
	 * @param key
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#containsKey(java.lang.String)
	 */
	public boolean containsKey(String key) {
		return xcachemgr.containsKey(key);
	}


	/**
	 * @param value
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#containsValue(java.lang.String)
	 */
	public boolean containsValue(String value) {
		return xcachemgr.containsValue(value);
	}


	/**
	 * @param skey
	 * @param svalue
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#putString(java.lang.String, java.lang.String)
	 */
	public String putString(String skey, String svalue) {
		return xcachemgr.putString(skey, svalue);
	}


	/**
	 * @param skey
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#getString(java.lang.String)
	 */
	public String getString(String skey) {
		return xcachemgr.getString(skey);
	}


	/**
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#getMapCache()
	 */
	public Map<String, String> getMapCache() {
		return xcachemgr.getMapCache();
	}


	/**
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#getCacheSize()
	 */
	public int getCacheSize() {
		return xcachemgr.getCacheSize();
	}


	/**
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#cloneStringMapCache()
	 */
	public Map<String, String> cloneStringMapCache() {
		return xcachemgr.cloneStringMapCache();
	}


	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return xcachemgr.toString();
	}


	/**
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#isEmpty()
	 */
	public boolean isEmpty() {
		return xcachemgr.isEmpty();
	}


	/**
	 * @param skey
	 * @param value
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#replace(java.lang.String, java.lang.Object)
	 */
	public String replace(String skey, String value) {
		return xcachemgr.replace(skey, value);
	}


	/**
	 * @param value
	 * @return
	 * @see com.foresee.test.util.cache.CacheMgr#containsStringValue(java.lang.String)
	 */
	public boolean containsStringValue(String value) {
		return xcachemgr.containsStringValue(value);
	}
	
	


}