package com.vigekoo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.vigekoo.modules.sys.dao.CarouselDao;
import com.vigekoo.modules.sys.entity.Carousel;
import com.vigekoo.modules.sys.service.CarouselService;

@Service("carouselService")
public class CarouselServiceImpl implements CarouselService {

	@Autowired
	private CarouselDao carouselDao;
	
	@Override
	public Carousel queryObject(Integer id){
		return carouselDao.queryObject(id);
	}
	
	@Override
	public List<Carousel> queryList(Map<String, Object> map){
		return carouselDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return carouselDao.queryTotal(map);
	}
	
	@Override
	public void save(Carousel carousel){
		carouselDao.save(carousel);
	}
	
	@Override
	public void update(Carousel carousel){
		carouselDao.update(carousel);
	}
	
	@Override
	public void delete(Integer id){
		carouselDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		carouselDao.deleteBatch(ids);
	}
	
}
