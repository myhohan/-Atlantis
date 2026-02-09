/* signup.js */

// 다음 주소 API
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = '';
            if (data.userSelectedType === 'R') addr = data.roadAddress;
            else addr = data.jibunAddress;

            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

document.getElementById("searchAddress").addEventListener("click", execDaumPostcode);

// 유효성 검사 객체
const checkObj = {
    "memberEmail": false,
    "authKey": false,
    "memberPw": false,
    "memberPwConfirm": false,
    "memberNickname": false,
    "memberTel": false
};

// 1. 이메일 유효성 검사
const memberEmail = document.getElementById("memberEmail");
const emailMessage = document.getElementById("emailMessage");

memberEmail.addEventListener("input", function(){
    const inputEmail = this.value.trim();
    checkObj.memberEmail = false;
    
    if(inputEmail.length === 0){
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";
        emailMessage.classList.remove("confirm", "error");
        return;
    }

    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(!regExp.test(inputEmail)){
        emailMessage.innerText = "알맞은 이메일 형식으로 작성해주세요.";
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");
        return;
    }

    // 중복 검사 (GET 방식)
    fetch("/member/checkEmail?memberEmail=" + inputEmail)
    .then(resp => resp.text())
    .then(count => {
        if(count == 1){
            emailMessage.innerText = "이미 사용중인 이메일입니다.";
            emailMessage.classList.add("error");
            emailMessage.classList.remove("confirm");
        } else {
            emailMessage.innerText = "사용 가능한 이메일입니다.";
            emailMessage.classList.add("confirm");
            emailMessage.classList.remove("error");
            checkObj.memberEmail = true;
        }
    })
    .catch(err => console.log(err));
});


// 2. 인증번호 발송 (중복 코드 통합 및 수정 완료)
const sendAuthKeyBtn = document.getElementById("sendAuthKeyBtn");
const authKeyMessage = document.getElementById("authKeyMessage");
let authTimer;

sendAuthKeyBtn.addEventListener("click", function(e){ // (1) 'e' 파라미터 추가
    
    e.preventDefault(); // (2) ★핵심★ 폼 제출(새로고침) 막기!

    // 유효한 이메일이 아니면 중단
    if(!checkObj.memberEmail){
        alert("유효한 이메일을 먼저 작성해주세요.");
        return;
    }

    // 인증번호 발송 요청
    fetch("/email/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ "email": memberEmail.value })
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            console.log("인증번호 발송 성공");
            alert("인증번호가 발송되었습니다. 이메일을 확인해주세요.");
            startTimer(); // 타이머 시작
        } else {
            alert("인증번호 발송 실패. 다시 시도해주세요.");
        }
    })
    .catch(err => {
        console.log("에러 발생:", err);
        alert("이메일 발송 중 문제가 발생했습니다.");
    });

});


// 타이머 함수
function startTimer(){
    authKeyMessage.innerText = "05:00";
    authKeyMessage.classList.remove("confirm", "error");
    
    clearInterval(authTimer); // 기존 타이머 초기화

    let min = 4, sec = 59; // 5분
    
    authTimer = setInterval(function(){
        authKeyMessage.innerText = "0" + min + ":" + (sec < 10 ? "0" + sec : sec);
        
        if(min == 0 && sec == 0){
            clearInterval(authTimer);
            checkObj.authKey = false;
            authKeyMessage.classList.add("error");
            authKeyMessage.innerText = "인증시간 만료";
            return;
        }
        
        if(sec == 0){
            sec = 60;
            min--;
        }
        sec--;
    }, 1000);
}


// 3. 인증번호 확인
const checkAuthKeyBtn = document.getElementById("checkAuthKeyBtn");
const authKey = document.getElementById("authKey");

checkAuthKeyBtn.addEventListener("click", function(e){ // (1) 'e' 파라미터 추가
    
    e.preventDefault(); // (2) ★핵심★ 폼 제출(새로고침) 막기!

    if(authKey.value.trim().length < 6){
        alert("인증번호를 입력해주세요.");
        return;
    }

    const obj = {
        "email": memberEmail.value,
        "authKey": authKey.value
    };

    fetch("/email/checkAuthKey", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            clearInterval(authTimer);
            authKeyMessage.innerText = "인증되었습니다.";
            authKeyMessage.classList.add("confirm");
            authKeyMessage.classList.remove("error");
            checkObj.authKey = true;
            
            // 이메일 수정 막기
            memberEmail.readOnly = true;
        } else {
            alert("인증번호가 일치하지 않습니다.");
            checkObj.authKey = false;
        }
    })
    .catch(err => console.log(err));
});


// 4. 비밀번호 & 확인 검사
const memberPw = document.getElementById("memberPw");
const memberPwConfirm = document.getElementById("memberPwConfirm");
const pwMessage = document.getElementById("pwMessage");

memberPw.addEventListener("input", function(){
    const inputPw = this.value.trim();
    checkObj.memberPw = false;

    if(inputPw.length === 0){
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자";
        pwMessage.classList.remove("confirm", "error");
        return;
    }

    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
    if(!regExp.test(inputPw)){
        pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        return;
    }

    pwMessage.innerText = "유효한 비밀번호 형식입니다.";
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPw = true;

    if(memberPwConfirm.value.length > 0) checkPw();
});

memberPwConfirm.addEventListener("input", checkPw);

function checkPw(){
    if(memberPw.value === memberPwConfirm.value){
        pwMessage.innerText = "비밀번호가 일치합니다.";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkObj.memberPwConfirm = true;
    } else {
        pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPwConfirm = false;
    }
}


// 5. 닉네임 검사
const memberNickname = document.getElementById("memberNickname");
const nickMessage = document.getElementById("nickMessage");

memberNickname.addEventListener("input", function(){
    const inputName = this.value.trim();
    checkObj.memberNickname = false;

    if(inputName.length === 0){
        nickMessage.innerText = "한글,영어,숫자로만 2~10글자";
        nickMessage.classList.remove("confirm", "error");
        return;
    }

    const regExp = /^[가-힣\w\d]{2,10}$/;
    if(!regExp.test(inputName)){
        nickMessage.innerText = "유효하지 않은 형식입니다.";
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        return;
    }

    fetch("/member/checkNickname?memberNickname=" + inputName)
    .then(resp => resp.text())
    .then(count => {
        if(count == 1){
            nickMessage.innerText = "이미 존재하는 닉네임입니다.";
            nickMessage.classList.add("error");
            nickMessage.classList.remove("confirm");
        } else {
            nickMessage.innerText = "사용 가능한 닉네임입니다.";
            nickMessage.classList.add("confirm");
            nickMessage.classList.remove("error");
            checkObj.memberNickname = true;
        }
    })
    .catch(err => console.log(err));
});


// 6. 전화번호 검사
const memberTel = document.getElementById("memberTel");
const telMessage = document.getElementById("telMessage");

memberTel.addEventListener("input", function(){
    const inputTel = this.value.trim();
    checkObj.memberTel = false;

    if(inputTel.length === 0){
        telMessage.innerText = "전화번호를 입력해주세요(- 제외)";
        telMessage.classList.remove("confirm", "error");
        return;
    }

    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
    if(!regExp.test(inputTel)){
        telMessage.innerText = "유효하지 않은 전화번호 형식입니다.";
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
        return;
    }

    telMessage.innerText = "유효한 전화번호입니다.";
    telMessage.classList.add("confirm");
    telMessage.classList.remove("error");
    checkObj.memberTel = true;
});


// 최종 제출 전 유효성 검사
document.getElementById("signUpForm").addEventListener("submit", function(e){
    for(let key in checkObj){
        if(!checkObj[key]){
            let str;
            switch(key){
                case "memberEmail": str="이메일이"; break;
                case "authKey": str="이메일 인증이"; break;
                case "memberPw": str="비밀번호가"; break;
                case "memberPwConfirm": str="비밀번호 확인이"; break;
                case "memberNickname": str="닉네임이"; break;
                case "memberTel": str="전화번호가"; break;
            }
            alert(str + " 유효하지 않습니다.");
            e.preventDefault(); // 제출 막기
            
            if(key === 'authKey') document.getElementById('authKey').focus();
            else document.getElementById(key).focus();
            
            return;
        }
    }
});