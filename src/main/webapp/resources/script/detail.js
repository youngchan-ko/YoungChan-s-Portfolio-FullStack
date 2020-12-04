const TAB_MENU = document.querySelector('.info_tab_lst');
let reservationBtn = document.querySelector('.bk_btn');
let slideBtnNext = document.querySelector('.nxt'); 
let slideBtnPrev = document.querySelector('.prev'); 
let currentSlideNumber= document.querySelector('#current_slide_number');
let totalSlideNumber= document.querySelector('#total_slide_number span');

//탭메뉴 이벤트 타겟 설정
function targetSelector(){
	if(event.target.tagName === "SPAN") {
		var selectedTarget= event.target.parentElement;
	} else if (event.target.tagName === "A") {
		var selectedTarget= event.target;
	} else if(event.target.tagName === "LI"){
		var selectedTarget= event.target.firstElementChild;
	}
	return selectedTarget;
}

//탭메뉴 활성화 클래스 변경
function TabMenuClassController(selectedTarget){
    this.tabMenuTarget = document.querySelectorAll('.info_tab_lst li a');
    this.detailInfo = document.querySelector('.detail_area_wrap');
    this.detailLocation = document.querySelector('.detail_location');
    this.selectedTarget = selectedTarget;
    this.classNameController();
}
TabMenuClassController.prototype = {
    classNameController : function(){
        for(i=0; i<this.tabMenuTarget.length; i++){
            this.tabMenuTarget[i].className = 'anchor';
        }
        if(this.selectedTarget.firstElementChild.innerText === unescape("%uC0C1%uC138%uC815%uBCF4")){
            this.detailLocation.className = 'detail_location hide';
            this.detailInfo.className = 'detail_area_wrap';
        }else{
            this.detailInfo.className = 'detail_area_wrap hide';
            this.detailLocation.className = 'detail_location';
        }
        this.selectedTarget.className += ' active';
    }
}

//상세정보 오시는길 탭메뉴 이벤트
TAB_MENU.addEventListener('click', function(){
    event.preventDefault();
    var selectedTarget = targetSelector();
    var tabMenuClassController = new TabMenuClassController(selectedTarget);
});

//장소 정보 쓰기
function WriteLocation(mapImage, displayInfo){
    this.locationTarget = document.querySelector('.detail_location');
    this.locationContents = document.querySelector('#location_contents').innerText;
    this.mapImage = mapImage;
    this.displayInfo = displayInfo;
    this.resultHtml = "";
    this.writeLocationInfo();
    this.blockEvent();
}
WriteLocation.prototype = {
    writeLocationInfo : function(){
        var locationContentsHtml = Handlebars.compile(this.locationContents);
        this.resultHtml = locationContentsHtml(this.displayInfo);
        this.resultHtml = this.resultHtml.replace("{fileInfoId}", this.mapImage.fileInfoId);
        this.locationTarget.innerHTML = this.resultHtml;
    },
    blockEvent : function(){
        var naviBtn = document.querySelector('.bottom_common_path');
        var mapAnchor = document.querySelector('.store_location');
        mapAnchor.addEventListener('click', function(){
            event.preventDefault();
        });
        naviBtn.addEventListener('click', function(){
            event.preventDefault();
        });
    }
}

//코멘트 쓰기
function WriteComment(commentsLimit){
    this.commentTarget = document.querySelector('.list_short_review');
    this.commentList = document.querySelector('#comment_list').innerText;
    this.commentImageListBase = document.querySelector('#comment_image_list').innerText;
    this.resultText = "";
    this.commentsLimit = commentsLimit;
    this.parseScore();
    this.writeComment();
}
WriteComment.prototype = {
    parseScore : function(){
        for(i=0; i<this.commentsLimit.length; i++){
            this.commentsLimit[i].score = this.commentsLimit[i].score.toFixed(1);
        }
    },
    writeComment : function(){
        for(i=0; i<this.commentsLimit.length; i++){
            if(this.commentsLimit[i].commentImages.length == 0){
                var commentListHtml = Handlebars.compile(this.commentList);
                this.resultText += commentListHtml(this.commentsLimit[i]);
            }else{
                var fileInfoId = this.commentsLimit[i].commentImages[0].fileInfoId;
                var commentImageList = this.commentImageListBase.replace("{fileInfoId}", fileInfoId);
                var commentImageListHtml = Handlebars.compile(commentImageList);
                this.resultText += commentImageListHtml(this.commentsLimit[i]);
            }
        }
        this.commentTarget.innerHTML = this.resultText;
    }
}

//별점 색칠하기,평균점 쓰기
function WriteScore(avg){
    this.userScoreTarget = document.querySelector('.text_value').firstElementChild;
    this.scoreGraphTarget = document.querySelector('.graph_value');
    this.userScore = 0;
    this.scoreGraphValue = 0;
    this.avg = avg;
    this.avgInit();
    this.parseAvg();
    this.writeAvg();
}
WriteScore.prototype = {
    avgInit : function(){
        if(this.avg === 'NaN'){
            this.avg = 0.0;
        }
    },
    parseAvg : function(){
        this.userScore = this.avg.toFixed(1);
        this.scoreGraphValue = this.userScore * 20;
    }, 
    writeAvg : function(){
        this.userScoreTarget.innerText = this.userScore;
        this.scoreGraphTarget.setAttribute('style', "width:"+this.scoreGraphValue+"%;");

    }
}

//댓글 총 갯수 쓰기.
function WriteCommentCount(commentTotalCount){
    this.commentCountTarget = document.querySelector('.green');
    this.commentTotalCount = commentTotalCount;
    this.commentCountText = document.querySelector('#comment_count').innerText;
    this.writeCommentTotal();
}
WriteCommentCount.prototype = {
    writeCommentTotal : function(){
        var commentCountHtml = this.commentCountText.replace("{commentCount}", this.commentTotalCount);
        this.commentCountTarget.prepend(commentCountHtml);
    }
}   

//할인정보 쓰기.   
function WriteDiscountInfo(data){
    this.discountInfoTarget = document.querySelector('.discount_info_wrap');
    this.discountRateInfo = data;
    this.discountRate = 0;
    this.noDiscountRate = document.querySelector('#without_discount_info').innerText;
    this.discountInfoText = document.querySelector('#discount_info').innerText;
    this.resultText = "";
    this.getDisplayInfoIdLength();
    this.writeDiscountRate();
}
WriteDiscountInfo.prototype = {
    getDisplayInfoIdLength : function(){
        for(i=0; i<this.discountRateInfo.length; i++){
            this.discountRate += this.discountRateInfo[i].discountRate; 
        }
    },
    writeDiscountRate : function(){
        if(this.discountRate === 0){
            this.discountInfoTarget.innerHTML = this.noDiscountRate;
        }else{
            var discountInfoHtml = Handlebars.compile(this.discountInfoText);
            for(i=0; i<this.discountRateInfo.length; i++){
            	if(i < this.discountRateInfo.length-1){
                    this.resultText += discountInfoHtml(this.discountRateInfo[i]) + ",";
                }else if(i=this.discountRateInfo.length-1){
                    this.resultText += discountInfoHtml(this.discountRateInfo[i]);
                }
            }
            this.discountInfoTarget.innerHTML = this.resultText + " 할인";
        }

    }
}

//상품정보 접기 이벤트
$('._close').click(function(e){
    e.preventDefault();
    $('.store_details').addClass("close3");
    $(this).css("display", "none");
    $('._open').css("display", "block");
});

//상품정보 펼치기 이벤트
$('._open').click(function(e){
    e.preventDefault();
    $('.store_details').removeClass("close3");
    $(this).css("display", "none");
    $('._close').css("display", "block");
});

//상품 정보 쓰기
function WriteContent(data){
    this.contentTarget = document.querySelector('.dsc');
    this.inContentTarget = document.querySelector('.detail_info_lst .in_dsc');
    this.productContentHtml = document.querySelector('#product_content').innerText;
    this.productDescription = data;
    this.resultHtml = "";
    this.writeDescription();
}
WriteContent.prototype = {
    writeDescription : function(){
        var contentHtml = Handlebars.compile(this.productContentHtml);
        this.resultHtml = contentHtml(this.productDescription);
        this.contentTarget.innerHTML = this.resultHtml;
        this.inContentTarget.innerHTML = this.resultHtml;
    }
}

//슬라이드 비활성화
function DeactivateSlide(){
    this.resetTotalSlide = 1,
    this.resetCurrentSlide = 1
    this.nextBtnDisplayControoler();
    this.slideInit();
    
}
DeactivateSlide.prototype = {
    nextBtnDisplayControoler : function(){
        slideBtnNext.style.display = 'none';
        slideBtnPrev.style.display = 'none';
    }, 
    slideInit : function(){
        currentSlideNumber.innerText = this.resetCurrentSlide;
        totalSlideNumber.innerText = this.resetTotalSlide;
    }
}
    
//슬라이드 구현
function WriteSlideImage(){
    this.slideWrap = document.querySelector('.visual_img'); 
    this.slideContents = document.querySelectorAll('.visual_img li'); 
    this.slideLength = this.slideContents.length; 
    this.slideWidth = 414; 
    this.slideSpeed = 300; 
    this.startNum = 0;
    this.firstChild = this.slideWrap.firstElementChild;
    this.lastChild = this.slideWrap.lastElementChild;
    this.clonedFirst = this.firstChild.cloneNode(true);
    this.clonedLast = this.lastChild.cloneNode(true);
    this.curIndex = 0;
    this.writeSlideLi();
    this.nextBtnEvent();
    this.prevBtnEvent();
}
WriteSlideImage.prototype = {
    writeSlideLi: function () {
        this.slideWrap.style.width = this.slideWidth * (this.slideLength + 2) + "px";
        this.slideWrap.appendChild(this.clonedFirst);
        this.slideWrap.insertBefore(this.clonedLast, this.slideWrap.firstElementChild);
        this.slideWrap.style.transform = "translate3d(-" + (this.slideWidth * (this.startNum + 1)) + "px, 0px, 0px)";
        this.curIndex = this.startNum;
        totalSlideNumber.innerText = this.slideLength;
    },
    //넥스트버튼 이벤트
    nextBtnEvent: function () {
        slideBtnNext.firstElementChild.addEventListener('click', function () {
            if (this.curIndex <= this.slideLength - 1) {
                this.slideWrap.style.transition = this.slideSpeed + "ms";
                this.slideWrap.style.transform = "translate3d(-" + (this.slideWidth * (this.curIndex + 2)) + "px, 0px, 0px)";
            }
            if (this.curIndex === this.slideLength - 1) {
                setTimeout(function () {
                    this.slideWrap.style.transition = "0ms";
                    this.slideWrap.style.transform = "translate3d(-" + this.slideWidth + "px, 0px, 0px)";
                }.bind(this), this.slideSpeed);
                this.curIndex = -1;
            }
            currentSlideNumber.innerText = this.curIndex + 2;
            curSlide = this.slideContents[++this.curIndex];
        }.bind(this));
    },
    //프리뷰버튼 이벤트
    prevBtnEvent: function () {
        slideBtnPrev.firstElementChild.addEventListener('click', function () {
            if (this.curIndex >= 0) {
                this.slideWrap.style.transition = this.slideSpeed + "ms";
                this.slideWrap.style.transform = "translate3d(-" + (this.slideWidth * this.curIndex) + "px, 0px, 0px)";
            }
            if (this.curIndex === 0) {
                setTimeout(function () {
                    this.slideWrap.style.transition = "0ms";
                    this.slideWrap.style.transform = "translate3d(-" + (this.slideWidth * this.slideLength) + "px, 0px, 0px)";
                }.bind(this), this.slideSpeed);
                this.curIndex = this.slideLength;
            }
            currentSlideNumber.innerText = this.curIndex;
            curSlide = this.slideContents[--this.curIndex];
        }.bind(this));
    }
}

//상품 이미지 쓰기
function WriteProductImages(productImages, displayInfo){
    this.productImageTarget = document.querySelector('.visual_img');
    this.productImageList = document.querySelector('#product_image_list').innerText;
    this.productImages = productImages;
    this.displayInfo = displayInfo;
    this.writeProductImages();
    this.controllSlide();
}
WriteProductImages.prototype = {
    writeProductImages : function(){
        var replacedList = this.productImageList.replace("{productDescription}", this.displayInfo.productDescription);
        var resultHtml = '';
        for (i=0; i<this.productImages.length; i++){
            if(this.productImages[i].type != 'ma'){
                var productImageListHtml = Handlebars.compile(replacedList);
                resultHtml += productImageListHtml(this.productImages[i]);
            }
        }
        this.productImageTarget.innerHTML = resultHtml;
    },
    controllSlide : function(){
        var listLength = this.productImageTarget.childElementCount;
        if(listLength <=1){
            var deactivateSlide = new DeactivateSlide();
        }else{
            var writeSlideImage = new WriteSlideImage();
        }
    }
}

//Ajax 통신
function getList(displayInfoId) {
	var oReq = new XMLHttpRequest();
	oReq.onreadystatechange = function(){
		if(oReq.readyState === 4 && oReq.status === 200){		
			var serverData = JSON.parse(this.responseText);				
            var slideImage = new WriteProductImages(serverData.productImages, serverData.displayInfo[0]);
            var writeContent = new WriteContent(serverData.displayInfo[0]);
            var writeDiscountInfo = new WriteDiscountInfo(serverData.productPrice);
            var writeCommentCount = new WriteCommentCount(serverData.commentCount);
            var writeScore = new WriteScore(serverData.averageScore);
            var writeComment = new WriteComment(serverData.commentsLimit);
            var writeLocation = new WriteLocation(serverData.mapImage[0], serverData.displayInfo[0]);
		}
	}
	oReq.open("GET", "/detail.ajax?"+displayInfoId);
	oReq.send();
}

//예매하기 버튼 parameter설정
function setReservationBtn(displayInfoId){
    reservationBtn.setAttribute("onclick", "location.href='/reservations?"+displayInfoId+" '");
};

//코멘트 더보기 parameter설정
function setReviewId(displayInfoId){
    var moreReviewTarget = document.querySelector('.btn_review_more');
    moreReviewTarget.setAttribute("href", "/review?"+displayInfoId);
}

//displayInfoId값 가져오기
function getDisplayInfoId(){
    var para = document.location.href.split("?");
    var displayInfoId = para[1];
    return displayInfoId;
}

//예악확인 링크주소 이벤트
function CheckMyReservation(){
    this.eventTarget = document.querySelector('.btn_my');
	this.addEventListener();
}
CheckMyReservation.prototype = {
	addEventListener : function(){
        this.eventTarget.addEventListener('click', function(){
            var innerTextTarget = this.eventTargetSelector(event.target);
            if(innerTextTarget.innerText == unescape("%uC608%uC57D%uD655%uC778")){
                this.eventTarget.setAttribute('href', '/login');
            } else{
                this.eventTarget.setAttribute('href', "http://localhost:8080/myreservation?reservationEmail="+innerTextTarget.innerText);
            }
        }.bind(this))
        
	},
	eventTargetSelector : function(eventTarget){
		if(eventTarget.tagName === "A"){
			var resultTarget = eventTarget.firstElementChild;
		} else{
			var resultTarget = eventTarget;
		}
		return resultTarget;
	}
}

document.addEventListener("DOMContentLoaded", function(){
    var displayInfoId = getDisplayInfoId();
    getList(displayInfoId);
    setReviewId(displayInfoId);
    setReservationBtn(displayInfoId);
    new CheckMyReservation();
});



