package kr.or.connect.reservation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.DetailDao;
import kr.or.connect.reservation.dto.CommentImages;
import kr.or.connect.reservation.dto.Comments;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.ProductImages;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.service.DetailService;

@Service
public class DetailServiceImpl implements DetailService {
	@Autowired
	DetailDao detailDao;

	//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
	@Override
	public List<DisplayInfo> getDisplayInfo(Integer displayInfoId) {
		List<DisplayInfo> displayInfo = detailDao.getDisplayInfo(displayInfoId);
		return displayInfo;
	}

	//상세페이지 이미지파일 경로
	@Override
	public List<ProductImages> getProductImages(Integer displayInfoId){
		List<ProductImages> productImages = detailDao.getProductImages(displayInfoId);
		return productImages;
	}

	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜, 코멘트 이미지 정보 4개만
	@Override
	public List<Comments> getCommentsLimit(Integer displayInfoId){
		List<Comments> comments = detailDao.getCommentsLimit(displayInfoId, DetailService.START, DetailService.LIMIT);
		List<Comments> commentLists = mergeCommentAndImage(comments);
		return commentLists;
	}
	
	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜, 코멘트 이미지 정보 모두다
	@Override
	public List<Comments> getCommentsAll(Integer displayInfoId){
		List<Comments> comments = detailDao.getCommentsAll(displayInfoId);
		List<Comments> commentLists = mergeCommentAndImage(comments);
		return commentLists;
	}

	//좌석 타입, 가격, 할인정보 
	@Override
	public List<ProductPrice> getProductPrice(Integer displayInfoId){
		List<ProductPrice> productImages = detailDao.getProductPrice(displayInfoId);
		return productImages;
	}

	//코멘트 갯수
	@Override
	public int getCommentCount(Integer displayInfoId) {
		return detailDao.getCommentCount(displayInfoId);
	}

	//맵 이미지 
	@Override
	public List<DisplayInfoImage> getDisplayInfoImage(Integer displayInfoId){
		List<DisplayInfoImage> mapImage = detailDao.getDisplayInfoImage(displayInfoId);
		return mapImage;
	}
	
	//코멘트안에 코멘트 이미지 넣기
	private List<Comments> mergeCommentAndImage(List<Comments> comments){
		List<Comments> commentLists = new ArrayList<Comments>();
		
		for(int i=0; i<comments.size(); i++) {
			//코멘트 이미지 경로
			List<CommentImages> commentImages = detailDao.getCommentImages(comments.get(i).getCommentId());
			
			Comments comment = new Comments();
			
			comment.setCommentId(comments.get(i).getCommentId());
			comment.setProductId(comments.get(i).getProductId());
			comment.setReservationInfoId(comments.get(i).getReservationInfoId());
			comment.setScore(comments.get(i).getScore());
			comment.setComment(comments.get(i).getComment());
			comment.setReservationName(comments.get(i).getReservationName());
			comment.setReservationTelephone(comments.get(i).getReservationTelephone());
			comment.setReservationEmail(comments.get(i).getReservationEmail());
			comment.setReservationDate(comments.get(i).getReservationDate());
			comment.setCreateDate(comments.get(i).getCreateDate());
			comment.setModifyDate(comments.get(i).getModifyDate());
			comment.setCommentImages(commentImages);
			
			commentLists.add(comment);
		}
		return commentLists;
	}
	
	//평균점 구하기
	@Override
	public double getAverageScore(Integer displayInfoId) {
		List<Comments> comments = detailDao.getCommentsAll(displayInfoId);
		double totalScore = 0.0; 
		for(int i=0; i<comments.size(); i++) {
			totalScore += comments.get(i).getScore();
		}
		int totalCount = detailDao.getCommentCount(displayInfoId);
		double avg = totalScore / totalCount;
		return avg;
	}
}
//@Transactional
