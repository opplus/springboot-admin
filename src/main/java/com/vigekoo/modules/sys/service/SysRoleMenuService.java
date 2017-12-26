package com.vigekoo.modules.sys.service;

import java.util.List;

public interface SysRoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	void deleteBatch(Long[] roleIds);
	
}
