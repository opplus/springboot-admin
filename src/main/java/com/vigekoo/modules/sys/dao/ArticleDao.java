package com.vigekoo.modules.sys.dao;

import com.vigekoo.modules.sys.entity.Article;
import com.vigekoo.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleDao extends BaseDao<Article> {
	
}
