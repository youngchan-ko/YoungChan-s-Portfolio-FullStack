package kr.or.connect.reservation.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import kr.or.connect.reservation.dao.AddReservationPriceDao;
import kr.or.connect.reservation.dao.AddReservationInfoDao;
import kr.or.connect.reservation.dto.Prices;
import kr.or.connect.reservation.dto.Reservating;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.service.ReservatingAPIService;

@Service
public class ReservatingAPIServiceImpl implements ReservatingAPIService {
	@Autowired
	AddReservationInfoDao addReservationInfoDao;

	@Autowired
	AddReservationPriceDao addReservationPriceDao;
	
	@Override
	@Transactional
	public Reservating addReservationAPI(Reservating reservations) {
		//DTO에 입력 reservationInfo
		ReservationInfo reservationInfo = createReservationInfo(reservations);
		
		//reservationInfo DB에 저장하고 자동생성된 ID 가져오기
		int reservationInfoId = addReservationInfoDao.insertReservationInfo(reservationInfo);
		
		//티켓 타입과 수량 List로 만들어서 저장하고 반환
		List<Prices> ticketTypeAndCountList = getTicketTypeAndCountList(reservations);
		
		Reservating reservating = makeReservating(reservationInfo, ticketTypeAndCountList);
		return reservating;
	}
	
	//ReservationInfoPrice 저장하기
	//reservationInfoPriceId 값은 사용자로부터 받은값이 아니고 자동 생성된값으로 설정했습니다.
	private List<Prices> getTicketTypeAndCountList(Reservating reservations){
		List<Prices> pricesList = new ArrayList<Prices>();
		List<Prices> pricesListWithId = new ArrayList<Prices>();
		pricesList = reservations.getPrices();
		for(int i=0; i<pricesList.size(); i++) {
			Prices prices = new Prices();
			
			prices.setCount(pricesList.get(i).getCount());
			prices.setProductPriceId(pricesList.get(i).getProductPriceId());
			prices.setReservationInfoId(pricesList.get(i).getReservationInfoId());
			
			int reservationInfoPriceId = addReservationPriceDao.insertAPIReservationInfoPrice(prices);
			
			prices.setReservationInfoPriceId(reservationInfoPriceId);
			pricesListWithId.add(prices);
		}
		return pricesListWithId;
	}
	
	//DTO에 입력 reservationInfo
	private ReservationInfo createReservationInfo(Reservating reservations) {
		ReservationInfo reservationInfo = new ReservationInfo();
		reservationInfo.setCancelFlag(0);
		reservationInfo.setCreateDate(createCreateDate());
		reservationInfo.setDisplayInfoId(reservations.getDisplayInfoId());
		reservationInfo.setReservationEmail(reservations.getReservationEmail());
		reservationInfo.setReservationName(reservations.getReservationName());
		reservationInfo.setReservationTel(reservations.getReservationTelephone());
		reservationInfo.setReservationDate(reservations.getReservationYearMonthDay());
		reservationInfo.setProductId(reservations.getProductId());
		return reservationInfo;
	}
	
	//reservationInfo 등록날짜 만들기
	private String createCreateDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String now = dateFormat.format(time);
		return now;
	}
	
	//reservationInfo 예약날짜 만들기
	private Reservating makeReservating(ReservationInfo reservationInfo, List<Prices> ticketTypeAndCountList) {
		Reservating reservating = new Reservating();
		
		reservating.setDisplayInfoId(reservationInfo.getDisplayInfoId());
		reservating.setPrices(ticketTypeAndCountList);
		reservating.setProductId(reservationInfo.getProductId());
		reservating.setReservationEmail(reservationInfo.getReservationEmail());
		reservating.setReservationName(reservationInfo.getReservationName());
		reservating.setReservationTelephone(reservationInfo.getReservationTel());
		reservating.setReservationYearMonthDay(reservationInfo.getReservationDate());
		return reservating; 
	}
}
