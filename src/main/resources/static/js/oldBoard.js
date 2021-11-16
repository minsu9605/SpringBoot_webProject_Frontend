$(function (){

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
                for(let i = 0; i < success.data.length; i++){
                    let newNode = "<div style='display: none;' class='card form-group col-sm-10 mx-auto p-0'>";
                    newNode += "<div class='card-body pt-3'><div class='row px-3 mb-2'>";
                    newNode += "<strong class='d-block text-gray-dark'>"+success.data[i].title+"</strong>";
                    newNode += "<p class='card-text text-danger'>물건을 올린지 1주일이 지났어요! 물건의 가격을 변경해보세요!</p>";
                    newNode += "</div><a href='/board/content?id= "+ success.data[i].id +"' class='btn btn-primary'>게시물로 이동</a></div></div>";
                    NodeList += newNode;
                }
                $(NodeList).appendTo($("#oldList")).slideDown();

                // 더보기 버튼 삭제
                if(startIndex + searchStep > oldListCnt){
                    $('#searchMoreNotify').remove();
                }
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + error);
            }
        });
    }
});