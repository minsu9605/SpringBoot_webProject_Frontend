const categoryId = $("#categoryId").val();
let grid;

$(function (){
    grid = new tui.Grid( {
        el: document.getElementById('recruitGrid'),
        scrollX:false,
        scrollY:false,

        columns: [
            {
                header : '번호',
                name : 'id',
                width : 'auto'
            },
            {
                header : '제목',
                name : 'title',
                width : 700
            },
            {
                header : '작성자',
                name : 'writer',
                align : 'center'
            },
            {
                header : '카테고리',
                name : 'categoryName',
                align : 'center'
            },
            {
                header : '작성일',
                name : 'createdDate',
                align : 'center',
                width : 120
            },
            {
                header : '조회수',
                name : 'hit',
                align : 'center',
            }
        ]
    });

    // td[data-column-name=title]

    grid.on('click', ev=>{
        if(ev.columnName === 'title'){
            console.log(ev);
            const rowKey = ev.rowKey;
            const id = grid.getFormattedValue(rowKey, "id");

            if(id !==  undefined){
                location.href = '/board/content?id=' + id;
            }
        }
    });
});

$(function getBoardList(){
    $.ajax({
        method:'get',
        url : '/board/list/table?category=' + categoryId,
        data : {"categoryId" : categoryId},
        success : function (data){
            if(data.list.length>0){
                grid.resetData(data.list);
            }else if (data.list.length == 0){
                alert("게시물이 없습니다.");
            }
        },
        error : function (request, statue, error){
            alert("code: " + request.status + "\n"  + "error: " + error);
        }
    })
});