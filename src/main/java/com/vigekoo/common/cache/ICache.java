package com.vigekoo.common.cache;

import java.util.List;

/**
 * @author oplus
 * @Description: TODO(通用缓存接口)
 * @date 2017-6-23 15:07
 */
public interface ICache {
	
	void put(String cacheName, Object key, Object value);
	
	<T> T get(String cacheName, Object key);
	
	@SuppressWarnings("rawtypes")
	List getKeys(String cacheName);
	
	void remove(String cacheName, Object key);
	
	void removeAll(String cacheName);
	
	<T> T get(String cacheName, Object key, ILoader iLoader);
	
	<T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass);
	
}
