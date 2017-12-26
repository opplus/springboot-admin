package com.vigekoo.modules.sys.controller;

import com.vigekoo.common.exception.AppException;
import com.vigekoo.common.utils.Query;
import com.vigekoo.common.utils.Result;
import com.vigekoo.common.annotation.SysLog;
import com.vigekoo.common.Constant;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.modules.sys.entity.SysConfig;
import com.vigekoo.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(系统参数信息)
 * @date 2017-6-23 15:07
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有参数列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public Result list(@RequestParam Map<String, Object> params){
		params.put("status", Constant.ConfigStatus.SHOW.getValue());
		//查询列表数据
		Query query = new Query(params);
		List<SysConfig> configList = sysConfigService.queryList(query);
		int total = sysConfigService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 参数信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public Result info(@PathVariable("id") Long id){
		SysConfig config = sysConfigService.queryObject(id);
		return Result.ok().put("profiles", config);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public Result save(@RequestBody SysConfig config){
		verifyForm(config);
		sysConfigService.save(config);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public Result update(@RequestBody SysConfig config){
		verifyForm(config);
		sysConfigService.update(config);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public Result delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		return Result.ok();
	}

	private void verifyForm(SysConfig config){
		if(StringUtils.isBlank(config.getKey())){
			throw new AppException("参数名不能为空");
		}
		if(StringUtils.isBlank(config.getValue())){
			throw new AppException("参数值不能为空");
		}

		SysConfig temp=sysConfigService.queryObjectByKey(config.getKey());
		if(config.getId()!=null){
			if(temp!=null && temp.getId().compareTo(config.getId()) != 0){
				throw new AppException("参数名已经存在");
			}
		}else{
			if(temp!=null){
				throw new AppException("参数名已经存在");
			}
		}
	}

}
