package com.vigekoo.modules.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vigekoo.modules.sys.entity.Article;
import com.vigekoo.modules.sys.service.ArticleService;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.common.utils.Query;
import com.vigekoo.common.utils.Result;

/**
 * @author oplus
 * @Description: TODO(文章)
 * @date 2017-11-30 15:35:54
 */
@RestController
@RequestMapping("/sys/article")
public class ArticleController extends AbstractController{

	@Autowired
	private ArticleService articleService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:article:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Article> articleList = articleService.queryList(query);
		int total = articleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(articleList, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:article:info")
	public Result info(@PathVariable("id") Integer id){
		Article article = articleService.queryObject(id);
		
		return Result.ok().put("article", article);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:article:save")
	public Result save(@RequestBody Article article){
		article.setCreateTime(new Date());
		article.setUpdateTime(new Date());
		articleService.save(article);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:article:update")
	public Result update(@RequestBody Article article){
		article.setUpdateTime(new Date());
		articleService.update(article);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:article:delete")
	public Result delete(@RequestBody Integer[] ids){
		articleService.deleteBatch(ids);
		
		return Result.ok();
	}
	
}
