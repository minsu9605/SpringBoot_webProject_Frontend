const categoryId = $("#categoryId").val();

let grid;

$(function () {
    grid = new tui.Grid({
        el: document.getElementById('recruitGrid'),
        scrollX: false,
        scrollY: false,

        // data : dataSource,

        columns: [
            {header: '번호', name: 'id', width: 'auto'},
            {header: '제목', name: 'title', width: 700, filter: {type: 'text'}},
            {header: '작성자', name: 'writer', align: 'center', filter: {type: 'text'}},
            {header: '카테고리', name: 'categoryName', align: 'center'},
            {header: '작성일', name: 'createdDate', align: 'center', width: 120},
            {header: '조회수', name: 'hit', align: 'center'}
        ],
        pageOptions: {
            useClient: true,
            perPage: 5
        }
    });

    grid.on('click', ev => {
        const rowKey = ev.rowKey;
        const id = grid.getFormattedValue(rowKey, "id");

        if (ev.columnName === 'title' && id!=null) {
            location.href = '/board/content?id=' + id;
        }
    });

    getBoardList();
});

/*게시글 목록*/
function getBoardList() {
    $.ajax({
        method: 'get',
        url: '/board/list/table',
        data: {"categoryId": categoryId},
        success: function (data) {
            const dataLength = data.list.length;
            if (dataLength > 0) {
                $("#totalCount").text(dataLength);
                grid.resetData(data.list);
            } else if (dataLength == 0) {
                alert("게시물이 없습니다.");
            }
        },
        error: function (request, statue, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    })
}

/*검색 버튼*/
$("#searchButton").on('click', function () {
    const searchType = $("#searchType").val();
    const keyword = $("#keyword").val();
    let state = {};
    let arr = [];

    /*검색필터 초기화*/
    grid.unfilter('title');
    grid.unfilter('writer');

    if (keyword === "") {
        alert("검색어를 입력하세요");
    } else {
        if (searchType === 'title' || searchType === 'writer') {
            state.code = 'contain';
            state.value = keyword;
        }
        arr.push(state);
        grid.filter(searchType, arr);
        $("#totalCountDiv").hide();

    }
});
