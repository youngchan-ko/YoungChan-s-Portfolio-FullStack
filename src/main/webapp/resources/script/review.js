const titleTarget = document.querySelector('.title');

//코멘트 쓰기
function writeComment(comments, displayInfo){
    const commentTarget = document.querySelector('.list_short_review');
    var commentList = document.querySelector('#comment_list').innerText;
    var commentImageListBase = document.querySelector('#comment_image_list').innerText;
    var resultText = "";
    for(i=0; i<comments.length; i++){
        comments[i].score = comments[i].score.toFixed(1);
    }
    for(i=0; i<comments.length; i++){
        if(comments[i].commentImages.length == 0){
            commentListHtml = commentList.replace("{productDescription}", displayInfo[0].productDescription);
            var commentListHtml = Handlebars.compile(commentListHtml);
            resultText += commentListHtml(comments[i]);
        }else{
            var fileInfoId = comments[i].commentImages[0].fileInfoId;
            var commentImageList = commentImageListBase.replace("{fileInfoId}", fileInfoId)
                                               .replace("{productDescription}", displayInfo[0].productDescription);
            
            var commentImageListHtml = Handlebars.compile(commentImageList);
            resultText += commentImageListHtml(comments[i]);
        }
    }
    commentTarget.innerHTML = resultText;
}

//댓글 총 갯수 쓰기.
function writeCommentCount(commentCount){
    const totalCountTarget = document.querySelector('.green');
    totalCountTarget.innerText = commentCount+" 건";
}

//별점 색칠하기
function writeScore(avg){
    const scoreGraphTarget = document.querySelector('.graph_value');
    const userScoreTarget = document.querySelector('.text_value').firstElementChild;
    if(avg == 'NaN'){
        avg = 0.0;
    }
    var userScore = avg.toFixed(1);
    var scoreGraphValue = userScore * 20;
    userScoreTarget.innerText = userScore;
    scoreGraphTarget.setAttribute('style', "width:"+scoreGraphValue+"%;");
}

//제목쓰기
function writeTitle(displayInfo){
    titleTarget.innerText = displayInfo[0].productDescription;
}

//Ajax 통신
function getList(displayInfoId) {
	var oReq = new XMLHttpRequest();
	oReq.onreadystatechange = function(){
		if(oReq.readyState === 4 && oReq.status === 200){		
            var serverData = JSON.parse(this.responseText);		
            writeTitle(serverData.displayInfo);
            writeScore(serverData.averageScore);
            writeCommentCount(serverData.commentCount);
            writeComment(serverData.comments, serverData.displayInfo);
		}
	}
	oReq.open("GET", "/review.ajax?"+displayInfoId);
	oReq.send();
}

//뒤로가기 parameter설정
function setGoBackId(displayInfoId){
    var moreReviewTarget = document.querySelector('.btn_back');
    moreReviewTarget.setAttribute("href", "./detail?"+displayInfoId);
    titleTarget.setAttribute("href", "./detail?"+displayInfoId);
}

//displayInfoId값 가져오기
function getDisplayInfoId(){
    var para = document.location.href.split("?");
    var displayInfoId = para[1];
    return displayInfoId;
}

document.addEventListener("DOMContentLoaded", function(){
    var displayInfoId = getDisplayInfoId();
    getList(displayInfoId);
    setGoBackId(displayInfoId);
});