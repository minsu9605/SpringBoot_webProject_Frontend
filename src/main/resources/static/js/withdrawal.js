function withdrawal() {
    if (!confirm("정말로 탈퇴 하시겠습니까?")) {
        return false
    } else if (!confirm("게시글과 댓글 등이 삭제되며 이후 취소 처리가 불가 합니다. 탈퇴 하시겠습니까?")) {
        return false
    }
    reConverter();
    alert("회원탈퇴가 완료되었습니다.")
}

function converter(value) {
    let result;
    if (value == "Male") {
        result = value.replace(/Male/gi, "남자");
    } else if (value == "Female") {
        result = value.replace(/Female/gi, "여자");
    }
    $("#gender").val(result);
}

$(function (){
   converter($("#gender").val());
});

function reConverter() {
    let result;
    if ($("#gender").val() == "남자"){
        result = $("#gender").val().replace(/남자/gi, "Male");
    }else if($("#gender").val() == "여자"){
        result = $("#gender").val().replace(/여자/gi, "Female");
    }
    $("#gender").val(result);
}