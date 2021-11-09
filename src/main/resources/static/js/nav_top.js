$(function () {
    $("#oldBoardButton").on('click',function () {    // [1]
        $(".popup_bg").show();
        $(".popup").show(700);
    });

    $(".popup_bg, .close").on('click',function () {    // [2]
        $(".popup_bg").hide();
        $(".popup").hide(200);
    });

    $.ajax({
        url : "/api/board/getMyOldBoardCnt",
        method : 'get',
        success : function(success){
            let totalCnt = success.totalCnt;
            $("#oldBoardCnt").text(totalCnt);
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }

    });
});

