package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.Products;
import kr.or.connect.reservation.dto.PromotionImgs;
import kr.or.connect.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	ReservationDao reservationDao;
	
	
	@Override
	@Transactional
	public List<Products> getProducts(Integer start) {
		List<Products> list = reservationDao.getAll(start, ReservationService.LIMIT);
		return list;
	}

	@Override
	@Transactional
	public int getTotalCount() {
		return reservationDao.getTotalCount();
	}
	
	@Override
	@Transactional
	public List<PromotionImgs> getPromotionImgs(){
		List<PromotionImgs> promotions = reservationDao.getPromotionImgs();
		return promotions;
	}
}
