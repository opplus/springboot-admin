package com.vigekoo.modules.sys.service;

import com.vigekoo.modules.sys.entity.SysConfig;

import java.util.List;
import java.util.Map;

public interface SysConfigService {
	
	/**
	 * 保存配置信息
	 */
	public void save(SysConfig config);
	
	/**
	 * 更新配置信息
	 */
	public void update(SysConfig config);
	
	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);
	
	/**
	 * 获取List列表
	 */
	public List<SysConfig> queryList(Map<String, Object> map);
	/**
	 * 获取总记录数
	 */
	public int queryTotal(Map<String, Object> map);
	
	public SysConfig queryObject(Long id);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key	key
	 */
	public String getValue(String key);
	
	/**
	 * 根据key，获取value的Object对象
	 * @param key	key
	 * @param clazz	Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);

	SysConfig queryObjectByKey(String key);
	
}
