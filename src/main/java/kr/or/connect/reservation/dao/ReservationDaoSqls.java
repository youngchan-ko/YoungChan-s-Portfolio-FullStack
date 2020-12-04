package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	public static final String GET_PRODUCTS = "select t2.id displayInfoId, t1.id productId, t1.description productDescription, \r\n" + 
											  "t2.place_name placeName,t1.content productContent, t4.file_name productImageUrl, t4.id fileInfoId, t3.type type\r\n" + 
											  "from product t1\r\n" + 
											  "inner join display_info t2 on t1.id = t2.product_id\r\n" + 
											  "inner join product_image t3 on t1.id = t3.product_id\r\n" + 
											  "inner join file_info t4 on t3.file_id = t4.id\r\n" + 
											  "where t3.type = \"th\"\r\n" + 
											  "limit :start, :limit";
	
	public static final String COUNT_TOTAL = "select count(*) cnt \r\n" + 
											 "from product t1\r\n" + 
											 "inner join display_info t2 on t1.id = t2.product_id\r\n" + 
											 "inner join product_image t3 on t1.id = t3.product_id\r\n" + 
											 "inner join file_info t4 on t3.file_id = t4.id\r\n" + 
											 "where t3.type = \"th\"\r\n";
	
	public static final String SELECT_PROMOTION_IMG = "select t1.id id, t2.id productId, t4.save_file_name productImageUrl, t4.id fileInfoId\r\n" + 
													  "from promotion t1\r\n" + 
													  "inner join product t2 on t1.product_id = t2.id\r\n" + 
													  "inner join product_image t3 on t2.id = t3.product_id\r\n" + 
													  "inner join file_info t4 on t3.file_id = t4.id\r\n" + 
													  "WHERE t3.type = \"th\"";
	
	public static final String GET_CATEGORY = "select t2.id displayInfoId, t1.id productId, t1.description productDescription, \r\n" + 
											  "t2.place_name placeName,t1.content productContent, t4.file_name productImageUrl, \r\n" + 
											  "t3.type type, t1.category_id categoryId, t4.id fileInfoId\r\n" + 
											  "from product t1\r\n" + 
											  "inner join display_info t2 on t1.id = t2.product_id\r\n" + 
											  "inner join product_image t3 on t1.id = t3.product_id\r\n" + 
											  "inner join file_info t4 on t3.file_id = t4.id\r\n" + 
											  "inner join category t5 on t1.category_id = t5.id\r\n" + 
											  "where t3.type = \"th\" and t5.id = :categoryId\r\n" +
											  "limit :start, :limit";
	
	public static final String COUNT_CATEGORY_TOTAL = "select count(*)\r\n" + 
													  "from category t1\r\n" + 
													  "inner join product t2 on t1.id = t2.category_id\r\n" + 
													  "inner join display_info t3 on t2.id = t3.product_id\r\n" + 
													  "where t2.category_id = :categoryId";
	
	public static final String GET_CATEGORY_TAB = "select t1.id id, t1.name name, count(t2.category_id) count\r\n" + 
												  "from category t1\r\n" + 
												  "inner join product t2 on t1.id = t2.category_id\r\n" + 
												  "inner join display_info t3 on t2.id = t3.product_id\r\n" + 
												  "group by t2.category_id";
	
	//제목, 콘텐츠 설명, 시간, 주소, 전화번호, 홈페이지, 크레딧, 모디파이
	public static final String GET_DISPLAY_INFO = "select t1.id productId, t2.id categoryId, t3.id displayInfoId, t2.name categoryName, \n" + 
													 "t1.description productDescription, t1.content productContent, t1.`event` productEvent,\n" + 
													 "t3.opening_hours openingHours, t3.place_name placeName, t3.place_lot placeLot,\n" + 
													 "t3.place_street placeStreet, t3.tel telephone, t3.homepage homepage,\n" + 
													 "t3.email email, t3.create_date createDate, t3.modify_date modifyDate\n" + 
													 "from product t1\n" + 
													 "inner join category t2 on t1.category_id = t2.id\n" + 
													 "inner join display_info t3 on t1.id = t3.product_id\n" + 
													 "where t3.id = :displayInfoId";

	//상세페이지 이미지파일 경로
	public static final String GET_PRODUCT_IMAGES = "select t1.id productId, t2.id productImageId, t2.type type, t3.id fileInfoId, \n" + 
													"t3.file_name fileName, t3.save_file_name saveFileName, t3.content_type contentType,\n" + 
													"t3.delete_flag deleteFlag, t3.create_date createDate, t3.modify_date modifyDate, t3.id fileInfoId\n" + 
													"from product t1\n" + 
													"inner join product_image t2 on t1.id = t2.product_id\n" + 
													"inner join file_info t3 on t2.file_id = t3.id\n" + 
													"inner join display_info t4 on t1.id = t4.product_id\n" + 
													"where t4.id = :displayInfoId";
	
	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜 4개만
	public static final String GET_COMMENTS_LIMIT = "select t3.id commentId, t1.id productId, t2.id reservationInfoId, t3.score score, \n" + 
											  "t3.comment comment, t2.reservation_name reservationName, t2.reservation_tel reservationTelephone,\n" + 
											  "t2.reservation_email reservationEmail, t2.reservation_date reservationDate, t2.create_date createDate,\n" + 
											  "t2.modify_date modifyDate\n" + 
											  "from product t1\n" + 
											  "inner join reservation_info t2 on t2.product_id = t1.id\n" + 
											  "inner join reservation_user_comment t3 on t2.id = t3.reservation_info_id\n" + 
											  "inner join display_info t4 on t1.id = t4.product_id\n" + 
											  "where t4.id = :displayInfoId limit :start, :limit";

	//별점, 코멘트, 작성자, 작성자전번, 작성자 이메일, 작성 날짜 모두
	public static final String GET_COMMENTS_ALL = "select t3.id commentId, t1.id productId, t2.id reservationInfoId, t3.score score, \n" + 
													"t3.comment comment, t2.reservation_name reservationName, t2.reservation_tel reservationTelephone,\n" + 
													"t2.reservation_email reservationEmail, t2.reservation_date reservationDate, t2.create_date createDate,\n" + 
													"t2.modify_date modifyDate\n" + 
													"from product t1\n" + 
													"inner join reservation_info t2 on t2.product_id = t1.id\n" + 
													"inner join reservation_user_comment t3 on t2.id = t3.reservation_info_id\n" + 
													"inner join display_info t4 on t1.id = t4.product_id\n" + 
													"where t4.id = :displayInfoId";
	
	//코멘트 이미지 경로
	public static final String GET_COMMENT_IMAGES ="select t1.id imageId, t2.id reservationInfoId, t3.id reservationUserCommentId, t4.id fileInfoId, \n" + 
												   "t4.file_name fileName, t4.save_file_name saveFileName, t4.content_type contentType,\n" + 
												   "t4.delete_flag deleteFlag, t4.create_date createDate, t4.modify_date modifyDate\n" + 
												   "from reservation_user_comment_image t1\n" + 
												   "inner join reservation_info t2 on t1.reservation_info_id = t2.id\n" + 
												   "inner join reservation_user_comment t3 on t1.reservation_user_comment_id = t3.id\n" + 
												   "inner join file_info t4 on t1.file_id = t4.id\n" + 
												   "where t3.id = :userCommentId";
	//좌석 타입, 가격, 할인정보 
	public static final String GET_PRODUCT_PRICE ="select t1.id productPriceId, t2.id productId, t1.price_type_name priceTypeName, t1.price price, \n" + 
												  "t1.discount_rate discountRate, t1.create_date createDate, t1.modify_date modifyDate\n" + 
												  "from product_price t1\n" + 
												  "inner join product t2 on t1.product_id = t2.id\n" + 
												  "inner join display_info t3 on t3.product_id = t2.id\n" + 
												  "where t3.id = :displayInfoId";
	
	public static final String GET_COMMENT_COUNT ="select count(*)\n" + 
												  "from product t1\n" + 
												  "inner join reservation_info t2 on t2.product_id = t1.id\n" + 
												  "inner join reservation_user_comment t3 on t2.id = t3.reservation_info_id\n" + 
												  "inner join display_info t4 on t1.id = t4.product_id\n" + 
												  "where t4.id = :displayInfoId";
	//맵 이미지
	public static final String GET_DISPLAYINFO_IMAGE ="select t1.id displayInfoImageId, t2.id displayInfoId, t3.id fileInfoId, t3.file_name FileName, \n" + 
													  "t3.save_file_name saveFileName, t3.content_type contentType, t3.delete_flag deleteFlag,\n" + 
													  "t3.create_date createDate, t3.modify_date modifyDate\n" + 
													  "from display_info_image t1\n" + 
													  "inner join display_info t2 on t1.display_info_id = t2.id\n" + 
													  "inner join file_info t3 on t1.file_id = t3.id\n" + 
													  "where t2.id = :displayInfoId";
    //myReservation 총 티켓가격	Part of reservations
	//지금은 GET_RESERVATION_INFO에 같이 들어있는데 필요없으면 나중에 지우기
	public static final String GET_TOTAL_PRICE ="SELECT sum(t3.price * t2.count) total\n" + 
												"FROM reservation_info t1\n" + 
												"inner join reservation_info_price t2 on t2.reservation_info_id = t1.id\n" + 
												"inner join product_price t3 on t2.product_price_id = t3.id\n" + 
												"where t1.reservation_email = :reservationEmail";
	
	//reservationInfo가져오기 		Part of reservations
	public static final String GET_RESERVATION_INFO ="select t1.cancel_flag cancelYn, t1.create_date createDate, t1.display_info_id displayInfoId, \n" + 
													 "t1.modify_date modifyDate, t1.product_id productId, t1.reservation_date reservationDate, \n" + 
													 "t1.reservation_email reservationEmail, t1.id reservationInfoId, t1.reservation_name reservationName, \n" + 
													 "t1.reservation_tel reservationTelephone, sum(t3.price * t2.count) totalPrice\n" + 
													 "from reservation_info t1\n" + 
													 "inner join reservation_info_price t2 on t2.reservation_info_id = t1.id\n" + 
													 "inner join product_price t3 on t2.product_price_id = t3.id\n" + 
													 "where t1.reservation_email = :reservationEmail\n" + 
													 "group by t1.display_info_id, cancelYn, createDate, displayInfoId, modifyDate, productId, reservationDate, \n" + 
													 "reservationEmail, reservationInfoId, reservationName, reservationTelephone";
	
	//예매내역 갯수		Part of reservations
	public static final String GET_RESERVATIONS_SIZE ="select count(*)\n" + 
													  "from reservation_info t1\n" + 
													  "where t1.reservation_email = :reservationEmail";
	
	//예매내역 저장할때 displayInfo.id로 productId가져오기
	public static final String GET_PRODUCT_ID ="select t1.product_id\n" + 
											   "from display_info t1\n" + 
											   "where t1.id = :displayInfoId";
	
	//예매내역 저장할때 productId와 ticketType으로 productPriceId가져오기
	public static final String GET_PRODUCT_PRICE_ID ="select t1.id\n" + 
													 "from product_price t1\n" + 
													 "where t1.product_id = :productId and t1.price_type_name= :ticketType";

	//예매내역 티켓별 타입과  타입별 가격
	public static final String GET_MY_RESERVATION_PRICE ="select t1.id reservationInfoId, t2.count count,  t3.price_type_name ticketType, t3.price typePrice\n" + 
														 "from reservation_info t1\n" + 
														 "inner join reservation_info_price t2 on t1.id = t2.reservation_info_id\n" + 
														 "inner join product_price t3 on t2.product_price_id = t3.id\n" + 
														 "where t1.id = :reservationInfoId";
	//티켓취소하기
	public static final String UPDATE_RESERVATION_INFO ="UPDATE reservation_info SET cancel_flag='1', modify_date=NOW() WHERE id=:reservationInfoId";
	
	//API cancel 후에 정보 가져오기
	public static final String GET_CANCEL_RESERVATION ="select t1.cancel_flag cancelYn, t1.create_date createDate, t1.display_info_id displayInfoId,\n" + 
													   "t1.modify_date modifyDate, t1.product_id productId, t1.reservation_date reservationDate,\n" + 
													   "t1.reservation_email reservationEmail, t1.id reservationInfoId, t1.reservation_name reservationName,\n" + 
													   "t1.reservation_tel reservationTelephone\n" + 
													   "from reservation_info t1\n" + 
													   "where t1.id = :reservationInfoId";
	//API cancel 후에 정보 가져오기
	public static final String GET_RESERVATION_INFO_PRICE ="select t2.id reservationInfoPriceId, t2.count count,  t2.reservation_info_id reservationInfoId, \n" + 
														   "t2.product_price_id productPriceId\n" + 
														   "from reservation_info t1\n" + 
														   "inner join reservation_info_price t2 on t1.id = t2.reservation_info_id\n" + 
														   "where t1.id = :reservationInfoId";
	//한줄평쓰기 페이지에 필요한 데이터
	public static final String GET_FOR_WRITE_COMMENT ="select t1.id reservationInfoId, t1.product_id productId, t1.reservation_email reservationEmail, \n" + 
													  "t2.description description\n" + 
													  "from reservation_info t1\n" + 
													  "inner join product t2 on t1.product_id = t2.id\n" + 
													  "where t1.id = :reservationInfoId";
	//리뷰 작성 후에 정보 가져가기
	public static final String GET_COMMENT = "select t1.comment comment, t1.id commentId, t1.create_date createDate, t1.modify_date modifyDate, \n" + 
											 "t1.product_id productId, t1.reservation_info_id reservationInfoId, t1.score score\n" + 
											 "from reservation_user_comment t1\n" + 
											 "where t1.id = :reservationUserCommentId";
	
	//리뷰 작성 후에 정보 가져가기
	public static final String GET_REVIEW_IMAGES = "select t1.id imageId, t1.reservation_info_id reservationInfoId, t1.reservation_user_comment_id reservationUserCommentId, \n" + 
												   "t2.id fileId, t2.file_name fileName, t2.save_file_name saveFileName, t2.content_type contentType, \n" + 
												   "t2.delete_flag deleteFlag, t2.create_date createDate, t2.modify_date modifyDate\n" + 
												   "from reservation_user_comment_image t1\n" + 
												   "inner join file_info t2 on t1.file_id = t2.id\n" + 
												   "where t1.reservation_user_comment_id = :reservationUserCommentId";
	//다운로드 파일 정보
	public static final String GET_DOWNLOAD_FILE_INFO ="select t1.file_name fileName, t1.save_file_name saveFileName, t1.content_type contentType\n" + 
													   "from file_info t1\n" + 
													   "where t1.id = :fileInfoId";
}



