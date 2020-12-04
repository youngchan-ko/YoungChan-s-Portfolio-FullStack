package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.CommentWritePage;

public interface WriteCommentService {
	public List<CommentWritePage> getInfoForWriteCommentPage(int reservationInfoId);
	
}