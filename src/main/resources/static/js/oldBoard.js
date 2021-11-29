$(function (){

    $('#moveOldBoardPage').hide();

    let startIndex = 0;
    let searchStep = 2;

    readOldNotify(startIndex);

    // 더보기 클릭시
    $('#searchMoreNotify').on('click',function(){
        startIndex += searchStep;
        readOldNotify(startIndex);
    });

    // 더보기 실행함수 **
    function readOldNotify(index){
        $.ajax({
            url: "/api/board/getMyOldBoardList",
            method: "get",
            data: {startIndex: index, searchStep: searchStep},
            success: function (success) {
                const oldListCnt = success.totalCnt;
                let NodeList = "";
                let newNode = ""

                if(success.data.length==0){
                    newNode += "<h4 class='text-center'>모든 알람을 확인하였습니다!</h4><br>";
                    newNode += "<span class='text-center mb-3'>오래된 게시물을 확인하시려면 아래 버튼을 눌러주세요!</span>";
                    NodeList += newNode;
                    $('#moveOldBoardPage').show();
                }
                for(let i = 0; i < success.data.length; i++){
                    newNode = "<div style='display: none;' class='card form-group col-sm-10 mx-auto p-0'>";
                    newNode += "<div class='card-body pt-3'><div class='row px-3 mb-2'>";
                    newNode += "<strong class='d-block text-gray-dark'>"+success.data[i].title+"</strong>";
                    newNode += "<p class='card-text text-danger'>물건을 올린지 1주일이 지났어요! 물건의 가격을 변경해보세요!</p>";
                    newNode += "</div><button type='button' class='btn btn-primary move'>게시물로 이동</button>";
                    newNode += "<input type='hidden' class='board_id' value='" + success.data[i].id + "'></div></div>";
                    NodeList += newNode;
                }
                $(NodeList).appendTo($("#oldList")).slideDown();

                // 더보기 버튼 삭제
                if(startIndex + searchStep > oldListCnt){
                    $('#searchMoreNotify').remove();
                }
                if(startIndex>=2){
                    $('#moveOldBoardPage').show();
                    $('#searchMoreNotify').remove();

                }

            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + error);
            }
        });
    }

    $(document).on('click',".move",function (){
        const id = $(this).siblings('input').val();
        $.ajax({
            url: "/api/board/setAlertReading",
            method: "get",
            data: {"id": id},
            success: function (success) {
                if(success.result=="success"){
                    location.href = '/board/content?id=' + id;
                }else if(success.result=="fail"){
                    alert("잠시 후 다시 시도해주세요.")
                }
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + error);
            }
        });
    });

});