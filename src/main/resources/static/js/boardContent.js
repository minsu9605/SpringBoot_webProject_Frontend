$("#soldOutBtn").on('click',function (){
    if(confirm("판매완료로 변경하시겠습니까?")) {
        $.ajax({
            type: "get",
            url: "/api/board/soldOut",
            data: {"id": $("#bid").val()},
            success: function (result) {
                if (result == "success") {
                    alert("변경되었습니다.");
                    location.reload();
                } else alert("잠시 후 다시 시도해주세요");
            },
            error: function (request, status, error) {
                alert("code : " + request.status + "\n" + "error : " + error);
            }
        });
    }
});

$("#showLocation").on('click',function (){
    window.open("/board/map_content","map","width=910,height=485,left=350,top=120");
});