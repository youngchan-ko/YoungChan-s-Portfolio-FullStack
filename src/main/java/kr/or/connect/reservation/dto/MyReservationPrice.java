package kr.or.connect.reservation.dto;

public class MyReservationPrice {
	private int reservationInfoId;
	private int count;
	private String ticketType;
	private int typePrice;
	
	public int getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public int getTypePrice() {
		return typePrice;
	}
	public void setTypePrice(int typePrice) {
		this.typePrice = typePrice;
	}
	@Override
	public String toString() {
		return "GetMyReservationPrice [reservationInfoId=" + reservationInfoId + ", count=" + count + ", ticketType="
				+ ticketType + ", typePrice=" + typePrice + "]";
	}
	
	
	
	
}
