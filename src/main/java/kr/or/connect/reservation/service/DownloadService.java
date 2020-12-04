package kr.or.connect.reservation.service;

import kr.or.connect.reservation.dto.DownloadDto;

public interface DownloadService {
	public DownloadDto getDownloadFileInfo(int reservationUserCommentImageId);
}