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

import kr.or.connect.reservation.dto.CommentWritePage;

@Repository
public class WriteCommentDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private RowMapper<CommentWritePage> getForWriteComment = BeanPropertyRowMapper.newInstance(CommentWritePage.class);
	    
	    public WriteCommentDao(DataSource dataSource) {
	    	this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
	    public List<CommentWritePage> getInfoForWriteCommentPage(int reservationInfoId) {
	    	Map<String, Integer> params = new HashMap<>();
	    	params.put("reservationInfoId", reservationInfoId);
	    	return jdbc.query(GET_FOR_WRITE_COMMENT, params, getForWriteComment);
	    }
	    
	    
}