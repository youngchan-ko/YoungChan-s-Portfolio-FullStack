const slideWrap = document.querySelector('.visual_img');
const leftMainList = document.querySelector('.lst_event_box');
const rightMainList = leftMainList.nextElementSibling;
const viewMore = document.querySelector('.more .btn');
const category = document.querySelector('.event_tab_lst');
const tabList = document.querySelectorAll('.event_tab_lst li a');
const promotionImgLength = 11;	
const moreValue = 4;

var mainHtml = document.querySelector("#itemList").innerHTML;
var	sectionCountWrap = document.querySelector(".event_lst_txt");
var	sectionCount = document.querySelector("#sectionCount").innerHTML;
var	btnPageCount = 1;
var	htmlLeft = '';
var	htmlRight = '';	
var categoryId = 0;

function resetProductList(){
	htmlLeft = '';
	htmlRight = '';
}

function resetPageCount(){
	btnPageCount = 0;
}

function targetSelector(){
	if(event.target.tagName === "SPAN") {
		var selectedTarget= event.target.parentElement.parentElement;
	} else if (event.target.tagName === "A") {
		var selectedTarget= event.target.parentElement;
	} else if(event.target.tagName === "LI"){
		var selectedTarget= event.target;
	}
	return selectedTarget;
}

function tabClassController(){
	for(i=0; i<tabList.length; i++){
		tabList[i].className = 'anchor';
	}
	var classTarget = targetSelector();
	classTarget.firstElementChild.className += ' active';
}

//카테고리 이벤트
category.addEventListener('click',function(){
	viewMore.style.display = 'block';
	var selectedTarget = targetSelector();
	categoryId = selectedTarget.getAttribute('data-category');
	tabClassController();
	resetProductList();
	resetPageCount();
	getList(btnPageCount, categoryId);
	btnPageCount++;
});

function setPageCount(){
	var PageCount = viewMore.getAttribute('data-pageCount');
	var start = PageCount * moreValue;
	getList(start, categoryId);
}

//더보기 클릭이벤트
viewMore.addEventListener('click',function(){
	setPageCount(); 
});

function pageCountController(pageCount){
	if (btnPageCount < pageCount){
		viewMore.setAttribute("data-pageCount", btnPageCount++);	
	}else if (btnPageCount == pageCount){
		viewMore.style.display = 'none';
	}
}

function insertSectionCount(serverData){
	var sectionCountHTML = sectionCount.replace("{count}", serverData.totalCount);
	sectionCountWrap.innerHTML = '';
	sectionCountWrap.innerHTML = sectionCountHTML;
}

function appendProducts(serverData){
	for(var i=0; i<serverData.list.length; i++){
		if(0 == i%2){
			htmlLeft += mainHtml.replace(/{productDescription}/gi, serverData.list[i].productDescription)
			.replace("{productImageUrl}", serverData.list[i].fileInfoId)
			.replace("{placeName}", serverData.list[i].placeName)
			.replace("{productContent}", serverData.list[i].productContent)
			.replace("{displayInfoId}", serverData.list[i].displayInfoId);
		} else {
			htmlRight += mainHtml.replace(/{productDescription}/gi, serverData.list[i].productDescription)
			.replace("{productImageUrl}", serverData.list[i].fileInfoId)
			.replace("{placeName}", serverData.list[i].placeName)
			.replace("{productContent}", serverData.list[i].productContent)
			.replace("{displayInfoId}", serverData.list[i].displayInfoId);
		}
	}
	leftMainList.innerHTML = htmlLeft;
	rightMainList.innerHTML = htmlRight;
}

function getList(start, categoryId) {
	var oReq = new XMLHttpRequest();
	oReq.onreadystatechange = function(){
		if(oReq.readyState === 4 && oReq.status === 200){		
			var serverData = JSON.parse(this.responseText);			
			appendProducts(serverData);
			insertSectionCount(serverData);
			pageCountController(serverData.pageCount);
		}
	}
	oReq.open("GET", "/getList.ajax?start="+start+"&categoryId="+categoryId);
	oReq.send();
}

function promotionSlide(){
	let firstItemClone = slideWrap.firstElementChild.cloneNode(true);
	slideWrap.appendChild(firstItemClone);
	var curIndex = 0;
	setInterval(function(){
		slideWrap.style.transition = '0.2s';
		slideWrap.style.transform = "translate3d(-"+414*(curIndex+1)+"px, 0px, 0px)";
		curIndex++;
		if(curIndex === promotionImgLength){
			setTimeout(function(){
				slideWrap.style.transition = '0s';
				slideWrap.style.transform = "translate3d(0px, 0px, 0px)";
			},201)
			curIndex = 0;
		}
	},1000);
}

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
	getList(0, 0);
	promotionSlide();
	new CheckMyReservation();
});


