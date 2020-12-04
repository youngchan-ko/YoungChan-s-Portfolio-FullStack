package kr.or.connect.reservation.dto;

public class PromotionImgs {
	private int id;
	private int productId;
	private int fileInfoId;
	private String productImageUrl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getFileInfoId() {
		return fileInfoId;
	}
	public void setFileInfoId(int fileInfoId) {
		this.fileInfoId = fileInfoId;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	@Override
	public String toString() {
		return "PromotionImgs [id=" + id + ", productId=" + productId + ", fileInfoId=" + fileInfoId
				+ ", productImageUrl=" + productImageUrl + "]";
	}
	
	
	
	
	
}
