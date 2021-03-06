package com.foresee.test.util.cache;
import java.util.*; 
import java.util.Map.Entry;

//Description: 管理缓存 

//可扩展的功能：当chche到内存溢出时必须清除掉最早期的一些缓存对象，这就要求对每个缓存对象保存创建时间 

public class CacheManager { 
	private static HashMap<String, Object> cacheMap = new HashMap<String, Object>(); 

	//单实例构造方法 
	private CacheManager() { 
		super(); 
	} 
	
	//获取String的缓存 
	public static String getStringItem(String key){ 
		try{ 
			return  cacheMap.get(key).toString(); 
		}catch(NullPointerException e){ 
			return null; 
		} 
	} 
	//设置String的缓存 
	public synchronized static boolean setStringItem(String key,String value){ 
		if (cacheMap.get(key) == null) { 
			cacheMap.put(key,value); 
			return true; 
		}else{ 
			return false; 
		} 
	}

	//获取布尔值的缓存 
	public static boolean getSimpleFlag(String key){ 
		try{ 
			return (Boolean) cacheMap.get(key); 
		}catch(NullPointerException e){ 
			return false; 
		} 
	} 
	//设置布尔值的缓存 
	public synchronized static boolean setSimpleFlag(String key,boolean flag){ 
		if (flag && getSimpleFlag(key)) {//假如为真不允许被覆盖 
			return false; 
		}else{ 
			cacheMap.put(key, flag); 
			return true; 
		} 
	}
	public static long getServerStartdt(String key){ 
		try { 
			return (Long)cacheMap.get(key); 
		} catch (Exception ex) { 
			return 0; 
		} 
	} 
	public synchronized static boolean setSimpleFlag(String key,long serverbegrundt){ 
		if (cacheMap.get(key) == null) { 
			cacheMap.put(key,serverbegrundt); 
			return true; 
		}else{ 
			return false; 
		} 
	} 


	//得到缓存。同步静态方法 
	private synchronized static CacheItem getCache(String key) { 
		return (CacheItem) cacheMap.get(key); 
	} 

	//判断是否存在一个缓存 
	private synchronized static boolean hasCache(String key) { 
		return cacheMap.containsKey(key); 
	} 

	//清除所有缓存 
	public synchronized static void clearAll() { 
		cacheMap.clear(); 
	} 

	//清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配 
	public synchronized static void clearAll(String type) { 
		Iterator<Entry<String, Object>>  i = cacheMap.entrySet().iterator(); 
		String key; 
		ArrayList<String> arr = new ArrayList<String>(); 
		try { 
			while (i.hasNext()) { 
				java.util.Map.Entry<String, Object> entry =  i.next(); 
				key =   entry.getKey(); 
				if (key.startsWith(type)) { //如果匹配则删除掉 
					arr.add(key); 
				} 
			} 
			for (int k = 0; k < arr.size(); k++) { 
				clearOnly(arr.get(k).toString()); 
			} 
		} catch (Exception ex) { 
			ex.printStackTrace(); 
		} 
	} 

	//清除指定的缓存 
	public synchronized static void clearOnly(String key) { 
		cacheMap.remove(key); 
	} 

	//载入缓存 
	public synchronized static void putCache(String key, CacheItem obj) { 
		cacheMap.put(key, obj); 
	} 

	//获取缓存信息 
	public static CacheItem getCacheInfo(String key) { 

		if (hasCache(key)) { 
			CacheItem cache = getCache(key); 
			if (cacheExpired(cache)) { //调用判断是否终止方法 
				cache.setExpired(true); 
			} 
			return cache; 
		}else 
			return null; 
	} 

	//载入缓存信息 
	public static void putCacheInfo(String key, CacheItem obj, long dt,boolean expired) { 
		CacheItem cache = new CacheItem(); 
		cache.setKey(key); 
		cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存 
		cache.setValue(obj); 
		cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE 
		cacheMap.put(key, cache); 
	} 
	//重写载入缓存信息方法 
	public static void putCacheInfo(String key,CacheItem obj,long dt){ 
		CacheItem cache = new CacheItem(); 
		cache.setKey(key); 
		cache.setTimeOut(dt+System.currentTimeMillis()); 
		cache.setValue(obj); 
		cache.setExpired(false); 
		cacheMap.put(key,cache); 
	} 

	//判断缓存是否终止 
	public static boolean cacheExpired(CacheItem cache) { 
		if (null == cache) { //传入的缓存不存在 
			return false; 
		} 
		long nowDt = System.currentTimeMillis(); //系统当前的毫秒数 
		long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数 
		if (cacheDt <= 0||cacheDt > nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE 
			return false; 
		} else { //大于过期时间 即过期 
			return true; 
		} 
	} 

	//获取缓存中的大小 
	public static int getCacheSize() { 
		return cacheMap.size(); 
	} 

	//获取指定的类型的大小 
	public static int getCacheSize(String type) { 
		int k = 0; 
		Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator(); 
		String key; 
		try { 
			while (i.hasNext()) { 
				java.util.Map.Entry<String, Object> entry = (java.util.Map.Entry<String, Object>) i.next(); 
				key = (String) entry.getKey(); 
				if (key.indexOf(type) != -1) { //如果匹配则删除掉 
					k++; 
				} 
			} 
		} catch (Exception ex) { 
			ex.printStackTrace(); 
		} 

		return k; 
	} 

	//获取缓存对象中的所有键值名称 
	@SuppressWarnings("finally")
	public static ArrayList<String> getCacheAllkey() { 
		ArrayList<String> a = new ArrayList<String>(); 
		try { 
			Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator(); 
			while (i.hasNext()) { 
				java.util.Map.Entry<String, Object> entry =  i.next(); 
				a.add(  entry.getKey()); 
			} 
		} catch (Exception ex) {} finally { 
			return a; 
		} 
	} 

	//获取缓存对象中指定类型 的键值名称 
	@SuppressWarnings("finally")
    public static ArrayList<String> getCacheListkey(String type) { 
		ArrayList<String> a = new ArrayList<String>(); 
		String key; 
		try { 
			Iterator<Entry<String, Object>>  i = cacheMap.entrySet().iterator(); 
			while (i.hasNext()) { 
				java.util.Map.Entry<String, Object> entry =   i.next(); 
				key = (String) entry.getKey(); 
				if (key.indexOf(type) != -1) { 
					a.add(key); 
				} 
			} 
		} catch (Exception ex) {} finally { 
			return a; 
		} 
	} 

} 

