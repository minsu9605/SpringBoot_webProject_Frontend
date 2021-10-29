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

/*거래장소 보기*/
$("#showLocation").on('click',function (){
    window.open("/board/map_content","map","width=910,height=485,left=350,top=120");
});

$(function (){
    $("#contentPrice").text( $("#contentPrice").text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
    $("#contentPrice").text( $("#contentPrice").text()+'원');
});


$(function getImageByTagName(){
    const dbValue = document.getElementById('contentMain');
    const tag = dbValue.getElementsByTagName('img');
    const tagLength = tag.length;
    if(tag.length>=1){
        for(let i=0; i<tagLength; i++){
            $(".contentImg").append(tag[0]);
        }
    }else if(tag.length==0){
        $(".contentImg").append('<img src="/images/no_image.png">');

    }

});




$(function(){
    $('.contentImg').slick({
        prevArrow:'.arrow_prev',
        nextArrow:'.arrow_next'
    });
});
