package com.vigekoo.modules.sys.service;

import com.vigekoo.modules.sys.entity.SysUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysUserService {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUser queryByUserName(String username);
	
	/**
	 * 根据用户ID，查询用户
	 * @param id
	 * @return
	 */
	SysUser queryObject(Long id);
	
	/**
	 * 查询用户列表
	 */
	List<SysUser> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	void save(SysUser user);
	
	/**
	 * 修改用户
	 */
	void update(SysUser user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 修改密码
	 * @param user       用户
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(SysUser user, String password, String newPassword);

	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserPermissions(long userId);

}
