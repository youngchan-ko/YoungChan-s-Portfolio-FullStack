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

import kr.or.connect.reservation.dto.CommentImages;
import kr.or.connect.reservation.dto.CommentList;


@Repository
public class GetCommentDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private RowMapper<CommentImages> commentImages = BeanPropertyRowMapper.newInstance(CommentImages.class);
	    private RowMapper<CommentList> getComment = BeanPropertyRowMapper.newInstance(CommentList.class);
	    
	    public GetCommentDao(DataSource dataSource) {
	    	this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
	    public List<CommentList> getComment(int reservationUserCommentId) {
	    	Map<String, Integer> params = new HashMap<>();
	    	params.put("reservationUserCommentId", reservationUserCommentId);
	    	return jdbc.query(GET_COMMENT, params, getComment);
	    }

	    public List<CommentImages> getReviewImages(int reservationUserCommentId) {
	    	Map<String, Integer> params = new HashMap<>();
	    	params.put("reservationUserCommentId", reservationUserCommentId);
	    	return jdbc.query(GET_REVIEW_IMAGES, params, commentImages);
	    }
	    
	    
}