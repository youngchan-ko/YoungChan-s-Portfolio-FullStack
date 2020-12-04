package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationUserComment;

@Repository
public class AddReservationUserCommentDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
	
	public AddReservationUserCommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment")
                .usingGeneratedKeyColumns("id");
	}

	public int insertReservationUserComment(ReservationUserComment reservationUserComment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserComment);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	

	
	
}





