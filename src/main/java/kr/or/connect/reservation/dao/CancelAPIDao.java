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

import kr.or.connect.reservation.dto.CancelReservation;
import kr.or.connect.reservation.dto.Prices;

@Repository
public class CancelAPIDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Prices> prices = BeanPropertyRowMapper.newInstance(Prices.class);
	private RowMapper<CancelReservation> cancelReservation = BeanPropertyRowMapper.newInstance(CancelReservation.class);
	
	public CancelAPIDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	//예매내역 관련 예약자 정보
	public CancelReservation getCancelReservation(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);
		return jdbc.queryForObject(GET_CANCEL_RESERVATION, params, cancelReservation);
	}
	
	//reservationInfoPrice 가져오기
	public List<Prices> getPrices(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);
		return jdbc.query(GET_RESERVATION_INFO_PRICE, params, prices);
	}
	
	
	
}