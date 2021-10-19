function pwCheckAll(){
    if(original_Pw.val()=="") {
        alert("기존 패스워드를 입력해주세요!. 필수항목입니다.");
        original_Pw.focus();
        return false;
    } else if (original_PwCheck == 0){
        alert("기존 패스워드를 확인해 주세요!");
        return false;
    } else if (!confirm("패스워드를 수정하시겠습니까?")){
        return false;
    } alert("수정되었습니다. 다시로그인해주세요");
    return true;
}

let original_PwCheck = 0;
const original_Pw = $("#original_Pw");

function originalPwCheck(){

    $.ajax({
        type :"post",
        url :"/api/pwCheck",
        data : {"original_Pw" : original_Pw.val()},
        dataType : "JSON",
        success : function(result){
            if(result.result == "1"){
                original_PwCheck = 1;
            }else{
                original_PwCheck = 0;
            }
        },
        error : function(){
            alert("ajax 실행 실패");
        }
    });
}