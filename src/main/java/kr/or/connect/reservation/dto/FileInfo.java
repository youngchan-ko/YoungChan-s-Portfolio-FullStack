package kr.or.connect.reservation.dto;

public class FileInfo {
	private int fileInfoId;
	private String fileName;
	private String saveFileName;
	private String contentType;
	private int deleteFlag;
	private String createDate;
	private String modifyDate;
	public int getFileInfoId() {
		return fileInfoId;
	}
	public void setFileInfoId(int ileInfoId) {
		this.fileInfoId = ileInfoId;
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
	@Override
	public String toString() {
		return "FileInfo [ileInfoId=" + fileInfoId + ", fileName=" + fileName + ", saveFileName=" + saveFileName
				+ ", contentType=" + contentType + ", deleteFlag=" + deleteFlag + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + "]";
	}
}
