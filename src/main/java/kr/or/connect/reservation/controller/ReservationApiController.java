package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.reservation.dto.CancelReservation;
import kr.or.connect.reservation.dto.Comments;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.Categories;
import kr.or.connect.reservation.dto.CommentList;
import kr.or.connect.reservation.dto.Products;
import kr.or.connect.reservation.dto.PromotionImgs;
import kr.or.connect.reservation.dto.ReservationsList;
import kr.or.connect.reservation.dto.ProductImages;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.Reservating;
import kr.or.connect.reservation.dto.ReviewData;
import kr.or.connect.reservation.service.CancelAPIService;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.MyReservationService;
import kr.or.connect.reservation.service.ReservatingAPIService;
import kr.or.connect.reservation.service.ReservationService;
import kr.or.connect.reservation.service.WriteReviewService;

@RestController
@RequestMapping(path="/api")
public class ReservationApiController {
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	CategoryService categoryService;

	@Autowired
	DetailService detailService;

	@Autowired
	MyReservationService myReservationService;

	@Autowired
	ReservatingAPIService reservatingAPIService;
	
	@Autowired
	CancelAPIService cancelAPIService;
	
	@Autowired
	WriteReviewService writeReviewService;
	
	@GetMapping(path="/products")
	public Map<String, Object> list(@RequestParam(defaultValue="0") int start,
									@RequestParam(defaultValue="0") int categoryId) {
		List<Products> list;
		int totalCount;
		
		// start로 시작하는 상품 목록 구하기
		if(categoryId == 0) {
			List<Products> listAll = reservationService.getProducts(start);			
			int totalCountAll = reservationService.getTotalCount();
			list = listAll;
			totalCount = totalCountAll;
		} else {
			List<Products> listCategory = categoryService.getCategoryProducts(start, categoryId);
			int totalCountCategory = categoryService.getCategoryTotalCount(categoryId);
			list = listCategory;
			totalCount = totalCountCategory;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("items", list);
		map.put("totalCount", totalCount);
		return map;
	}
	
	@GetMapping(path="/categories")
	public Map<String,Object> categories(){
		List<Categories> categories = categoryService.getCategoryTab();
		Map<String, Object> map = new HashMap<>();
		map.put("items", categories);
		return map;
	}
	
	@GetMapping(path="/promotions")
	public Map<String,Object> promotions(){
		List<PromotionImgs> promotions = reservationService.getPromotionImgs();
		Map<String, Object> map = new HashMap<>();
		map.put("items", promotions);
		return map;
	}
	
	@GetMapping(path="/products/{displayInfoId}")
	public Map<String, Object> detail (@PathVariable("displayInfoId") int displayInfoId) {
		List<DisplayInfo> displayInfo = detailService.getDisplayInfo(displayInfoId);
		List<ProductImages> productImages = detailService.getProductImages(displayInfoId);
		List<DisplayInfoImage> displayInfoImage = detailService.getDisplayInfoImage(displayInfoId);
		List<Comments> comments = detailService.getCommentsAll(displayInfoId);
		double averageScore = detailService.getAverageScore(displayInfoId);
		List<ProductPrice> productPrice = detailService.getProductPrice(displayInfoId);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("displayInfo", displayInfo);
		map.put("productImages", productImages);
		map.put("displayInfoImage", displayInfoImage);
		map.put("comments", comments);
		map.put("averageScore", averageScore);
		map.put("productPrice", productPrice);
		return map;
	}
	
	//예매내역 가져오기
	@RequestMapping(path = "/reservations", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String myReservationsList (@RequestParam String reservationEmail) 
    		throws JsonProcessingException {
		List<ReservationsList> getReservations = myReservationService.getReservations(reservationEmail);		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("getReservations", getReservations);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		return json;
	}
	
	//예매하기
	@RequestMapping(path = "/reservations", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String insertReservation (@RequestBody Reservating reservating) 
			throws JsonProcessingException {
		Reservating reservations = reservatingAPIService.addReservationAPI(reservating);		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("reservations", reservations);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		return json;
	}
	
	//취소하기
	@PutMapping(path="/reservations/{reservationId}")
	public String calcelTicket (@PathVariable("reservationId") int reservationInfoId) 
			throws JsonProcessingException{
		System.out.println("Controller");
		System.out.println(reservationInfoId);
		myReservationService.updateReservationInfo(reservationInfoId);
		CancelReservation cancelReservation = cancelAPIService.getCancelReservation(reservationInfoId);
		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("cancelReservation", cancelReservation);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		return json;
	}
	
	@RequestMapping(value = "/reservations/{reservationInfoId}/comments",
					method = RequestMethod.POST, 
					produces = "application/json; charset=utf8")
	public String writeComment (ReviewData writeReview) throws JsonProcessingException {
		CommentList getComment = writeReviewService.writeReview(writeReview);
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("getComment", getComment);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		
		return json;
	}
	
	
}