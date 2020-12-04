package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Comments;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.ProductImages;
import kr.or.connect.reservation.dto.ProductPrice;


public interface DetailService {
	public static final Integer LIMIT = 4;
	public static final Integer START = 0;
	
	//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
	public List<DisplayInfo> getDisplayInfo(Integer displayInfoId);
	
	//상세페이지 이미지파일 경로
	public List<ProductImages> getProductImages(Integer displayInfoId);
	
	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜 4개만
	public List<Comments> getCommentsLimit(Integer displayInfoId);

	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜 모두다
	public List<Comments> getCommentsAll(Integer displayInfoId);
	
	//좌석 타입, 가격, 할인정보 
	public List<ProductPrice> getProductPrice(Integer displayInfoId);

	//코멘트 갯수
	public int getCommentCount(Integer displayInfoId);

	//맵 이미지
	public List<DisplayInfoImage> getDisplayInfoImage(Integer displayInfoId);

	//평균점수
	public double getAverageScore(Integer displayInfoId);
}