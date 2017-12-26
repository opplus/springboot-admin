package com.vigekoo.modules.sys.service;

import java.util.List;

public interface SysUserRoleService {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	void delete(Long userId);

	void deleteBatch(Long[] userIds);

}
