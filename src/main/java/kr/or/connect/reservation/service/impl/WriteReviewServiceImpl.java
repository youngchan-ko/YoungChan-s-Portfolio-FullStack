package kr.or.connect.reservation.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.AddFileInfoDao;
import kr.or.connect.reservation.dao.AddReservationUserCommentDao;
import kr.or.connect.reservation.dao.AddReservationUserCommentImageDao;
import kr.or.connect.reservation.dao.GetCommentDao;
import kr.or.connect.reservation.dto.CommentImages;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.CommentList;
import kr.or.connect.reservation.dto.ReservationUserComment;
import kr.or.connect.reservation.dto.ReservationUserCommentImage;
import kr.or.connect.reservation.dto.ReviewData;
import kr.or.connect.reservation.service.WriteReviewService;

@Service
public class WriteReviewServiceImpl implements WriteReviewService {
	
	@Autowired
	AddFileInfoDao addFileInfoDao;

	@Autowired
	AddReservationUserCommentDao addReservationUserCommentDao;

	@Autowired
	AddReservationUserCommentImageDao addReservationUserCommentImageDao;

	@Autowired
	GetCommentDao getCommentDao;
	
	private String baseDir = File.separator + "temp" + File.separator;
	private String savedDir = "review_img" + File.separator + new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
	private String formattedDir = baseDir + savedDir;
	private SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
	private Date time = new Date();
	private String now = dateFormat.format(time);
	
	
	@Override
	@Transactional
	public CommentList writeReview(ReviewData writeReview) {
		ReservationUserComment reservationUserComment = makeReservationUserComment(writeReview);
		int reservationUserCommentId = addReservationUserCommentDao.insertReservationUserComment(reservationUserComment);
		if(writeReview.getReviewImage() != null){
			String saveFileName = makeFileName(writeReview);
			WriteFile(writeReview, saveFileName);
			FileInfo fileInfo = makeFileInfo(writeReview, saveFileName);
			int fileInfoId = addFileInfoDao.insertFileInfo(fileInfo);
			
			ReservationUserCommentImage reservationUserCommentImage = makeReservationUserCommentImage(fileInfoId, writeReview, reservationUserCommentId);
			int reservationUserCommentImageId = addReservationUserCommentImageDao.insertReservationUserComment(reservationUserCommentImage);
		}
		CommentList getComment = makeGetComment(reservationUserCommentId);
		return getComment;
	}
	
	private String makeFileName(ReviewData writeReview) {
			String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String saveFilename = dateStr+writeReview.getReviewImage().getOriginalFilename();
			return saveFilename;
	}

	private void WriteFile(ReviewData writeReview, String saveFileName) {
		File f = new File(formattedDir);
		if(!f.exists()){ // 저장 디렉토리 확인
			f.mkdirs(); // 해당 디렉토리 만들기
		}
		try(
                FileOutputStream fos = new FileOutputStream(formattedDir + File.separator + saveFileName);
                InputStream is = writeReview.getReviewImage().getInputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = is.read(buffer)) != -1){
                fos.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
	
	private FileInfo makeFileInfo(ReviewData writeReview, String saveFileName) {
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setContentType(writeReview.getReviewImage().getContentType());
		fileInfo.setCreateDate(now);
		fileInfo.setDeleteFlag(0);
		fileInfo.setFileName(saveFileName);
		fileInfo.setModifyDate(now);
		fileInfo.setSaveFileName(savedDir + File.separator + saveFileName);
		
		return fileInfo;
	}
	
	private ReservationUserComment makeReservationUserComment(ReviewData writeReview) {
		ReservationUserComment reservationUserComment = new ReservationUserComment();
		reservationUserComment.setComment(writeReview.getComment());
		reservationUserComment.setCreateDate(now);
		reservationUserComment.setModifyDate(now);
		reservationUserComment.setProductId(writeReview.getProductId());
		reservationUserComment.setReservationInfoId(writeReview.getReservationInfoId());
		reservationUserComment.setScore(Double.valueOf(writeReview.getScore()));
		
		return reservationUserComment;
	}
	
	private ReservationUserCommentImage makeReservationUserCommentImage(int fileInfoId, ReviewData writeReview, int reservationUserCommentId) {
		ReservationUserCommentImage reservationUserCommentImage = new ReservationUserCommentImage();
		reservationUserCommentImage.setFileId(fileInfoId);
		reservationUserCommentImage.setReservationInfoId(writeReview.getReservationInfoId());
		reservationUserCommentImage.setReservationUserCommentId(reservationUserCommentId);
		
		return reservationUserCommentImage;
	}
	
	private CommentList makeGetComment(int reservationUserCommentId) {
		List<CommentList> getCommentList = getCommentDao.getComment(reservationUserCommentId);
		List<CommentImages> commentImages = getCommentDao.getReviewImages(reservationUserCommentId);
		
		CommentList getComment = new CommentList();
		getComment.setComment(getCommentList.get(0).getComment());
		getComment.setCommentId(getCommentList.get(0).getCommentId());
		getComment.setCommentImages(commentImages);
		getComment.setCreateDate(getCommentList.get(0).getCreateDate());
		getComment.setModifyDate(getCommentList.get(0).getModifyDate());
		getComment.setProductId(getCommentList.get(0).getProductId());
		getComment.setReservationInfoId(getCommentList.get(0).getReservationInfoId());
		getComment.setScore(getCommentList.get(0).getScore());
		return getComment;
	}
}
