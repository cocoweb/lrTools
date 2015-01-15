package com.foresee.test.loadrunner.lrapi4j.helper;

import java.util.Map;

import com.foresee.test.util.cache.CacheMgr;


public class RegItemCache  {
    protected volatile static CacheMgr<RegItem> xcachemgr = new CacheMgr<RegItem>() ;
    volatile static RegItemCache mapCacheObject;// 缓存实例对象

    /**
     * 采用单例模式获取缓存对象实例
     * 
     * @return
     *  */
     public static RegItemCache getInstance() {
            if (null == mapCacheObject) {
                    synchronized (RegItemCache.class) {
                            if (null == mapCacheObject) {
                                    mapCacheObject = new RegItemCache();
                            }
                    }
            }
            return mapCacheObject;
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
     * @param skey
     * @param value
     * @return
     * @see com.foresee.test.util.cache.CacheMgr#replace(java.lang.String, java.lang.Object)
     */
    public RegItem replace(String skey, RegItem value) {
        return xcachemgr.replace(skey, value);
    }

    /**
     * @param key
     * @param value
     * @return
     * @see com.foresee.test.util.cache.CacheMgr#put(java.lang.String, java.lang.Object)
     */
    public RegItem put(String key, RegItem value) {
        System.out.println(">>>web_reg_string: ["+key+"] is OK.");
        
        return xcachemgr.put(key, value);
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
     * @return
     * @see com.foresee.test.util.cache.CacheMgr#getMapCache()
     */
    public Map<String, RegItem> getMapCache() {
        return xcachemgr.getMapCache();
    }

    /**
     * @return
     * @see com.foresee.test.util.cache.CacheMgr#getCacheSize()
     */
    public int getCacheSize() {
        return xcachemgr.getCacheSize();
    }

    public void clearAll() {
        xcachemgr.clearAll();
        
    }

}
