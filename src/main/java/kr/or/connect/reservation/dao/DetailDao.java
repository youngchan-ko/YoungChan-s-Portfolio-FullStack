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
import kr.or.connect.reservation.dto.Comments;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.ProductImages;
import kr.or.connect.reservation.dto.ProductPrice;

@Repository
public class DetailDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<DisplayInfo> displayInfo = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<CommentImages> commentImages = BeanPropertyRowMapper.newInstance(CommentImages.class);
	private RowMapper<ProductImages> productImages = BeanPropertyRowMapper.newInstance(ProductImages.class);
	private RowMapper<ProductPrice> productPrice = BeanPropertyRowMapper.newInstance(ProductPrice.class);
	private RowMapper<Comments> comments = BeanPropertyRowMapper.newInstance(Comments.class);
	private RowMapper<DisplayInfoImage> mapImage = BeanPropertyRowMapper.newInstance(DisplayInfoImage.class);
	public DetailDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
	public List<DisplayInfo> getDisplayInfo(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.query(GET_DISPLAY_INFO, params, displayInfo);
	}

	//상세페이지 이미지파일 경로
	public List<ProductImages> getProductImages(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.query(GET_PRODUCT_IMAGES, params, productImages);
	}

	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜 4개만
	public List<Comments> getCommentsLimit(Integer displayInfoId, Integer start, Integer limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		params.put("start", start);
		params.put("limit", limit);
		return jdbc.query(GET_COMMENTS_LIMIT, params, comments);
	}

	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜 모두
	public List<Comments> getCommentsAll(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.query(GET_COMMENTS_ALL, params, comments);
	}

	//코멘트 이미지 경로
	public List<CommentImages> getCommentImages(Integer userCommentId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("userCommentId", userCommentId);
		return jdbc.query(GET_COMMENT_IMAGES, params, commentImages);
	}

	//좌석 타입, 가격, 할인정보 
	public List<ProductPrice> getProductPrice(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.query(GET_PRODUCT_PRICE, params, productPrice);
	}
	
	//코멘트 갯수
	public int getCommentCount(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
    	return jdbc.queryForObject(GET_COMMENT_COUNT, params, Integer.class);
    }

	//맵 이미지 
	public List<DisplayInfoImage> getDisplayInfoImage(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.query(GET_DISPLAYINFO_IMAGE, params, mapImage);
	}
}