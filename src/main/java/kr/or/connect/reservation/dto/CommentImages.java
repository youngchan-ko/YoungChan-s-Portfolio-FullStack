package kr.or.connect.reservation.dto;

import java.sql.Date;

public class CommentImages {
	private int imageId;
	private int reservationInfoId;
	private int reservationUserCommentId;
	private int fileInfoId;
	private String fileName;
	private String saveFileName;
	private String contentType;
	private int deleteFlag;
	private Date createDate;
	private Date modifyDate;
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public int getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public int getReservationUserCommentId() {
		return reservationUserCommentId;
	}
	public void setReservationUserCommentId(int reservationUserCommentId) {
		this.reservationUserCommentId = reservationUserCommentId;
	}
	public int getFileInfoId() {
		return fileInfoId;
	}
	public void setFileInfoId(int fileInfoId) {
		this.fileInfoId = fileInfoId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Override
	public String toString() {
		return "CommentImages [imageId=" + imageId + ", reservationInfoId=" + reservationInfoId
				+ ", reservationUserCommentId=" + reservationUserCommentId + ", fileInfoId=" + fileInfoId
				+ ", fileName=" + fileName + ", saveFileName=" + saveFileName + ", contentType=" + contentType
				+ ", deleteFlag=" + deleteFlag + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}
	

	
	
	
	
	
}
