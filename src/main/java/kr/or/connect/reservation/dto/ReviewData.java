package kr.or.connect.reservation.dto;

import org.springframework.web.multipart.MultipartFile;

public class ReviewData {
	private int reservationInfoId;
	private int productId;
	private int score;
	private String comment;
	private MultipartFile reviewImage;
	public int getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public MultipartFile getReviewImage() {
		return reviewImage;
	}
	public void setReviewImage(MultipartFile reviewImage) {
		this.reviewImage = reviewImage;
	}
	@Override
	public String toString() {
		return "WriteReview [reservationInfoId=" + reservationInfoId + ", productId=" + productId + ", score=" + score
				+ ", comment=" + comment + ", reviewImage=" + reviewImage + "]";
	}
	
}
