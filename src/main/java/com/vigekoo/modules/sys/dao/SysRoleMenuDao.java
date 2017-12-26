package com.vigekoo.modules.sys.dao;

import com.vigekoo.modules.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenu> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
