package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationUserCommentImage;

@Repository
public class AddReservationUserCommentImageDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
	
	public AddReservationUserCommentImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment_image")
                .usingGeneratedKeyColumns("id");
	}

	public int insertReservationUserComment(ReservationUserCommentImage reservationUserCommentImage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserCommentImage);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	

	
	
}





