package kr.or.connect.reservation.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.or.connect.reservation.dao.CancelAPIDao;
import kr.or.connect.reservation.dto.CancelReservation;
import kr.or.connect.reservation.dto.Prices;
import kr.or.connect.reservation.service.CancelAPIService;

@Service
public class CancelAPIServiceImpl implements CancelAPIService {
	
	@Autowired
	CancelAPIDao cancelAPIDao;

	@Override
	public CancelReservation getCancelReservation(int reservationInfoId) {
		CancelReservation cancelReservation = cancelAPIDao.getCancelReservation(reservationInfoId);
		List<Prices> prices = cancelAPIDao.getPrices(reservationInfoId);
		cancelReservation.setPrices(prices);
		return cancelReservation;
	}
}
