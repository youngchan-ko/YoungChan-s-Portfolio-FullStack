package kr.or.connect.reservation.dto;

public class FormData {
	private String reservationName;
	private String reservationTelephone;
	private String reservationEmail;
	private int displayInfoId;
	private String ticketTypeCount;
	public String getReservationName() {
		return reservationName;
	}
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
	public String getReservationTelephone() {
		return reservationTelephone;
	}
	public void setReservationTelephone(String reservationTelephone) {
		this.reservationTelephone = reservationTelephone;
	}
	public String getReservationEmail() {
		return reservationEmail;
	}
	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}
	public int getDisplayInfoId() {
		return displayInfoId;
	}
	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}
	public String getTicketTypeCount() {
		return ticketTypeCount;
	}
	public void setTicketTypeCount(String ticketTypeCount) {
		this.ticketTypeCount = ticketTypeCount;
	}
	@Override
	public String toString() {
		return "FormData [reservationName=" + reservationName + ", reservationTelephone=" + reservationTelephone
				+ ", reservationEmail=" + reservationEmail + ", displayInfoId=" + displayInfoId + ", ticketTypeCount="
				+ ticketTypeCount + "]";
	}
	
	
	
	
	
	
	
}
