package com.vigekoo.common.cache;

/**
 * @author oplus
 * @Description: TODO(缓存工厂基类)
 * @date 2017-6-23 15:07
 */
public abstract class BaseCacheFactory implements ICache {

	@SuppressWarnings("unchecked")
	public <T> T get(String cacheName, Object key, ILoader iLoader) {
		Object data = get(cacheName, key);
		if (data == null) {
			data = iLoader.load();
			put(cacheName, key, data);
		}
		return (T) data;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass) {
		Object data = get(cacheName, key);
		if (data == null) {
			try {
				ILoader dataLoader = iLoaderClass.newInstance();
				data = dataLoader.load();
				put(cacheName, key, data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return (T) data;
	}

}
