package com.vigekoo.modules.sys.service;

import com.vigekoo.modules.sys.entity.Generals;
import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(兵器)
 * @date 2017-12-19 13:59:34
 */
public interface GeneralsService {
	
	Generals queryObject(Integer id);
	
	List<Generals> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Generals generals);
	
	void update(Generals generals);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

}
