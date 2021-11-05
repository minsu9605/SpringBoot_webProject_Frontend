/********************************/
let isEnd = false;

$(function(){
    $(window).scroll(function(){
        let $window = $(this);
        let scrollTop = $window.scrollTop();
        let windowHeight = $window.height();
        let documentHeight = $(document).height();

        console.log("documentHeight:" + documentHeight + " | scrollTop:" + scrollTop + " | windowHeight: " + windowHeight );

        // scrollbar의 thumb가 바닥 전 30px까지 도달 하면 리스트를 가져온다.
        if( scrollTop + windowHeight + 30 > documentHeight ){
            fetchList();
        }
    })
    fetchList();
})

let fetchList = function(){
    if(isEnd == true)
        return;

    // 방명록 리스트를 가져올 때 시작 번호
    // renderList 함수에서 html 코드를 보면 <li> 태그에 data-no 속성이 있는 것을 알 수 있다.
    // ajax에서는 data- 속성의 값을 가져오기 위해 data() 함수를 제공.
    $.ajax({
        url:"/api/board/getMyOldBoardList" ,
        type: "GET",
        dataType: "json",
        success: function(result){
            // 컨트롤러에서 가져온 방명록 리스트는 result.data에 담겨오도록 했다.
            // 남은 데이터가 5개 이하일 경우 무한 스크롤 종료
            let length = result.data.length;
            if( length < 5 ){
                isEnd = true;
            }
            $.each(result.data, function(index, vo){
                renderList(false, vo);
            })
        }
    });
}

let renderList = function(mode, vo){
    // 리스트 html을 정의
    let html =
        "<div class='card border-primary mb-3' style='max-width: 18rem;'>"+
        "<div class='''card-body''>" +
        "<h5 class='card-title'>"+vo.title+"</h5>" +
        "<p class='card-text text-danger'>물건을 올린지 1주일이 지났어요! 물건의 가격을 변경해보세요!</p>" +
        "<a href=/board/content?id='"+vo.id+" class='btn btn-primary'>게시물로 이동</a>" +
        "</div>" +
        "</div>"

    /*"<li data-no='"+ vo.no +"'>" +
    "<strong>"+ vo.name +"</strong>" +
    "<p>"+ vo.content.replace(/\n/gi, "<br>") +"</p>" +
    "<strong></strong>" +
    "<a href='#' data-no='"+ vo.no +"'>삭제</a>" +
    "</li>"*/

    if( mode ){
        $(".oldBoardMessage").prepend(html);
    }
    else{
        $(".oldBoardMessage").append(html);
    }
}
