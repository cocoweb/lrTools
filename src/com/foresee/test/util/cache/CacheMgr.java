package com.foresee.test.util.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMgr<V extends Object> {

	protected volatile Map<String, V> cacheMap ;

	public CacheMgr() {
		super();
		init();
	}
	
	protected  void init(){
		cacheMap = (Map<String, V>) new ConcurrentHashMap<String, V>();
	}

	public boolean isEmpty() {
		return cacheMap.isEmpty();
	}

	public Set<String> keySet() {
		return cacheMap.keySet();
	}

	public Collection<V> values() {
		return  cacheMap.values();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(String key) {
		return cacheMap.containsKey(key);
	}


	/**
	 * @param skey
	 * @param newValue
	 * @return
	 * @see java.util.Map#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public V replace(String skey,  V value) {
		return cacheMap.replace(skey, value);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsStringValue(String value) {
		return cacheMap.containsValue(value);
	}

	public boolean containsValue(V value) {
		return cacheMap.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(String key) {
		return cacheMap.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(String key, V value) {
		return cacheMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public synchronized String putString(String skey, String svalue) {
		if( !cacheMap.containsKey(skey)){
	
			/********** 数据处理，将数据放入cacheMap缓存中 **begin ******/
			cacheMap.put(skey,(V) svalue);
			/********** 数据处理，将数据放入cacheMap缓存中 ***end *******/
	
		}
		return svalue;
	}

	public String getString(String skey) {
		return cacheMap.get(skey).toString();
	}

	/**
	 * 返回缓存对象
	 * 
	 * @return
	 */
	public Map<String, V> getMapCache() {
			return  this.cacheMap;
	}
	

	/**
	 * 获取缓存项大小
	 * @return
	 */
	public int getCacheSize() {
		return cacheMap.size();
	}

	/**
	 * @return
	 */
	public Map<String, String> cloneStringMapCache() {
		Map<String,String> nMap=new HashMap<String,String>();
		//nMap.putAll(super.getMapCache());
	    for (Map.Entry<? extends String, ? extends Object> e : this.getMapCache().entrySet())
	        nMap.put(e.getKey(), e.getValue().toString());
		
		return nMap;
	}
	
	       //清除所有缓存 
        public  void clearAll() { 
            for (Map.Entry<? extends String, ? extends Object> e : this.getMapCache().entrySet())
                cacheMap.remove(e.getKey());

        } 


}