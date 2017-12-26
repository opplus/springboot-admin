package com.vigekoo.common.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author oplus
 * @Description: TODO(Ehcache缓存工厂)
 * @date 2017-6-23 15:07
 */
public class EhcacheFactory extends BaseCacheFactory {
	
	private static CacheManager cacheManager;
	private static volatile Object locker = new Object();
	private static final Logger log = Logger.getLogger(EhcacheFactory.class);
	
	private static CacheManager getCacheManager() {
		if (cacheManager == null) {
			synchronized (EhcacheFactory.class) {
				if (cacheManager == null) {
					cacheManager = CacheManager.create();
				}
			}
		}
		return cacheManager;
	}
	
	static Cache getOrAddCache(String cacheName) {
		CacheManager cacheManager = getCacheManager();
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			synchronized(locker) {
				cache = cacheManager.getCache(cacheName);
				if (cache == null) {
					log.warn("无法找到缓存 [" + cacheName + "]的配置, 使用默认配置.");
					cacheManager.addCacheIfAbsent(cacheName);
					cache = cacheManager.getCache(cacheName);
					log.debug("缓存 [" + cacheName + "] 启动.");
				}
			}
		}
		return cache;
	}
	
	public void put(String cacheName, Object key, Object value) {
		getOrAddCache(cacheName).put(new Element(key, value));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String cacheName, Object key) {
		Element element = getOrAddCache(cacheName).get(key);
		return element != null ? (T)element.getObjectValue() : null;
	}
	
	@SuppressWarnings("rawtypes")
	public List getKeys(String cacheName) {
		return getOrAddCache(cacheName).getKeys();
	}
	
	public void remove(String cacheName, Object key) {
		getOrAddCache(cacheName).remove(key);
	}
	
	public void removeAll(String cacheName) {
		getOrAddCache(cacheName).removeAll();
	}

}
