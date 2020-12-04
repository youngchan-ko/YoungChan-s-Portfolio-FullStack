package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Products;
import kr.or.connect.reservation.dto.PromotionImgs;

public interface ReservationService {
	public static final Integer LIMIT = 4;
	public List<Products> getProducts(Integer start);
	public int getTotalCount();
	public List<PromotionImgs> getPromotionImgs();
}