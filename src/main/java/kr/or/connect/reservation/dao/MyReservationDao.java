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

import kr.or.connect.reservation.dto.MyReservationPrice;
import kr.or.connect.reservation.dto.Reservations;

@Repository
public class MyReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Reservations> reservations = BeanPropertyRowMapper.newInstance(Reservations.class);
	private RowMapper<MyReservationPrice> getMyReservationPrice = BeanPropertyRowMapper.newInstance(MyReservationPrice.class);
	
	public MyReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	//예매내역 관련 예약자 정보
	public List<Reservations> getReservations(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);
		return jdbc.query(GET_RESERVATION_INFO, params, reservations);
	}
	
	//예매내역 총 갯수
	public int getTotalMyReservation(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);
		return jdbc.queryForObject(GET_RESERVATIONS_SIZE, params, Integer.class);
	}

	//예매내역 티켓별 타입과  타입별 가격
	public List<MyReservationPrice> getMyReservationPrice(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);
		return jdbc.query(GET_MY_RESERVATION_PRICE, params, getMyReservationPrice);
	}
	
	
	//티켓취소하기
	public int updateReservationInfo(int reservationInfoId) {
		Map<String, ?> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
		return jdbc.update(UPDATE_RESERVATION_INFO, params);
	}
	
}