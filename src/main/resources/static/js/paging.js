/*
let totalDataSize = $("#memberList").length;
let listSize =10;
let bottomSize = 10;
let cursor;
alert(totalDataSize);
const firstBottomNumber =
/!*totalDataSize = 총 데이터수
    bottomSize = 하단의 크기
    cursor = 현재나의 페이지
    listSize = 한 화면에 보여줄 수*!/
/!*$(document).ready(function () {
    $.ajax({
        method : "get",
        url : "/admin",
        data : {"memberList" : memberList},
        datatype : "JSON",
        success: function (data) {
            totalDataSize = data.result.length;
            // paging(totalDataSize, listSize, bottomSize, cursor);
            alert("성공 : " + totalDataSize + "입니다");
        },
        error : function(){
            alert("실패");
        }
    });

});*!/

function paging(totalDataSize, listSize,  bottomSize, cursor){
    let totalPageSize = Math.ceil(totalDataSize/listSize);
    let firstBottomNumber = cursor - (cursor % bottomSize) + 1;
    let lastBottomNumber = cursor - (cursor % bottomSize) + bottomSize;
    if(lastBottomNumber>totalPageSize){
        lastBottomNumber = totalPageSize
    }
    return {
        totalDataSize,
        listSize,
        bottomSize,
        cursor,
        totalPageSize,
        firstBottomNumber,
        lastBottomNumber
    }
}
*/
