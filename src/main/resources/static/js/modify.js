const id = $("#id");
const username = $("#username");
const nickname = $("#nickname");
let nicknameCheck = 1;
let usernameCheck = 1;

function withdrawalSubmit() {
    if(!confirm("탈퇴처리 하시겠습니까?")){
        return false;
    }else {
        $.ajax({
            type: "delete",
            url: "/admin/withdrawal",
            data: {"id": id.val()},
            success: function () {
                    alert("회원 탈퇴처리가 완료되었습니다.");
            },
            error: function (request, status, error) {
                alert("ajax 실행 실패");
                alert("code:" + request.status + "\n" + "error :" + error);

            }
        });
    }

}

function checkAgeModify() {
    const birth_check_msg = $("#birth_check_msg");
    if ($("#year").val() == "") {
        birth_check_msg.html("태어난 년도를 선택해주세요");
        birth_check_msg.css("color", "red");
        return false;
    } else if ($("#month").val() == "") {
        birth_check_msg.html("태어난 월을 선택해주세요");
        birth_check_msg.css("color", "red");
        return false;
    } else if ($("#day").val() == "") {
        birth_check_msg.html("태어난 날짜(일)를 선택해주세요");
        birth_check_msg.css("color", "red");
        return false;
    } else {
        birth_check_msg.html("");
    }
    return true;
}


function modifyCheckAll(){
    if(username.val()==""){
        alert("아이디를 입력해주세요!. 필수항목입니다.");
        username.focus();
        return false;
    }else if(nickname.val()==""){
        alert("아이디를 입력해주세요!. 필수항목입니다.");
        nickname.focus();
        return false;
    } else if (usernameCheck == 0){
        alert("아이디를 확인해 주세요!");
        return false;
    } else if (nicknameCheck == 0){
        alert("닉네임을 확인해 주세요!");
        return false;
    } else if(!blankModify()){
        return false;
    } else if (!confirm("정보를 수정하시겠습니까?")){
        return false;
    } alert("수정되었습니다.");
    return true;
}

function nicknameModify(){

    $.ajax({
        type :"post",
        url :"/nicknameModify",
        data : {"id" : id.val(), "nickname" : nickname.val()},
        dataType : "JSON",
        success : function(result){
            console.log("리턴 결과:" + result.result);
            if(result.result == "0"){
                $('.nickname_ok').css({"display" : "inline-block","color" : "blue"});
                $('.nickname_already').css("display", "none");
                nicknameCheck = 1;
            }else{
                $('.nickname_ok').css("display","none");
                $('.nickname_already').css({"display" :"inline-block", "color" : "red"});
                nicknameCheck = 0;
            }
        },
        error : function(){
            alert("ajax 실행 실패");
        }
    });
}

function usernameModify(){

    $.ajax({
        type :"post",
        url :"/usernameModify",
        data : {"id" : id.val(), "username" : username.val()},
        dataType : "JSON",
        success : function(result){
            if(result.result == "0"){
                $('.username_ok').css({"display" : "inline-block","color" : "blue"});
                $('.username_already').css("display", "none");
                usernameCheck = 1;
            }else{
                $('.username_ok').css("display","none");
                $('.username_already').css({"display" :"inline-block", "color" : "red"});
                usernameCheck = 0;
            }
        },
        error : function(){
            alert("ajax 실행 실패");
        }
    });
}

function blankModify() {
    if($("#gender").val() == "") {
        alert("성별을 입력해주세요!. 필수항목입니다.");
        $("#gender").focus();
        return false;
    } else if($("#role").val() == "") {
        alert("권한을 입력해주세요!. 필수항목입니다.");
        $("#role").focus();
        return false;
    }
    return true;
}