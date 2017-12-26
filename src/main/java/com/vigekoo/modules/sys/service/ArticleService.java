package com.vigekoo.modules.sys.service;

import com.vigekoo.modules.sys.entity.Article;
import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(文章)
 * @date 2017-11-30 15:35:54
 */
public interface ArticleService {
	
	Article queryObject(Integer id);
	
	List<Article> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Article article);
	
	void update(Article article);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

}
