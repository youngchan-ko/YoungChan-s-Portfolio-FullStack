package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Categories;
import kr.or.connect.reservation.dto.Products;

public interface CategoryService {
	public static final Integer LIMIT = 4;
	public List<Products> getCategoryProducts(Integer start, Integer categoryId);
	public int getCategoryTotalCount(Integer categoryId);
	public List<Categories> getCategoryTab();
}