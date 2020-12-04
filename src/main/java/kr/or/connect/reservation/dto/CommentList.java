package kr.or.connect.reservation.dto;

import java.util.List;

public class CommentList {
	private String comment;
	private int commentId;
	private List<CommentImages> commentImages;
	private String createDate;
	private String modifyDate;
	private int productId;
	private int reservationInfoId;
	private double score;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public List<CommentImages> getCommentImages() {
		return commentImages;
	}
	public void setCommentImages(List<CommentImages> commentImages) {
		this.commentImages = commentImages;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "GetComment [comment=" + comment + ", commentId=" + commentId + ", commentImages=" + commentImages
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", productId=" + productId
				+ ", reservationInfoId=" + reservationInfoId + ", score=" + score + "]";
	}
	
	
}
