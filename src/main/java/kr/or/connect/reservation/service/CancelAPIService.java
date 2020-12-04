package kr.or.connect.reservation.service;

import kr.or.connect.reservation.dto.CancelReservation;

public interface CancelAPIService {
	public CancelReservation getCancelReservation(int reservationInfoId);
}