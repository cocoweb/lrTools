package com.foresee.test.loadrunner.lrapi4j;

import com.foresee.test.util.cache.StringCacheMgr;

public class ParameterCache extends StringCacheMgr{

	volatile static ParameterCache mapCacheObject;// 缓存实例对象


	protected ParameterCache() {
		super();
	}

	/**
	 * 采用单例模式获取缓存对象实例
	 * 
	 * @return
	 *  */
	 public static ParameterCache getInstance() {
		if (null == mapCacheObject) {
			synchronized (ParameterCache.class) {
				if (null == mapCacheObject) {
					mapCacheObject = new ParameterCache();
				}
			}
		}
		return mapCacheObject;
	}

	/**
	 * 使用替换策略，相同就更换value
	 * @see com.foresee.test.util.cache.CacheMgr#putString(java.lang.String, java.lang.String)
	 */
	 @Override
	public String putString(String skey, String svalue) {
		if( containsKey(skey)){
			return replace(skey, svalue).toString();
		}
		return super.putString(skey, svalue);
	}

	
	

}