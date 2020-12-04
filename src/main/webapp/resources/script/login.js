
//이메일 유효성검사
//이메일 인풋 이벤트리스너
function SubmitEvent(){
    this.loginBtnTarget = document.querySelector('.login_btn');
    this.emailInputTarget = document.querySelector('.login_input');
    this.formTarget =document.querySelector('#form1');
    this.clickEvent();
}
SubmitEvent.prototype = {
    clickEvent : function(){
        this.loginBtnTarget.addEventListener('click', function(){
            var emailFilter = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            var emailValueCheck = (emailFilter).test(this.emailInputTarget.value);
            if(emailValueCheck){
                this.formTarget.submit();
            }else{
                event.preventDefault();
                alert("이메일 형식을 확인해주세요.");
            }
        }.bind(this))
    }
}

//링크버튼 비활성화
function BlockLink(target){
    this.linkItem = document.querySelectorAll('.lnk_item');
    this.naverItem = document.querySelectorAll('.lnk_naver');
    this.item = document.querySelectorAll('.item');
    this.addEvent(this.linkItem);
    this.addEvent(this.naverItem);
    this.addEvent(this.item);
}
BlockLink.prototype = {
    addEvent : function(target){
        for(i=0; i<target.length; i++){
            target[i].addEventListener('click', function(){
                event.preventDefault();
            })
        }
    }
}

document.addEventListener("DOMContentLoaded", function(){
    new BlockLink();
    new SubmitEvent();
});