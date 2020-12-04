package kr.or.connect.reservation.dto;

import java.util.List;

public class ReservationsList {
	private List<Reservations> reservations;
	private int size;
	
	public List<Reservations> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservations> reservations) {
		this.reservations = reservations;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "GetReservations [reservations=" + reservations + ", size=" + size + "]";
	}
	
	
	
}
