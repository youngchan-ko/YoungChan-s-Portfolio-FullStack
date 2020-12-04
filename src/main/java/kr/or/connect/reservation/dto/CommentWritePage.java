package kr.or.connect.reservation.dto;

public class CommentWritePage {
	private int reservationInfoId;
	private int productId;
	private String reservationEmail;
	private String description;
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
	public String getReservationEmail() {
		return reservationEmail;
	}
	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "GetForWriteComment [reservationInfoId=" + reservationInfoId + ", productId=" + productId
				+ ", reservationEmail=" + reservationEmail + ", description=" + description + "]";
	}
	
	
	
}
