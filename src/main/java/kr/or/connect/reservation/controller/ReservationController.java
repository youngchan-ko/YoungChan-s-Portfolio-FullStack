package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.reservation.dto.CancelReservation;
import kr.or.connect.reservation.dto.Comments;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.FormData;
import kr.or.connect.reservation.dto.Categories;
import kr.or.connect.reservation.dto.CommentList;
import kr.or.connect.reservation.dto.CommentWritePage;
import kr.or.connect.reservation.dto.Products;
import kr.or.connect.reservation.dto.PromotionImgs;
import kr.or.connect.reservation.dto.ProductImages;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ReservationsWithTypePrice;
import kr.or.connect.reservation.dto.ReviewData;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.MyReservationService;
import kr.or.connect.reservation.service.ReservatingService;
import kr.or.connect.reservation.service.ReservationService;
import kr.or.connect.reservation.service.WriteCommentService;
import kr.or.connect.reservation.service.WriteReviewService;

@Controller
public class ReservationController {
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	DetailService detailService;
	
	@Autowired
	ReservatingService reservatingService;

	@Autowired
	MyReservationService myreservationService;
	
	@Autowired
	WriteCommentService writeCommentService;

	@Autowired
	WriteReviewService writeReviewService;
	
	
	//접속
	@RequestMapping(path = "/", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String mainpage(ModelMap model) {
		//promotion 이미지 전체 가져오기
		List<PromotionImgs> promotions = reservationService.getPromotionImgs();
		//TabMenu 가져오기
		List<Categories> CategoryTabs = categoryService.getCategoryTab();
		
		model.addAttribute("promotionImgs", promotions);
		model.addAttribute("CategoryTabs", CategoryTabs);
		return "mainpage";
	}
	 
	//로그인
	@RequestMapping(path = "/login", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String loginpage() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(path = "/getList.ajax", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String list (@RequestParam(defaultValue="0") int start,
    					@RequestParam(defaultValue="0") int categoryId) 
    							throws JsonProcessingException {
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
		
		// 전체 페이지수 구하기
		int pageCount = totalCount / ReservationService.LIMIT;
		if(totalCount % ReservationService.LIMIT > 0) {
			pageCount++;
		}
		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("list", list);
		lists.put("totalCount", totalCount);
		lists.put("pageCount", pageCount);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		return json;
	}
	
	
	@RequestMapping(path = "/detail", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String detail() {
		return "detail";
	}
	
	
	@ResponseBody
	@RequestMapping(path = "/detail.ajax", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String detailList (@RequestParam(required=false) int displayInfoId) 
    		throws JsonProcessingException {
		//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
		List<DisplayInfo> displayInfo = detailService.getDisplayInfo(displayInfoId);
		
		//상세페이지 이미지파일 경로
		List<ProductImages> productImages = detailService.getProductImages(displayInfoId);
		
		//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜, 코멘트 이미지정보, 4개만
		List<Comments> commentsLimit = detailService.getCommentsLimit(displayInfoId);

		//평균점수
		double averageScore = detailService.getAverageScore(displayInfoId);
		
		//좌석 타입, 가격, 할인정보 
		List<ProductPrice> productPrice = detailService.getProductPrice(displayInfoId);
		
		//코멘트 총 갯수
		int commentCount = detailService.getCommentCount(displayInfoId);
		
		//맵 이미지
		List<DisplayInfoImage> mapImage = detailService.getDisplayInfoImage(displayInfoId);
		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("displayInfo", displayInfo);
		lists.put("productImages", productImages);
		lists.put("commentsLimit", commentsLimit);
		lists.put("averageScore", averageScore);
		lists.put("productPrice", productPrice);
		lists.put("commentCount", commentCount);
		lists.put("mapImage", mapImage);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		
		return json;
	}
	
	
	@RequestMapping(path = "/review", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String reviewPage() throws JsonProcessingException {
		return "review";
	}
	
	
	@ResponseBody
	@RequestMapping(path = "/review.ajax", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String reviewList (@RequestParam(required=false) int displayInfoId) 
    		throws JsonProcessingException {
		//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
		List<DisplayInfo> displayInfo = detailService.getDisplayInfo(displayInfoId);
		//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜, 코멘트 이미지정보, 모두다
		List<Comments> comments = detailService.getCommentsAll(displayInfoId);
		//코멘트 총 갯수
		int commentCount = detailService.getCommentCount(displayInfoId);
		//평균점수
		double averageScore = detailService.getAverageScore(displayInfoId);
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("displayInfo", displayInfo);
		lists.put("comments", comments);
		lists.put("commentCount", commentCount);
		lists.put("averageScore", averageScore);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		
		return json;
	}
	
	@RequestMapping(path = "/reservations", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String reserve() {
		return "reserve";
	}
	
	@ResponseBody
	@RequestMapping(path = "/reservations.ajax", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String reservationsList (@RequestParam(required=false) int displayInfoId) 
    		throws JsonProcessingException {
		//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
		List<DisplayInfo> displayInfo = detailService.getDisplayInfo(displayInfoId);
		
		//상세페이지 이미지파일 경로
		List<ProductImages> productImages = detailService.getProductImages(displayInfoId);
		
		//좌석 타입, 가격, 할인정보 
		List<ProductPrice> productPrice = detailService.getProductPrice(displayInfoId);
		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("displayInfo", displayInfo);
		lists.put("productImages", productImages);
		lists.put("productPrice", productPrice);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping(path = "/reservating.ajax", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public int reservating(@RequestBody FormData formData) {
		int result = reservatingService.addReservationInfo(formData);
		return result;
	}
	
	@RequestMapping(path = "/myreservation", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String myreservation(@RequestParam(required=false) String reservationEmail, 
								ModelMap model) {
		//TabMenu 가져오기
		int myReservationTotalCount = myreservationService.getReservationTotalCount(reservationEmail);
		
		model.addAttribute("myReservationTotalCount", myReservationTotalCount);
		model.addAttribute("reservationEmail", reservationEmail);
		
		return "myreservation";
	}
	
	@ResponseBody
	@RequestMapping(path = "/myreservations.ajax", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String myReservationsList (HttpSession session) 
    		throws JsonProcessingException {
		String reservationEmail = (String)session.getAttribute("reservationEmail");
		List<ReservationsWithTypePrice> getReservations = myreservationService.getReservationsWithTypePrice(reservationEmail);		

		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("getReservations", getReservations);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(path = "/cancelupdate.ajax", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String updateReservationInfo (@RequestParam(required=false) int reservationInfoId) 
    		throws JsonProcessingException {
		
		CancelReservation reservationInfoCancelUpdate = myreservationService.updateReservationInfo(reservationInfoId);		
		Map<String, Object> lists = new HashMap<String, Object>();
		lists.put("reservationInfoCancelUpdate", reservationInfoCancelUpdate);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(lists);
		
		return json;
	}
	
	@RequestMapping(path = "/reservations/{reservationInfoId}/comments", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String reviewWritePage(@PathVariable("reservationInfoId") int reservationInfoId,
    								ModelMap model) throws JsonProcessingException {
		List<CommentWritePage> data = writeCommentService.getInfoForWriteCommentPage(reservationInfoId);
		
		model.addAttribute("data", data);
		
		return "reviewwrite";
	}
	
	
	@ResponseBody
	@RequestMapping(path = "/writereview.ajax", method = RequestMethod.POST)
    public CommentList writeReview (ReviewData writeReview) 
    		throws JsonProcessingException {
		CommentList getComment = writeReviewService.writeReview(writeReview);
		return getComment;
	}
	
}









 