package com.vigekoo.common.cache;

import java.util.List;

/**
 * @author oplus
 * @Description: TODO(缓存工具类)
 * @date 2017-6-23 15:07
 */
public class CacheKit {
	
	private static ICache defaultCacheFactory = new EhcacheFactory();

	public static void put(String cacheName, Object key, Object value) {
		defaultCacheFactory.put(cacheName, key, value);
	}
	
	public static <T> T get(String cacheName, Object key) {
		return defaultCacheFactory.get(cacheName, key);
	}
	
	@SuppressWarnings("rawtypes")
	public static List getKeys(String cacheName) {
		return defaultCacheFactory.getKeys(cacheName);
	}
	
	public static void remove(String cacheName, Object key) {
		defaultCacheFactory.remove(cacheName, key);
	}
	
	public static void removeAll(String cacheName) {
		defaultCacheFactory.removeAll(cacheName);
	}
	
	public static <T> T get(String cacheName, Object key, ILoader iLoader) {
		return defaultCacheFactory.get(cacheName, key, iLoader);
	}
	
	public static <T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass) {
		return defaultCacheFactory.get(cacheName, key, iLoaderClass);
	}
	
}


