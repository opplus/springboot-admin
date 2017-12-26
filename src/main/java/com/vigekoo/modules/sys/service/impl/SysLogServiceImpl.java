package com.vigekoo.modules.sys.service.impl;

import com.vigekoo.modules.sys.dao.SysLogDao;
import com.vigekoo.modules.sys.service.SysLogService;
import com.vigekoo.modules.sys.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;
	
	@Override
	public SysLog queryObject(Long id){
		return sysLogDao.queryObject(id);
	}
	
	@Override
	public List<SysLog> queryList(Map<String, Object> map){
		return sysLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLogDao.queryTotal(map);
	}
	
	@Override
	public void save(SysLog sysLog){
		sysLogDao.save(sysLog);
	}
	
	@Override
	public void delete(Long id){
		sysLogDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysLogDao.deleteBatch(ids);
	}
	
}
