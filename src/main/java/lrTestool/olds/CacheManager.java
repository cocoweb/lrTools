package lrTestool;

public class CacheManager {
	volatile static CacheManager mapCacheObject;// 缓存实例对象
	
	
	public  CacheManager getCacheManager(){
		 return null;
	}
	
	/**
	 * 采用单例模式获取缓存对象实例
	 * 
	 * @return
	 */
	public static CacheManager getInstance() {
		if (null == mapCacheObject) {
			synchronized (CacheManager.class) {
				if (null == mapCacheObject) {
					//mapCacheObject = (CacheManager)getCacheManager();
				}
			}
		}
		return mapCacheObject;
	}

	public CacheManager() {
		//super();
	}

	public boolean containsKey(String skey) {
		//return cacheMap.containsKey(skey);
		return false;
	}

}