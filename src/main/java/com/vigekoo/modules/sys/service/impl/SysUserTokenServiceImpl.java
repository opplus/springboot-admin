package com.vigekoo.modules.sys.service.impl;

import com.vigekoo.common.shiro.TokenGenerator;
import com.vigekoo.modules.sys.dao.SysUserTokenDao;
import com.vigekoo.modules.sys.entity.SysUserToken;
import com.vigekoo.modules.sys.redis.SysUserTokenRedis;
import com.vigekoo.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {

	@Autowired
	private SysUserTokenDao sysUserTokenDao;

	@Autowired
	private SysUserTokenRedis sysUserTokenRedis;
	
	//24小时后过期
	private final static int EXPIRE = 86400;

	@Override
	public SysUserToken queryByUserId(Long userId) {
		SysUserToken sysUserToken=sysUserTokenRedis.get(userId);
		if(sysUserToken==null){
			sysUserToken=sysUserTokenDao.queryByUserId(userId);
			sysUserTokenRedis.saveOrUpdate(sysUserToken);
		}
		return sysUserToken;
	}

	@Override
	public SysUserToken queryByToken(String token) {
		SysUserToken sysUserToken=sysUserTokenRedis.get(token);
		if(sysUserToken==null){
			sysUserToken=sysUserTokenDao.queryByToken(token);
			sysUserTokenRedis.saveOrUpdate(sysUserToken);
		}
		return sysUserToken;
	}

	@Override
	@Transactional
	public void save(SysUserToken token){
		sysUserTokenDao.save(token);
		sysUserTokenRedis.saveOrUpdate(token);
	}

	@Override
	@Transactional
	public void update(SysUserToken token){
		sysUserTokenRedis.delete(token);
		sysUserTokenDao.update(token);
	}

	@Override
	public Map<String, Object> createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		SysUserToken tokenEntity = queryByUserId(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserToken();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			update(tokenEntity);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("token", token);
		result.put("expire", EXPIRE);
		return result;
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//修改token
		SysUserToken tokenEntity = new SysUserToken();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		update(tokenEntity);
	}

}
