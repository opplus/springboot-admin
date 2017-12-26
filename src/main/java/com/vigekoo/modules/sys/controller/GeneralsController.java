package com.vigekoo.modules.sys.controller;

import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vigekoo.modules.sys.entity.Generals;
import com.vigekoo.modules.sys.service.GeneralsService;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.common.utils.Query;
import com.vigekoo.common.utils.Result;

/**
 * @author oplus
 * @Description: TODO(兵器)
 * @date 2017-12-19 13:59:34
 */
@RestController
@RequestMapping("/sys/generals")
public class GeneralsController extends AbstractController{

	@Autowired
	private GeneralsService generalsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:generals:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Generals> generalsList = generalsService.queryList(query);
		int total = generalsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(generalsList, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:generals:info")
	public Result info(@PathVariable("id") Integer id){
		Generals generals = generalsService.queryObject(id);
		
		return Result.ok().put("generals", generals);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:generals:save")
	public Result save(@RequestBody Generals generals){
		generalsService.save(generals);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:generals:update")
	public Result update(@RequestBody Generals generals){
		generalsService.update(generals);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:generals:delete")
	public Result delete(@RequestBody Integer[] ids){
		generalsService.deleteBatch(ids);
		
		return Result.ok();
	}
	
}
