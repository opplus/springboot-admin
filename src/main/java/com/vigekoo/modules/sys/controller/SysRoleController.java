package com.vigekoo.modules.sys.controller;

import com.vigekoo.common.annotation.SysLog;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.common.utils.Query;
import com.vigekoo.common.utils.Result;
import com.vigekoo.common.validator.ValidatorUtils;
import com.vigekoo.modules.sys.entity.SysRole;
import com.vigekoo.modules.sys.service.SysRoleMenuService;
import com.vigekoo.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(角色管理)
 * @date 2017-6-23 15:07
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysRole> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public Result select(){
		Map<String, Object> map = new HashMap<>();
		List<SysRole> list = sysRoleService.queryList(map);
		return Result.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public Result info(@PathVariable("roleId") Long roleId){
		SysRole role = sysRoleService.queryObject(roleId);
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		return Result.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody SysRole role){
		ValidatorUtils.validateEntity(role);
		sysRoleService.save(role);
		return Result.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody SysRole role){
		ValidatorUtils.validateEntity(role);
		sysRoleService.update(role);
		return Result.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		return Result.ok();
	}
}
