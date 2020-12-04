package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Products;
import kr.or.connect.reservation.dto.PromotionImgs;


@Repository
public class ReservationDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private RowMapper<Products> products = BeanPropertyRowMapper.newInstance(Products.class);
	    private RowMapper<PromotionImgs> promotions = BeanPropertyRowMapper.newInstance(PromotionImgs.class);
	    
	    public ReservationDao(DataSource dataSource) {
	    	this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
	    public List<Products> getAll(Integer start, Integer limit) {
	    	Map<String, Integer> params = new HashMap<>();
	    	params.put("start", start);
	    	params.put("limit", limit);
	    	return jdbc.query(GET_PRODUCTS, params, products);
	    }

	    public int getTotalCount() {
	    	return jdbc.queryForObject(COUNT_TOTAL, Collections.emptyMap(), Integer.class);
	    }
	    
	    public List<PromotionImgs> getPromotionImgs() {
	    	return jdbc.query(SELECT_PROMOTION_IMG, promotions);
	    }
}