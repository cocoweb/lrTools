package com.foresee.test.util.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class MapCacheMgr{

	//extends CacheManager {


	//private final static Log log = LogFactory.getLog(MapCacheManager.class);

	//private volatile long updateTime = 0L;// 更新缓存时记录的时间

	static Map<String, String> cacheMap = new ConcurrentHashMap<String, String>();// 缓存容器
	volatile static MapCacheMgr mapCacheObject;// 缓存实例对象

	private volatile boolean updateFlag = true;// 正在更新时的阀门，为false时表示当前没有更新缓存，为true时表示当前正在更新缓存

	public boolean isEmpty() {
		return cacheMap.isEmpty();
	}

	public Set<String> keySet() {
		return cacheMap.keySet();
	}

	public Collection<String> values() {
		return cacheMap.values();
	}

	private MapCacheMgr() {
		//this.LoadCache();// 加载缓存
		//updateTime = System.currentTimeMillis();// 缓存更新时间

	}

	/**
	 * 采用单例模式获取缓存对象实例
	 * 
	 * @return
	 *  */
	public static MapCacheMgr getInstance() {
		if (null == mapCacheObject) {
			synchronized (MapCacheMgr.class) {
				if (null == mapCacheObject) {
					mapCacheObject = new MapCacheMgr();
				}
			}
		}
		return mapCacheObject;
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
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(String value) {
		return cacheMap.containsValue(value);
	}

	public boolean containsValue(Object value) {
		return cacheMap.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public String get(String key) {
		return cacheMap.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public String put(String key, String value) {
		return cacheMap.put(key, value);
	}

	public synchronized String putString(String skey,String svalue){
		if( !cacheMap.containsKey(skey)){
			this.updateFlag = true;// 正在更新
	
			/********** 数据处理，将数据放入cacheMap缓存中 **begin ******/
			cacheMap.put(skey, svalue);
			/********** 数据处理，将数据放入cacheMap缓存中 ***end *******/
	
			this.updateFlag = false;// 更新已完成
		}
		return svalue;
	}
	public String getString(String skey){
		return cacheMap.get(skey);
	}

	/**
	 * 装载缓存
	 */
	private void LoadCache() {

		this.updateFlag = true;// 正在更新

		/********** 数据处理，将数据放入cacheMap缓存中 **begin ******/
		cacheMap.put("key1", "value1");
		cacheMap.put("key2", "value2");
		cacheMap.put("key3", "value3");
		cacheMap.put("key4", "value4");
		cacheMap.put("key5", "value5");
		/********** 数据处理，将数据放入cacheMap缓存中 ***end *******/

		this.updateFlag = false;// 更新已完成

	}
	/**
	 * 返回缓存对象
	 * 
	 * @return
	 */
	public Map<String, String> getMapCache() {

		//long currentTime = System.currentTimeMillis();

		if (this.updateFlag) {// 前缓存正在更新
			//log.info("cache is Instance .....");
			return null;

		}

		//if (this.IsTimeOut(currentTime)) {// 如果当前缓存正在更新或者缓存超出时限，需重新加载
			synchronized (this) {
				//this.ReLoadCache();
				//this.updateTime = currentTime;
			}
		//}

		return this.cacheMap;
	}

//	private boolean IsTimeOut(long currentTime) {
//
//		return ((currentTime - this.updateTime) > 1000000);// 超过时限，超时
//	}

	/**
	 * 获取缓存项大小
	 * @return
	 */
	public int getCacheSize() {
		return cacheMap.size();
	}

	/**
	 * 获取更新时间
	 * @return
	 */
//	private long getUpdateTime() {
//		return this.updateTime;
//	}

	/**
	 * 获取更新标志
	 * @return
	 */
	private boolean getUpdateFlag() {
		return this.updateFlag;
	}

	/**
	 * 重新装载
	 */
//	private void ReLoadCache() {
//		this.cacheMap.clear();
//		this.LoadCache();
//	}

}