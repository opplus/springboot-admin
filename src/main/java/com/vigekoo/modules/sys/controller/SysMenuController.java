package com.vigekoo.modules.sys.controller;

import com.vigekoo.common.exception.AppException;
import com.vigekoo.common.utils.Result;
import com.vigekoo.modules.sys.entity.SysMenu;
import com.vigekoo.modules.sys.service.SysMenuService;
import com.vigekoo.common.annotation.SysLog;
import com.vigekoo.common.Constant.MenuType;
import com.vigekoo.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author oplus
 * @Description: TODO(系统菜单)
 * @date 2017-6-23 15:07
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public Result nav(){
		List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = sysUserService.getUserPermissions(getUserId());
		return Result.ok().put("menuList", menuList).put("permissions", permissions);
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenu> list(){
		List<SysMenu> menuList = sysMenuService.queryList(new HashMap<String, Object>());

		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public Result select(){
		//查询列表数据
		List<SysMenu> menuList = sysMenuService.queryNotButtonList();
		
		//添加顶级菜单
		SysMenu root = new SysMenu();
		root.setId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return Result.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public Result info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.queryObject(menuId);
		return Result.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public Result save(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);
		
		sysMenuService.save(menu);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public Result update(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);
				
		sysMenuService.update(menu);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public Result delete(long menuId){
		//判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.queryListByParentId(menuId);
		if(menuList.size() > 0){
			return Result.error("请先删除子菜单或按钮");
		}

		sysMenuService.deleteBatch(new Long[]{menuId});
		
		return Result.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new AppException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new AppException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new AppException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new AppException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new AppException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
