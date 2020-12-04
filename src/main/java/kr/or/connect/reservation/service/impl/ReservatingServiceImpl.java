package kr.or.connect.reservation.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.reservation.dao.AddReservationPriceDao;
import kr.or.connect.reservation.dao.AddReservationInfoDao;
import kr.or.connect.reservation.dto.FormData;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.service.ReservatingService;

@Service
public class ReservatingServiceImpl implements ReservatingService {
	@Autowired
	AddReservationInfoDao addReservationInfoDao;

	@Autowired
	AddReservationPriceDao addReservationPriceDao;
	
	@Override
	@Transactional
	public int addReservationInfo(FormData formData) {
		//DTO에 입력 reservationInfo
		ReservationInfo reservationInfo = createReservationInfo(formData);
		
		//reservationInfo DB에 저장하고 자동생성된 ID 가져오기
		int reservationInfoId = addReservationInfoDao.insertReservationInfo(reservationInfo);
		
		//티켓값 저장을 위해서 productId가져오기
		int productId = reservationInfo.getProductId();
		
		//티켓 타입과 수량 맵으로 만들기
		Map<String, Integer> ticketTypeAndCountMap = getTicketTypeAndCountMap(formData);
		
		//ticketTypeAndCountMap에서 값 추출해서 ReservationInfoPrice 저장하기
		int resultReservationInfoPriceId = 0;
		for(String ticketType : ticketTypeAndCountMap.keySet()){ 
			int productPriceId = getProductPriceId(productId, ticketType);
			String countStr = String.valueOf(ticketTypeAndCountMap.get(ticketType));
			Integer count = Integer.valueOf(countStr);
			int reservationInfoPriceId = addReservationInfoPrice(reservationInfoId, productPriceId, count);
			resultReservationInfoPriceId += reservationInfoPriceId;
		}
		if(reservationInfoId>0 && resultReservationInfoPriceId>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	
	
	//ReservationInfoPrice 저장하기
	private int addReservationInfoPrice(int reservationInfoId, int productPriceId, int count) {
		ReservationInfoPrice reservationInfoPrice = new ReservationInfoPrice();
		reservationInfoPrice.setProductPriceId(productPriceId);
		reservationInfoPrice.setReservationInfoId(reservationInfoId);
		reservationInfoPrice.setCount(count);
		int reservationPriceId = addReservationPriceDao.insertReservationInfoPrice(reservationInfoPrice);
		return reservationPriceId;
	}
	
	//ProductPriceId 가져오기
	private int getProductPriceId(int productId, String ticketType) {
		int productPriceId = addReservationPriceDao.getProductPriceId(productId, ticketType);
		return productPriceId;
	}
	
	//티켓 타입과 수량 맵으로 만들기
	private Map<String, Integer> getTicketTypeAndCountMap(FormData formData){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Integer> ticketTypeAndCountMap = new HashMap<String, Integer>();
		
		try {
			ticketTypeAndCountMap = mapper.readValue(formData.getTicketTypeCount(), Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ticketTypeAndCountMap;
	}
	
	//DTO에 입력 reservationInfo
	private ReservationInfo createReservationInfo(FormData formData) {
		ReservationInfo reservationInfo = new ReservationInfo();
		reservationInfo.setCancelFlag(0);
		reservationInfo.setCreateDate(createCreateDate());
		reservationInfo.setDisplayInfoId(formData.getDisplayInfoId());
		reservationInfo.setReservationEmail(formData.getReservationEmail());
		reservationInfo.setReservationName(formData.getReservationName());
		reservationInfo.setReservationTel(formData.getReservationTelephone());
		reservationInfo.setReservationDate(createReservationDate());
		reservationInfo.setProductId(addReservationInfoDao.getProductId(formData.getDisplayInfoId()));
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
	private String createReservationDate() {
		Calendar calendar = Calendar.getInstance();
		Random random = new Random();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		
		calendar.add(Calendar.DATE,random.nextInt(5));
		String reservationDate = dateFormat.format(calendar.getTime());
		
		return reservationDate; 
	}
	
	
}
