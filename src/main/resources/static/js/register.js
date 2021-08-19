function checkAll() {
    if(usernameOverlapCheck == 0) {
        alert("아이디 중복체크를 해주세요");
        return false;
    }else if(!pwSame()) {
        return false;
    }else if(nicknameOverlapCheck == 0) {
        alert("닉네임 중복체크를 해주세요");
        return false;
    }else if(!checkAge()) {
        return false;
    }else if(!checkGender()){
        return false;
    }else if(!confirm("일부 정보는 수정이 불가합니다 \n회원가입을 진행하시겠습니까?")){
        return false
    }else {
        alert($("#username").val() + " 님 회원가입이 완료되었습니다.")
    }
    return true;
}

let usernameOverlapCheck = 0;
let nicknameOverlapCheck = 0;

function usernameCheck(){
    const username = $("#username").val();
    if(username == "") {
        alert("아이디를 입력해주세요!. 필수항목입니다.");
        $("#username").focus();
        return false;
    }
    $.ajax({
        type :"post",
        url :"/usernameOverlap",
        data : {"username" : username},
        dataType : "JSON",
        success : function(result){
            if(result.result == "0"){
                if(confirm("이 아이디는 사용 가능합니다. \n사용하시겠습니까?")){
                    usernameOverlapCheck = 1;
                    $("#username").attr("readonly", true);
                    $("#usernameOverlay").attr("disabled", true);
                    $("#usernameOverlay").css("display", "none");
                    $("#resetUsername").attr("disabled",false);
                    $("#resetUsername").css("display","inline-block");
                }
                return false;
            }else{
                alert("이미 사용중인 아이디입니다.");
                $("#username").focus();
            }
        },
        error : function(){
            alert("ajax 실행 실패");
        }
    });
}

function pwSame() {
    const password = $("#password").val();
    const password_check = $("#password_check").val();
    const pw_check_msg = $("#pw_check_msg");
    //패스워드 입력되었는지 확인하기
    if(password == "") {
        alert("패스워드를 입력해주세요!. 필수항목입니다.");
        $("#password").focus();
        return false;
    }
    //패스워드 조건 확인
    if(password.length < 6) {
        alert('패스워드는 6글자 이상이어야 합니다.');
        $("#password").focus();
        return false;
    }
    //패스워드 일치 확인
    if(password !="" && password_check !="") {
        if(password == password_check){
            pw_check_msg.html("비밀번호가 일치합니다.");
            pw_check_msg.css("color","blue");
        }else {
            pw_check_msg.html("비밀번호가 다릅니다. 다시 확인해 주세요.");
            pw_check_msg.css("color","red");
            return false;
        }
    }
    return true;
}

function nicknameCheck(){
    const nickname = $("#nickname").val();
    if(nickname == "") {
        alert("닉네임을 입력해주세요!. 필수항목입니다.");
        $("#nickname").focus();
        return false;
    }
    $.ajax({
        type :"post",
        url :"/nicknameOverlap",
        data : {"nickname" : nickname},
        dataType : "JSON",
        success : function(result){
            if(result.result == "0"){
                if(confirm("이 이름은 사용 가능합니다. \n사용하시겠습니까?")){
                    nicknameOverlapCheck = 1;
                    $("#nickname").attr("readonly", true);
                    $("#nicknameOverlay").attr("disabled", true);
                    $("#nicknameOverlay").css("display", "none");
                    $("#resetNickname").attr("disabled",false);
                    $("#resetNickname").css("display","inline-block");
                }
                return false;

            }else{
                alert("이미 사용중인 이름입니다.");
                $("#nickname").focus();
            }
        },
        error : function(){
            alert("ajax 실행 실패");
        }
    });
}

function checkAge() {
    const birth_check_msg = $("#birth_check_msg");
    if ($("#year").val() == "") {
        birth_check_msg.html("태어난 년도를 선택해주세요");
        birth_check_msg.css("color","red");
        return false;
    } else if($("#month").val() == ""){
        birth_check_msg.html("태어난 월을 선택해주세요");
        birth_check_msg.css("color","red");
        return false;
    } else if($("#day").val() == "") {
        birth_check_msg.html("태어난 날짜(일)를 선택해주세요");
        birth_check_msg.css("color","red");
        return false;
    } else{
        birth_check_msg.html("");
    }
    return true;
}

function checkGender() {
    if($("#gender").val() == "") {
        alert("성별을 입력해주세요!. 필수항목입니다.");
        $("#gender").focus();
        return false;
    }
    return true;
}

function reUsername(){
    usernameOverlapCheck = 0;
    $("#username").attr("readonly", false).focus();
    $("#username").val("");
    $("#usernameOverlay").attr("disabled", false);
    $("#usernameOverlay").css("display", "inline-block");
    $("#resetUsername").attr("disabled",true);
    $("#resetUsername").css("display","none");
}

function reNickname(){
    nicknameOverlapCheck = 0;
    $("#nickname").attr("readonly", false).focus();
    $("#nickname").val("");
    $("#nicknameOverlay").attr("disabled", false);
    $("#nicknameOverlay").css("display", "inline-block");
    $("#resetNickname").attr("disabled",true);
    $("#resetNickname").css("display","none");
}