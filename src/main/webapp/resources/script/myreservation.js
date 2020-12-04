

//취소 버튼 이벤트
function AddBtnEvent(){
	this.eventTarget = document.querySelectorAll('.cancel_btn');
	this.popupTarget = document.querySelector('.popup_booking_wrapper');
	this.titleTarget = document.querySelector('.pop_tit').firstElementChild;
	this.openingTimeTarget = document.querySelector('.sm');
	this.noBtn = document.querySelector('.btn_gray');
	this.closeBtn = document.querySelector('.popup_btn_close');
	this.yesBtn = document.querySelector('.btn_green');
	this.reservationInfoId = '';
	this.addEventListner();
}
AddBtnEvent.prototype = {
	addEventListner : function(){
		for(i=0; i<this.eventTarget.length; i++){
			this.eventTarget[i].addEventListener('click', function(){
				event.preventDefault();
				var eventTarget = this.eventTargetSelector(event.target);
				var title = eventTarget.parentElement.parentElement.firstElementChild.nextElementSibling.innerText;
				var openingTime = eventTarget.parentElement.parentElement.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.firstElementChild.nextElementSibling.innerText;
				this.reservationInfoId = eventTarget.parentElement.parentElement.firstElementChild.firstElementChild.innerText;
				this.popupTarget.style.display = 'block';
				this.titleTarget.innerText = title;
				this.openingTimeTarget.innerText = openingTime;
				this.popupNoBtnEvent();
				this.popupCloseBtnEvent();
				this.popupYesBtnEvent(this.reservationInfoId);
				
			}.bind(this))
		}
	},
	eventTargetSelector : function(eventTarget){
		if(eventTarget.tagName === "SPAN"){
			var resultTarget = eventTarget.parentElement;
		} else{
			var resultTarget = eventTarget;
		}
		return resultTarget;
	},
	popupNoBtnEvent : function(){
		this.noBtn.addEventListener('click', function(){
			event.preventDefault();
			this.popupTarget.style.display = 'none';
		}.bind(this))
	},
	popupCloseBtnEvent : function(){
		this.closeBtn.addEventListener('click', function(){
			event.preventDefault();
			this.popupTarget.style.display = 'none';
		}.bind(this))
	},
	popupYesBtnEvent :function(reservationInfoId){
		this.yesBtn.addEventListener('click', function(){
			event.preventDefault();
			this.popupTarget.style.display = 'none';
			this.reservationInfoUpdate(reservationInfoId);
		}.bind(this))
	},
	reservationInfoUpdate : function(reservationInfoId){
		var oReq = new XMLHttpRequest();
		oReq.onreadystatechange = function(){
			if(oReq.readyState === 4 && oReq.status === 200){		
			var serverData = JSON.parse(this.responseText);	
			var reservationEmail = serverData.reservationInfoCancelUpdate.reservationEmail;
			window.location.replace("http://localhost:8080/myreservation?reservationEmail="+reservationEmail);			
			}
		}
		oReq.open("GET", "/cancelupdate.ajax?reservationInfoId="+reservationInfoId);
		oReq.send();
	}
	
}

//티켓 쓰기
function WriteTicket(confirmedTicket, usedTicket, canceledTicket){
	this.confirmedTicketData = confirmedTicket;
	this.usedTicketData = usedTicket;
	this.canceledTicketData = canceledTicket;
	
	this.ticketTemplate = document.querySelector('#ticketCountTemplate').innerText;
	
	this.confirmedTemplate = document.querySelector('#confirmedTicketTemplate').innerText;
	this.confirmedTarget = document.querySelector('.confirmed');

	this.usedTemplate = document.querySelector('#usedTicketTemplate').innerText;
	this.usedTarget = document.querySelector('.used_ticket_target');
	
	this.canceledTemplate = document.querySelector('#canceledTicketTemplate').innerText;
	this.canceledTarget = document.querySelector('.cancel');

	this.replaceTemplate(this.confirmedTicketData, this.confirmedTarget, this.confirmedTemplate);
	this.replaceTemplate(this.usedTicketData, this.usedTarget, this.usedTemplate);
	this.replaceTemplate(this.canceledTicketData, this.canceledTarget, this.canceledTemplate);
	new AddBtnEvent();
}
WriteTicket.prototype = {
	replaceTemplate : function(ticketData, appendTarget, template){
		var resultHtml = '';
		for(i=0; i<ticketData.length; i++){
			var ticketPrice = this.ticketTypeCount(ticketData[i].ticketTypePrice);
			resultHtml += template.replace('{productDescription}', ticketData[i].displayInfo[0].productDescription)
									   .replaceAll('{reservationInfoId}', ticketData[i].reservationInfoId)
									   .replace('{openingHours}', ticketData[i].displayInfo[0].openingHours)
									   .replace('{ticketTypeCount}', ticketPrice)
									   .replace('{placeLot}', ticketData[i].displayInfo[0].placeLot)
									   .replace('{placeStreet}', ticketData[i].displayInfo[0].placeStreet)
									   .replace('{placeName}', ticketData[i].displayInfo[0].placeName)
									   .replace('{totalPrice}', ticketData[i].totalPrice);
			
		}
		appendTarget.innerHTML += resultHtml;
	},
	ticketTypeCount : function(ticketTypePrice){
		var resultHtml = '';
		for(j=0; j<ticketTypePrice.length; j++){
			var ticketType = this.parseTicketType(ticketTypePrice[j].ticketType);
			resultHtml += this.ticketTemplate.replace('{ticketType}', ticketType)
											 .replace('{count}', ticketTypePrice[j].count)
											 .replace('{typePrice}', ticketTypePrice[j].count*ticketTypePrice[j].typePrice);
		}
		return resultHtml;
	},
	parseTicketType : function(ticketType){
		switch(ticketType){
			case 'A':
				ticketType = '성인';
				break;
			case 'Y':
				ticketType = '청소년';
				break;   
			case 'B':
				ticketType = '유아';
				break;   
			case 'S':
				ticketType = '셋트';
				break;   
			case 'D':
				ticketType = '장애인';
				break;   
			case 'C':
				ticketType = '지역주민';
				break;   
			case 'E':
				ticketType = '어얼리버드';
				break;   
			case 'V':
				ticketType = 'VIP';
				break;   
			case 'R':
				ticketType = 'R석';
				break;   
			case 'B':
				ticketType = 'B석';
				break;   
			case 'S':
				ticketType = 'S석';
				break;   
		}
		return ticketType;
	}
}

//취소 확정 사용 티켓 나누기
function GetCanceledTicket(serverData){
	this.serverData = serverData;
	this.canceledTicket = new Array();
	this.uncanceledTicket = new Array();
	this.usedTicket = new Array();
	this.confirmedTicket = new Array();
	this.pickCanceled();
	this.pickUnused();
}
GetCanceledTicket.prototype = {
	pickCanceled : function(){
		for(i=0; i<this.serverData.length; i++){
			if(this.serverData[i].cancelYn === 1){
				this.canceledTicket.push(this.serverData[i]);
			}else{
				this.uncanceledTicket.push(this.serverData[i]);
			}
		}
	},
	pickUnused : function(){
		var makeToday = new Date();
		var today = new Date(makeToday.getFullYear(), makeToday.getMonth(), makeToday.getDate());
		for(i=0; i<this.uncanceledTicket.length; i++){
			var reservationDate = this.stringToDate(this.uncanceledTicket[i].reservationDate);
			if(reservationDate < today){
				this.usedTicket.push(this.uncanceledTicket[i]);
			}else{
				this.confirmedTicket.push(this.uncanceledTicket[i]);
			}
		}
	},
	stringToDate : function(dateString){
		var year = dateString.substr(0,4);
		var month = dateString.substr(5,2);
		var day = dateString.substr(8,2);
		var date = new Date(year, month-1, day);
		return date;
	}
}

//티켓별 카운트 쓰기
function WriteTicketCount(totalTicketCount, canceledTicketCount, usedTicketCount, confirmedTicketCount){
	this.totalTicketCount = totalTicketCount;
	this.canceledTicketCount = canceledTicketCount;
	this.usedTicketCount = usedTicketCount;
	this.confirmedTicketCount = confirmedTicketCount;
	this.countTarget = document.querySelector('.my_summary');
	this.ticketCountTemplate =document.querySelector('#totalTicketCountTemplate').innerText;
	this.writeCounts();
}
WriteTicketCount.prototype = {
	writeCounts : function(){
		
		var result = this.ticketCountTemplate.replace('{totalTicketCount}', this.totalTicketCount)
								.replace('{confirmedTicketCount}', this.confirmedTicketCount)
								.replace('{usedTicketCount}', this.usedTicketCount)
								.replace('{canceledTicketCount}', this.canceledTicketCount);
		
		this.countTarget.innerHTML = result;
	}
}

//리스트 받아오기
function GetList() {
	this.getList();
}
GetList.prototype = {
	getList : function(){
		var oReq = new XMLHttpRequest();
		oReq.onreadystatechange = function(){
		if(oReq.readyState === 4 && oReq.status === 200){		
			var serverData = JSON.parse(this.responseText);				
			var canceledTicket = new GetCanceledTicket(serverData.getReservations).canceledTicket;
			var usedTicket = new GetCanceledTicket(serverData.getReservations).usedTicket;
			var confirmedTicket = new GetCanceledTicket(serverData.getReservations).confirmedTicket;
			
			new WriteTicket(confirmedTicket, usedTicket, canceledTicket);
			new WriteTicketCount(serverData.getReservations.length, canceledTicket.length, usedTicket.length, confirmedTicket.length);
			
		}
	}
	oReq.open("GET", "/myreservations.ajax");
	oReq.send();
	}
}

document.addEventListener("DOMContentLoaded", function(){
    new GetList();
});

