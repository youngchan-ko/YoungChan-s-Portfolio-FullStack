package kr.or.connect.reservation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CancelAPIDao;
import kr.or.connect.reservation.dao.DetailDao;
import kr.or.connect.reservation.dao.MyReservationDao;
import kr.or.connect.reservation.dto.CancelReservation;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.MyReservationPrice;
import kr.or.connect.reservation.dto.ReservationsList;
import kr.or.connect.reservation.dto.Reservations;
import kr.or.connect.reservation.dto.ReservationsWithTypePrice;
import kr.or.connect.reservation.service.MyReservationService;

@Service
public class MyReservationServiceImpl implements MyReservationService {
	@Autowired
	MyReservationDao myReservationDao;

	@Autowired
	DetailDao detailDao;

	@Autowired
	CancelAPIDao cancelAPIDao;
	
	@Override
	public List<ReservationsWithTypePrice> getReservationsWithTypePrice(String reservationEmail) {
		List<Reservations> reservations = myReservationDao.getReservations(reservationEmail);
		List<ReservationsWithTypePrice> ReservationsList = makeReservationsWithTypePrice(reservations);
		return ReservationsList;
	}
	
	//예매내역 관련 예약자 정보 - for APIController
	@Override
	public List<ReservationsList> getReservations(String reservationEmail) {
		List<Reservations> reservations = myReservationDao.getReservations(reservationEmail);
		List<ReservationsList> ReservationsList = makeReservations(reservations,reservationEmail);
		return ReservationsList;
	}

	//데이터 합쳐서 GetReservations만들기 - for APIController
	private List<ReservationsList> makeReservations(List<Reservations> reservations,String reservationEmail){
		List<Reservations> reservationList = new ArrayList<Reservations>();

		for (Reservations reservationElement : reservations) {
			List<DisplayInfo> displayInfo = detailDao.getDisplayInfo(reservationElement.getDisplayInfoId());

			Reservations reservation = new Reservations();

			reservation.setCancelYn(reservation.getCancelYn());
			reservation.setCreateDate(reservation.getCreateDate());
			reservation.setDisplayInfo(displayInfo);
			reservation.setDisplayInfoId(reservation.getDisplayInfoId());
			reservation.setModifyDate(reservation.getModifyDate());
			reservation.setProductId(reservation.getProductId());
			reservation.setReservationDate(reservation.getReservationDate());
			reservation.setReservationEmail(reservation.getReservationEmail());
			reservation.setReservationInfoId(reservation.getReservationInfoId());
			reservation.setReservationName(reservation.getReservationName());
			reservation.setReservationTelephone(reservation.getReservationTelephone());
			reservation.setTotalPrice(reservation.getTotalPrice());

			reservationList.add(reservation);
		}

		List<ReservationsList> getReservations = new ArrayList<ReservationsList>();
		int size = myReservationDao.getTotalMyReservation(reservationEmail);

		ReservationsList getReservation = new ReservationsList();
		getReservation.setReservations(reservationList);
		getReservation.setSize(size);
		
		getReservations.add(getReservation);
		
		return getReservations;
	}

	private List<ReservationsWithTypePrice> makeReservationsWithTypePrice(List<Reservations> reservations){
		List<ReservationsWithTypePrice> reservationsWithTypePriceList = new ArrayList<ReservationsWithTypePrice>();
		
		
		for(int i=0; i<reservations.size(); i++) {
			List<DisplayInfo> displayInfo = detailDao.getDisplayInfo(reservations.get(i).getDisplayInfoId());
			List<MyReservationPrice> ticketTypePrice = myReservationDao.getMyReservationPrice(reservations.get(i).getReservationInfoId());
			ReservationsWithTypePrice reservationsWithTypePrice = new ReservationsWithTypePrice();
			
			reservationsWithTypePrice.setCancelYn(reservations.get(i).getCancelYn());
			reservationsWithTypePrice.setCreateDate(reservations.get(i).getCreateDate());
			reservationsWithTypePrice.setDisplayInfo(displayInfo);
			reservationsWithTypePrice.setDisplayInfoId(reservations.get(i).getDisplayInfoId());
			reservationsWithTypePrice.setModifyDate(reservations.get(i).getModifyDate());
			reservationsWithTypePrice.setProductId(reservations.get(i).getProductId());
			reservationsWithTypePrice.setReservationDate(reservations.get(i).getReservationDate());
			reservationsWithTypePrice.setReservationEmail(reservations.get(i).getReservationEmail());
			reservationsWithTypePrice.setReservationInfoId(reservations.get(i).getReservationInfoId());
			reservationsWithTypePrice.setReservationName(reservations.get(i).getReservationName());
			reservationsWithTypePrice.setReservationTelephone(reservations.get(i).getReservationTelephone());
			reservationsWithTypePrice.setTotalPrice(reservations.get(i).getTotalPrice());
			reservationsWithTypePrice.setTicketTypePrice(ticketTypePrice);
			
			reservationsWithTypePriceList.add(reservationsWithTypePrice);
		}
		return reservationsWithTypePriceList;
		
	}
	
	@Override
	public int getReservationTotalCount(String reservationEmail) {
		int totalCount = myReservationDao.getTotalMyReservation(reservationEmail);
		return totalCount;
	}
	
	@Override
	public List<MyReservationPrice> getMyReservationPrice(int reservationInfoId){
		List<MyReservationPrice> getMyReservationPrice = myReservationDao.getMyReservationPrice(reservationInfoId);
		return getMyReservationPrice;
	}
	
	@Override
	public CancelReservation updateReservationInfo(int reservationInfoId){
		int updateReservationInfo = myReservationDao.updateReservationInfo(reservationInfoId);
		CancelReservation cancelReservation = cancelAPIDao.getCancelReservation(reservationInfoId);
		return cancelReservation;
	}
}
