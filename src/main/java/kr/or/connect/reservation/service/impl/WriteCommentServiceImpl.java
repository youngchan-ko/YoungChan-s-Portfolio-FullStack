package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.WriteCommentDao;
import kr.or.connect.reservation.dto.CommentWritePage;
import kr.or.connect.reservation.service.WriteCommentService;

@Service
public class WriteCommentServiceImpl implements WriteCommentService {
	@Autowired
	WriteCommentDao writeCommentDao;

	@Override
	public List<CommentWritePage> getInfoForWriteCommentPage(int reservationInfoId) {
		List<CommentWritePage> infoForWriteCommentPage = writeCommentDao.getInfoForWriteCommentPage(reservationInfoId);
		return infoForWriteCommentPage;
	}
	
	
	
}
