const NAME_INPUT = document.querySelector("[name='reservationName']");
const TEL_INPUT = document.querySelector("[name='reservationTelephone']");
const EMAIL_INPUT = document.querySelector("[name='reservationEmail']");

//Ajax로 폼데이터 보내기
function SendForm(){
    this.formData = '';
    this.makeFormData();
    this.sendAjax();	
}
SendForm.prototype = {
    makeFormData : function(){
        var formArray = $('.form_horizontal').serializeArray();
        var returnArray = {};
        for (var i = 0; i < formArray.length; i++){
            returnArray[formArray[i]['name']] = formArray[i]['value'];
        }
        this.formData = JSON.stringify(returnArray);
    },
    sendAjax : function(){
        var oReq = new XMLHttpRequest();
	    oReq.onreadystatechange = function(){
            if(oReq.readyState === 4 && oReq.status === 200){		
                var serverData = JSON.parse(this.responseText);	
                if(serverData == 1){
                    alert("예매가 정상적으로 완료되었습니다.");
                    window.location.replace("/");
                }else{
                    alert("예매가 비정상적으로 완료되었습니다. 다시 예약해주세요.");
                }
            }
        }
        oReq.open("POST", "/reservating.ajax");
        oReq.setRequestHeader('Content-Type', 'application/json');
        oReq.send(this.formData);
    }
}

//블라인드 요소 쓰기
function WriteBlind(){
    this.baseHtml = document.querySelector('#ticketTypeCount').innerHTML;
    this.displayInfoValueHtml = document.querySelector('#displayInfoValue').innerHTML;
    this.items = document.querySelectorAll('.qty');
    this.appendTarget = document.querySelector('.form_horizontal');
    this.ticketCount = new Object();
    this.displayInfoValue = this.getDisplayInfoValue();
    this.getTypeAndCount();
    this.writeDisplayInfoValue();
}
WriteBlind.prototype = {
    getTypeAndCount : function(){
        for(i=0; i<this.items.length; i++){
            var ticket = new Object();
            var korTicketType = this.items[i].firstElementChild.nextElementSibling.firstElementChild.firstElementChild.innerText;
            var engTicketType = this.korToEngType(korTicketType);
            var ticketCount = this.items[i].firstElementChild.firstElementChild.firstElementChild.nextElementSibling.getAttribute('value');
            this.ticketCount[engTicketType] = ticketCount;
        }
        this.writeBlind();
    },
    korToEngType : function(korTicketType){
        var engTicketType ='';
        switch(korTicketType){
            case '성인':
                engTicketType = 'A';
                break;
            case '청소년':
                engTicketType = 'Y';
                break;   
            case '유아':
                engTicketType = 'B';
                break;   
            case '셋트':
                engTicketType = 'S';
                break;   
            case '장애인':
                engTicketType = 'D';
                break;   
            case '지역주민':
                engTicketType = 'C';
                break;   
            case '어얼리버드':
                engTicketType = 'E';
                break;   
            case 'VIP':
                engTicketType = 'V';
                break;   
            case 'R석':
                engTicketType = 'R';
                break;   
            case 'B석':
                engTicketType = 'B';
                break;   
            case 'S석':
                engTicketType = 'S';
                break;   
        }
        return engTicketType;
    },
    writeBlind : function(){
        var ticketValue =JSON.stringify(this.ticketCount)
        var resultHtml = this.baseHtml.replace('{ticketCount}', ticketValue);
        this.appendTarget.innerHTML += resultHtml;                           
    },
    getDisplayInfoValue : function(){
        var para = document.location.href.split("=");
        var displayInfoId = para[1];
        return displayInfoId;
    },
    writeDisplayInfoValue : function(){
        var resultHtml = '';
        resultHtml = this.displayInfoValueHtml.replaceAll('{displayInfoValue}', this.displayInfoValue);
        this.appendTarget.innerHTML += resultHtml;   
    }
}

//체크박스 이벤트리스너
function CheckBoxAddEventListner(){
    this.eventTarget = document.querySelector('#chk3');
    this.addEventListner();
}
CheckBoxAddEventListner.prototype = {
    addEventListner : function(){
        this.eventTarget.addEventListener('click', function(){
            new ActivateReserveBtn();
        })
    }
}

//필수 입력정보 확인, 예매버튼 활성화
function ActivateReserveBtn(){
    this.successMsg = document.querySelectorAll('.success');
    this.agreeCheckBox = document.querySelector('.chk_agree');
    this.totalTicketCount = document.getElementById('totalCount').innerText;
    this.checkBox = document.getElementById('chk3');
    this.reservationBtn = document.querySelector('.bk_btn_wrap');
    this.checkInsert = this.checkInsertElements();
    this.checkTicket = this.checkTicketCount();
    this.checked = this.checkCheckBox();
    this.activateReserveBtn();
}
ActivateReserveBtn.prototype = {
    checkInsertElements : function(){
        var result = '';
        for(i=0; i<this.successMsg.length; i++){
            result += this.successMsg[i].style.display; 
        }
        if(result === 'blockblockblock'){
            return true;
        }else{
            return false;
        }
    },
    checkTicketCount : function(){
        var ticketCount = parseInt(this.totalTicketCount);
        if(ticketCount>0){
            return true;
        }else{
            return false;
        }
    },
    checkCheckBox : function(){
       var result = this.checkBox.checked;
       return result;
    },
    activateReserveBtn : function(){
        if(this.checkInsert && this.checkTicket && this.checked){
            this.reservationBtn.className = "bk_btn_wrap";
            this.reservationBtn.onclick = function(){
                new WriteBlind();
                new SendForm();
            }.bind(this);
        }else{
            this.reservationBtn.className = "bk_btn_wrap disable";
            this.reservationBtn.onclick = function(){
                event.preventDefault();
            }
        }
    }

}

//개인정보 약관 펼치기
function AgreeEvent(){
    this.eventTarget = document.querySelectorAll('.btn_agreement');
    this.addEvent();
}
AgreeEvent.prototype = {
    addEvent : function(){
        for(i=0; i<this.eventTarget.length; i++){
            this.eventTarget[i].addEventListener("click",function(){
                event.preventDefault();
                var selctedTarget = this.eventTargetSelector();
                selctedTarget.classList.toggle("open");
                var openCloseBtn = selctedTarget.firstElementChild.nextElementSibling.firstElementChild;
                var arrow = selctedTarget.firstElementChild.nextElementSibling.firstElementChild.nextElementSibling;
                if(selctedTarget.classList.contains('open')){
                    openCloseBtn.innerText = '접기';
                    arrow.className = 'fn fn-up2';
                } else{
                    openCloseBtn.innerText = '보기';
                    arrow.className = 'fn fn-down2';
                }
            }.bind(this));
        }
    },
    eventTargetSelector : function(){
        if(event.target.tagName === 'A'){
            var selectedTarget= event.target.parentElement;
        } else if(event.target.tagName === 'SPAN'){
            var selectedTarget= event.target.parentElement.parentElement;
        } else if(event.target.tagName === 'I'){
            var selectedTarget= event.target.parentElement.parentElement;
        }
        return selectedTarget;
    }
}

//오늘 날짜 쓰기
function WriteDate(){
    this.today = new Date();   
    this.year = this.today.getFullYear(); 
    this.month = this.today.getMonth() + 1;  
    this.date = this.today.getDate();  
    this.dateTarget = document.querySelector('.date_data');
    this.dateText = document.querySelector('#date').innerText;
    this.writeDate();
    
}
WriteDate.prototype = {
    writeDate : function(){
        var todayText = this.year + '.' + this.month + '.' + this.date;
        this.dateTarget.innerHTML = this.dateText.replace("{date}", todayText);
       
    }
}

//예매자정보 이벤트핸들러
function InputEventHandler(warningMessage, successMessage, valueCheck){
    this.warningMessage = warningMessage;
    this.successMessage = successMessage;
    this.valueCheck = valueCheck;
    this.handler();
}
InputEventHandler.prototype = {
    handler : function(){
        if(!this.valueCheck){
            this.warningMessage.style.display="block";
            this.successMessage.style.display="none";
        } else if(this.valueCheck){
            this.successMessage.style.display="block";
            this.warningMessage.style.display="none";
            event.target.setAttribute('value', event.target.value);
        }
    }
}

//이메일 인풋 이벤트리스너
EMAIL_INPUT.addEventListener("change", function(){
    var emailFilter = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    var emailValueCheck = (emailFilter).test(EMAIL_INPUT.value);
    var warningMessage = document.querySelector('.email_wrap .warning');
    var successMessage = document.querySelector('.email_wrap .success');
    new InputEventHandler(warningMessage, successMessage, emailValueCheck);
    new ActivateReserveBtn();
});

//전화번호 인풋 이벤트리스너
TEL_INPUT.addEventListener("change", function(){
    var telFilter = /^\d{3}-\d{3,4}-\d{4}|\d{2,3}-\d{3,4}-\d{4}$/;
    var telValueCheck = (telFilter).test(TEL_INPUT.value);
    var warningMessage = document.querySelector('.tel_wrap .warning');
    var successMessage = document.querySelector('.tel_wrap .success');
    new InputEventHandler(warningMessage, successMessage, telValueCheck);
    new ActivateReserveBtn();
});

//이름 인풋 이벤트리스너
NAME_INPUT.addEventListener("change", function(){
    var nameFilter = /^[가-힣]{2,10}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}|[a-zA-Z]{2,20}$/;
    var nameValueCheck = (nameFilter).test(NAME_INPUT.value);
    var warningMessage = document.querySelector('.name_wrap .warning');
    var successMessage = document.querySelector('.name_wrap .success');
    new InputEventHandler(warningMessage, successMessage, nameValueCheck);
    new ActivateReserveBtn();
});


//상품 가격 리스트, 티켓수량 이벤트
function WritePriceList(compiledPrice){
    this.priceListTarget = document.querySelector('.ticket_body');
    this.priceListTemp = document.querySelector('#priceList').innerText;
    this.priceListText = Handlebars.compile(this.priceListTemp);
    this.ticketTypeTarget = document.querySelector('#ticketType');
    this.ticketTypeCountTarget = document.querySelector('#typeTicketCount');

    this.price = compiledPrice;
    this.resultHtml = '';
    this.writePriceList();
    this.plusBtn = document.querySelectorAll('.ico_plus3');
    this.minusBtn = document.querySelectorAll('.ico_minus3');
    this.plusBtnEvent();
    this.minusBtnEvent();
    this.ticketCount = 0;
    this.ticketCountValue = document.querySelectorAll('.count_control_input');
    this.totalTicketCountTarget = document.querySelector('#totalCount');
    this.ticketTypeCountHtml = document.querySelector('#ticketTypeCount').innerHTML;
    
}
WritePriceList.prototype = {
    writePriceList : function(){
        for(i=0; i<this.price.length; i++){
            var priceListHtml = this.priceListText(this.price[i]);
            if(this.price[i].discountRate === 0){
                priceListHtml = priceListHtml.replace("{discountPrice}", this.price[i].price+"원");
            } else {
                priceListHtml = priceListHtml.replace("{discountPrice}", this.price[i].price+"원 ("+this.price[i].discountRate+"% 할인가)");
            }
            this.resultHtml += priceListHtml;
        }
        this.priceListTarget.innerHTML = this.resultHtml;
    },
    //티켓수 추가 이벤트
    plusBtnEvent: function() {
        for (i = 0; i<this.plusBtn.length; i++) {
            
            this.plusBtn[i].addEventListener('click', function(){
                event.preventDefault();
                var ticketCountBox = event.target.previousElementSibling;
                var minusTarget = event.target.previousElementSibling.previousElementSibling;
                var totalPrice = event.target.parentElement.nextElementSibling.firstElementChild;
                var korTicketType = event.target.parentElement.parentElement.nextElementSibling.firstElementChild.firstElementChild.innerText;
                var individualPrice = parseInt(event.target.getAttribute("data-price"));
                var totalPriceWrap = event.target.parentElement.nextElementSibling;
                var ticketCount = parseInt(ticketCountBox.getAttribute("value"));
                
                var currentTicketCount = ticketCount + 1;
                ticketCountBox.setAttribute("value", currentTicketCount);
                
               totalPrice.innerText = individualPrice * currentTicketCount;
                
                if(currentTicketCount != 0){
                    ticketCountBox.setAttribute("class", "count_control_input");
                    minusTarget.setAttribute("class", "btn_plus_minus spr_book2 ico_minus3");
                    totalPriceWrap.setAttribute("class", "individual_price on_color");
                } 
                
                this.writeTotalTicketCount();
                new ActivateReserveBtn();
            }.bind(this));
        } 
    },
    //티켓수 감소 이벤트
    minusBtnEvent: function() {
        for (i = 0; i<this.minusBtn.length; i++) {
            this.minusBtn[i].addEventListener('click', function () {
                event.preventDefault();
                var ticketCountBox = event.target.nextElementSibling;
                var totalPriceWrap = event.target.parentElement.nextElementSibling;
                var plusTarget = event.target.nextElementSibling.nextElementSibling;
                var totalPrice = event.target.parentElement.nextElementSibling.firstElementChild;
                var individualPrice = parseInt(event.target.getAttribute("data-price"));
                var ticketCount = parseInt(event.target.nextElementSibling.getAttribute("value"));

                var currentTicketCount = ticketCount - 1;
                    if(currentTicketCount <= 0){
                        currentTicketCount = 0;
                        ticketCountBox.setAttribute("class", "count_control_input disabled");
                        event.target.setAttribute("class", "btn_plus_minus spr_book2 ico_minus3 disabled");
                        totalPriceWrap.setAttribute("class", "individual_price");
                    }
                ticketCountBox.setAttribute("value", currentTicketCount);

                totalPrice.innerText = individualPrice * currentTicketCount;

                event.target.nextElementSibling.setAttribute("value", currentTicketCount);
                this.writeTotalTicketCount();
                new ActivateReserveBtn();
            }.bind(this));
        }
    },
    getTotalTicketCount : function(){
        var totalCount = 0;
        for(i=0; i<this.ticketCountValue.length; i++){
            totalCount += parseInt(this.ticketCountValue[i].getAttribute("value"));
        }
        return totalCount;
    },
    writeTotalTicketCount : function(){
        var totalTicketCount = this.getTotalTicketCount();
        this.totalTicketCountTarget.innerText = totalTicketCount;
    }
}

//상품 정보 쓰기
function WriteProductInfo(compiledPrice, displayInfo){
    this.productInfoHtml = document.querySelector('#productInfo').innerText;
    this.price = compiledPrice;
    this.displayInfo = displayInfo;
    this.produtInfoTarget = document.querySelector('.section_store_details');
    this.pricesText = document.querySelector('#prices').innerText;
    this.pricesInfo = this.getPrices();
    this.writeProductInfo();
}
WriteProductInfo.prototype = {
    writeProductInfo : function(){
        var resultHtml = this.productInfoHtml.replace("{placeName}", this.displayInfo[0].placeName)
                                             .replace("{openingHours}", this.displayInfo[0].openingHours)
                                             .replace("{price}", this.pricesInfo);
        this.produtInfoTarget.innerHTML = resultHtml;
    },
    getPrices : function(){
        var resultText = '';
        for(i=0; i<this.price.length; i++){
            if(i < this.price.length-1){
                resultText += this.pricesText.replace("{priceTypeName}", this.price[i].priceTypeName)
                                             .replace("{price}", this.price[i].price) + ",";
            }else if(i=this.price.length-1){
                resultText += this.pricesText.replace("{priceTypeName}", this.price[i].priceTypeName)
                                             .replace("{price}", this.price[i].price);
            }
        }
        return resultText;
    }
    
}

//이미지 쓰기
function WriteImageSection(images, compiledPrice, displayInfo){
    this.imageTarget = document.querySelector('.visual_img');
    this.imageList = document.querySelector('#image').innerText;
    this.images = images;
    this.price = compiledPrice;
    this.displayInfo = displayInfo;
    this.minPrice = this.getMinPrice();
    this.writeImageSection();
}
WriteImageSection.prototype = {
    writeImageSection : function(){
        var resultHtml = '';
        var replacedText = this.imageList.replace("{minPrice}", this.minPrice)
                                         .replace("{productDescription}", this.displayInfo[0].productDescription);
        var mainImageHtml = Handlebars.compile(replacedText);
        
        for(i=0; i<this.images.length; i++){
            if(this.images[i].type == 'ma'){
                resultHtml += mainImageHtml(this.images[i]);
            }
        }
        this.imageTarget.innerHTML = resultHtml;
    },
    getMinPrice: function () {
        var prices = [];
        for (i = 0; i < this.price.length; i++) {
            prices[i] = this.price[i].price;
        }
        minPrice = Math.min.apply(null, prices);
        return minPrice;
    }
}

//priceTypeName 바꾸기
function ProductPriceCompiler(priceData){
    this.priceData = priceData;
    this.compilepriceTypeName();
}
ProductPriceCompiler.prototype = {
    compilepriceTypeName : function(){
        for(i=0; i<this.priceData.length; i++){
            switch(this.priceData[i].priceTypeName){
               case 'A':
                    this.priceData[i].priceTypeName = '성인';
                    break;
                case 'Y':
                    this.priceData[i].priceTypeName = '청소년';
                    break;   
                case 'B':
                    this.priceData[i].priceTypeName = '유아';
                    break;   
                case 'S':
                    this.priceData[i].priceTypeName = '셋트';
                    break;   
                case 'D':
                    this.priceData[i].priceTypeName = '장애인';
                    break;   
                case 'C':
                    this.priceData[i].priceTypeName = '지역주민';
                    break;   
                case 'E':
                    this.priceData[i].priceTypeName = '어얼리버드';
                    break;   
                case 'V':
                    this.priceData[i].priceTypeName = 'VIP';
                    break;   
                case 'R':
                    this.priceData[i].priceTypeName = 'R석';
                    break;   
                case 'B':
                    this.priceData[i].priceTypeName = 'B석';
                    break;   
                case 'S':
                    this.priceData[i].priceTypeName = 'S석';
                    break;   
            }
        }
        
    }
}

//Ajax 통신
function getList(displayInfoId) {
	var oReq = new XMLHttpRequest();
	oReq.onreadystatechange = function(){
		if(oReq.readyState === 4 && oReq.status === 200){		
			var serverData = JSON.parse(this.responseText);				
            var compiledPrice = new ProductPriceCompiler(serverData.productPrice);
            var writeImageSection = new WriteImageSection(serverData.productImages, compiledPrice.priceData, serverData.displayInfo);
            var writeProductInfo = new WriteProductInfo(compiledPrice.priceData, serverData.displayInfo);
            var writePriceList = new WritePriceList(compiledPrice.priceData);
		}
	}
	oReq.open("GET", "/reservations.ajax?"+displayInfoId);
	oReq.send();
}

//뒤로가기 주소 설정
function setGoBack(displayInfoId){
    let goBack = document.querySelector('.btn_back');
    goBack.setAttribute("href", "/detail?"+displayInfoId);
}

//displayInfoId값 가져오기
function getDisplayInfoId(){
    var para = document.location.href.split("?");
    var displayInfoId = para[1];
    return displayInfoId;
}

document.addEventListener("DOMContentLoaded", function(){
    var displayInfoId = getDisplayInfoId();
    new WriteDate();
    getList(displayInfoId);
    setGoBack(displayInfoId);
    new AgreeEvent();
    new CheckBoxAddEventListner();
    
});

