package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dto.Categories;
import kr.or.connect.reservation.dto.Products;
import kr.or.connect.reservation.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao CategoryDao;

	@Override
	public List<Products> getCategoryProducts(Integer start, Integer categoryId) {
		List<Products> list = CategoryDao.getCategoryProducts(start, CategoryService.LIMIT, categoryId);
		return list;
	}

	@Override
	public int getCategoryTotalCount(Integer categoryId) {
		return CategoryDao.getCategoryTotalCount(categoryId);
	}
	
	@Override
	public List<Categories> getCategoryTab(){
		return CategoryDao.getCategoryTab();
	}
}
