package kr.or.connect.reservation.service;

import kr.or.connect.reservation.dto.CommentList;
import kr.or.connect.reservation.dto.ReviewData;

public interface WriteReviewService {
	public CommentList writeReview(ReviewData writeReview);
}