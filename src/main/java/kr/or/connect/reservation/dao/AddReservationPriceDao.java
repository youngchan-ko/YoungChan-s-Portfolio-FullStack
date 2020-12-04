package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Prices;
import kr.or.connect.reservation.dto.ReservationInfoPrice;

@Repository
public class AddReservationPriceDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
	
	public AddReservationPriceDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_info_price")
                .usingGeneratedKeyColumns("id");
	}

	public int insertReservationInfoPrice(ReservationInfoPrice ReservationInfoPrice) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(ReservationInfoPrice);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int insertAPIReservationInfoPrice(Prices prices) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(prices);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int getProductPriceId(Integer productId, String ticketType) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("ticketType", ticketType);
    	return jdbc.queryForObject(GET_PRODUCT_PRICE_ID, params, Integer.class);
	}
	
}





