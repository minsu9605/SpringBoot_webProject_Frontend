const id = $("#id");
const username = $("#username");
const nickname = $("#nickname");
let nicknameCheck = 1;
let usernameCheck = 1;

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