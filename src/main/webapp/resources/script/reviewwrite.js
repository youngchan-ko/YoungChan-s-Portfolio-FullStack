/*
2. 경로 변경하기
6. 컨트롤러 로거 지우기
*/ 


// formData만들어서 ajax로 서버 전송하기
function SendFormData(){
    this.submitTarget = document.querySelector('.bk_btn');
    this.submitEvent();
}
SendFormData.prototype = {
    submitEvent : function(){
        this.submitTarget.addEventListener('click', function(){
            var textLength = document.querySelector('#comment_text').value.length;
            
            if(textLength < 5){
                alert("리뷰 입력을 확인해주세요.");
            }else{
                this.makeFormData();
            }
        }.bind(this))
    },
    makeFormData : function(){
        var formData = new FormData();
        var starPoint = document.querySelector('.star_rank').innerText;
        var reviewText = document.querySelector('#comment_text').value;
        var imageFile = document.querySelector('#reviewImageFileOpenInput').files[0];
        var productId = document.querySelector('#productId').value;
        var reservationInfoId = document.querySelector('#reservationInfoId').value;
        
        formData.append("score", starPoint);
        formData.append("comment", reviewText);
        formData.append("productId", productId);
        formData.append("reservationInfoId", reservationInfoId);
        if(imageFile != null){
            formData.append("reviewImage", imageFile);
        }

        this.sendAjax(formData);
    },
    sendAjax : function(formData){
        var oReq = new XMLHttpRequest();
	    oReq.onreadystatechange = function(){
            if(oReq.readyState === 4 && oReq.status === 200){		
                var serverData = JSON.parse(this.responseText);
                alert("리뷰작성에 성공했습니다.");
                window.location.replace("http://localhost:8080/");
            }
        }
        oReq.open("POST", "/writereview.ajax");
        oReq.send(formData);
    }
}


//File 확장자 검사, add, delete
function CheckFileType(){
    this.fileTarget = document.querySelector('#reviewImageFileOpenInput');
    this.thumListTarget = document.querySelector('.item');
    this.thumImgTarget = document.querySelector('.item_thumb');
    this.cancelTarget = document.querySelector('.spr_book');
    this.fileTargetEvent();
}
CheckFileType.prototype = {
    fileTargetEvent : function(){
        this.fileTarget.addEventListener('change', function(){
            var image = event.target.files[0];
            var checkImageType = this.valideImageType(image.type);
            if(checkImageType){
                this.thumListTarget.style.display = 'inline-block';
                this.thumImgTarget.src = window.URL.createObjectURL(image);
                this.cancelEvent();
            }else{
                alert("이미지 파일의 확장자는 jpg와 png만 가능합니다.");
            }
        }.bind(this))
    },
    valideImageType : function(imageType){
        const result = ([ 'image/jpeg',
                          'image/png',
                          'image/jpg' ].indexOf(imageType) > -1);
        return result;
    },
    cancelEvent : function(){
        this.cancelTarget.addEventListener('click', function(){
            this.thumListTarget.style.display = 'none';
            this.thumImgTarget.src = "";
            this.fileTarget.value = "";
        }.bind(this))
    }
}

//TextArea 이벤트
function TextAreaController(){
    this.textAreaTarget = document.querySelector('#comment_text');
    this.textLengthTarget = document.querySelector('.guide_review').firstElementChild;
    this.placeHolder = document.querySelector('.review_write_info');
    this.textValue = '';
    this.textAreaKeyupEvent();
    this.textAreaBlurEvent();
}
TextAreaController.prototype = {
    textAreaKeyupEvent : function(){
        this.textAreaTarget.addEventListener('keyup', function(){
            var textValue = event.target.value;
            this.textLengthTarget.innerText = textValue.length;
            this.textValue = textValue;
            this.textLimiter();
        }.bind(this))
    },
    textLimiter : function(){
        if(this.textValue.length >= 400){
            alert("리뷰는 5자이상 400자 이하로 작성이 가능합니다.");
        }
    },
    textAreaBlurEvent : function(){
        this.textAreaTarget.addEventListener('blur', function(){
            var textFilter = /^.{5,400}$/;
            var textValueCheck = (textFilter).test(this.textValue);
            if(this.textValue.length == 0 || ""){
                this.placeHolder.style.display = 'block';
            }else if(!textValueCheck){
                alert("리뷰는 5자이상 400자 이하로 작성이 가능합니다.");
            }
        }.bind(this))
    }
    
    
}

//placeHolder 이벤트
function PlaceHolderController(){
    this.placeHolder = document.querySelector('.review_write_info');
    this.placeHolderEvent();
    
}
PlaceHolderController.prototype = {
    placeHolderEvent : function(){
        this.placeHolder.addEventListener('click', function(){
            this.placeHolder.style.display = 'none';
        }.bind(this))
    }
}

//별점 이벤트
function StarPointController(){
    this.starTarget = document.querySelectorAll('.rating_rdo');
    this.numPointTarget = document.querySelector('.star_rank');
    this.checkedController();
    new TextAreaController();
}
StarPointController.prototype = {
    checkedController : function(){
        for(i=0; i<this.starTarget.length; i++){
            this.starTarget[i].addEventListener('click', function(){
                event.preventDefault();
                var starTargetValue = event.target.value;
                this.writeNumPoint(starTargetValue);
                for(j=0; j<this.starTarget.length; j++){
                    if(this.starTarget[j].value <= starTargetValue){
                        this.starTarget[j].className = "rating_rdo checked";
                    }else{
                        this.starTarget[j].className = "rating_rdo";
                    }
                }
            }.bind(this))
        }
    },
    writeNumPoint : function(num){
        if(num === 0){
            this.numPointTarget.className = 'star_rank gray_star';
        }else{
            this.numPointTarget.className = 'star_rank';
        }
        this.numPointTarget.innerText = num;
    }
}

document.addEventListener("DOMContentLoaded", function(){
    new StarPointController();
    new PlaceHolderController();
    new CheckFileType();
    new SendFormData();
});