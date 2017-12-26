package com.vigekoo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.vigekoo.modules.sys.dao.ArticleDao;
import com.vigekoo.modules.sys.entity.Article;
import com.vigekoo.modules.sys.service.ArticleService;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public Article queryObject(Integer id){
		return articleDao.queryObject(id);
	}
	
	@Override
	public List<Article> queryList(Map<String, Object> map){
		return articleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return articleDao.queryTotal(map);
	}
	
	@Override
	public void save(Article article){
		articleDao.save(article);
	}
	
	@Override
	public void update(Article article){
		articleDao.update(article);
	}
	
	@Override
	public void delete(Integer id){
		articleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		articleDao.deleteBatch(ids);
	}
	
}
