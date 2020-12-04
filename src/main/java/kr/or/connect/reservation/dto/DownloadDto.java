package kr.or.connect.reservation.dto;

public class DownloadDto {
	private String fileName;
	private String saveFileName;
	private String contentType;
	
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
	@Override
	public String toString() {
		return "DownloadDto [fileName=" + fileName + ", saveFileName=" + saveFileName + ", contentType=" + contentType
				+ "]";
	}
	
	
	
	
	
}
