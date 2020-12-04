package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Categories;
import kr.or.connect.reservation.dto.Products;

@Repository
public class CategoryDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Products> products = BeanPropertyRowMapper.newInstance(Products.class);
	private RowMapper<Categories> categorys = BeanPropertyRowMapper.newInstance(Categories.class);
	public CategoryDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Products> getCategoryProducts(Integer start, Integer limit, Integer categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
		params.put("categoryId", categoryId);
		return jdbc.query(GET_CATEGORY, params, products);
	}

	public int getCategoryTotalCount(Integer categoryId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		return jdbc.queryForObject(COUNT_CATEGORY_TOTAL, params, Integer.class);
	}
	
	public List<Categories> getCategoryTab() {
		return jdbc.query(GET_CATEGORY_TAB, categorys);
	}

}