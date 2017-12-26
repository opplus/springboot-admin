package com.vigekoo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.vigekoo.modules.sys.dao.GeneralsDao;
import com.vigekoo.modules.sys.entity.Generals;
import com.vigekoo.modules.sys.service.GeneralsService;

@Service("generalsService")
public class GeneralsServiceImpl implements GeneralsService {

	@Autowired
	private GeneralsDao generalsDao;
	
	@Override
	public Generals queryObject(Integer id){
		return generalsDao.queryObject(id);
	}
	
	@Override
	public List<Generals> queryList(Map<String, Object> map){
		return generalsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return generalsDao.queryTotal(map);
	}
	
	@Override
	public void save(Generals generals){
		generalsDao.save(generals);
	}
	
	@Override
	public void update(Generals generals){
		generalsDao.update(generals);
	}
	
	@Override
	public void delete(Integer id){
		generalsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		generalsDao.deleteBatch(ids);
	}
	
}
