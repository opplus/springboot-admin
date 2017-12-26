package com.vigekoo.modules.sys.service.impl;

import com.vigekoo.common.Constant;
import com.vigekoo.modules.sys.dao.SysMenuDao;
import com.vigekoo.modules.sys.entity.SysMenu;
import com.vigekoo.modules.sys.service.SysMenuService;
import com.vigekoo.modules.sys.service.SysUserService;
import com.vigekoo.common.Constant.MenuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuDao sysMenuDao;

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public List<SysMenu> queryListByParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = queryListByParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenu> userMenuList = new ArrayList<>();
		for(SysMenu menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenu> queryListByParentId(Long parentId) {
		return sysMenuDao.queryListByParentId(parentId);
	}

	@Override
	public List<SysMenu> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	@Override
	public List<SysMenu> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public SysMenu queryObject(Long id) {
		return sysMenuDao.queryObject(id);
	}

	@Override
	public List<SysMenu> queryList(Map<String, Object> map) {
		return sysMenuDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysMenuDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysMenu menu) {
		sysMenuDao.save(menu);
	}

	@Override
	@Transactional
	public void update(SysMenu menu) {
		sysMenuDao.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		sysMenuDao.deleteBatch(ids);
	}
	
	@Override
	public List<SysMenu> queryUserList(Long userId) {
		return sysMenuDao.queryUserList(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenu> menuList = queryListByParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		
		for(SysMenu entity : menuList){
			if(entity.getType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListByParentId(entity.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
