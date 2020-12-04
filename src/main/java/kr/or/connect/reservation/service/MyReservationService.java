package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.CancelReservation;
import kr.or.connect.reservation.dto.MyReservationPrice;
import kr.or.connect.reservation.dto.ReservationsList;
import kr.or.connect.reservation.dto.ReservationsWithTypePrice;

public interface MyReservationService {
	public List<ReservationsList> getReservations(String reservationEmail);
	public List<ReservationsWithTypePrice> getReservationsWithTypePrice(String reservationEmail);
	public int getReservationTotalCount(String reservationEmail);
	public List<MyReservationPrice> getMyReservationPrice(int reservationInfoId);
	public CancelReservation updateReservationInfo(int reservationInfoId);
	
}