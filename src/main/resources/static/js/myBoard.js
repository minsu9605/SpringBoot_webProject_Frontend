$(function () {
    getList();
});

let grid;

function getList(){
    const datasource = {
        api: {
            readData: {
                url: '/api/board/myBoard/table',
                method: 'GET',
                initParams: {searchType: $("#searchType").val(), keyword: $("#keyword").val()}
            }
        }
    }

    grid = new tui.Grid({
        el: document.getElementById('recruitGrid'),
        scrollX: false,
        scrollY: false,

        data : datasource,

        columns: [
            {header: '번호', name: 'id', width: 'auto',align: 'center'},
            {header: '제목', name: 'title', width: 700},
            {header: '작성자', name: 'writer', width: 'auto', align: 'center'},
            {header: '작성일', name: 'createdDate', align: 'center', width: 120},
            {header: '조회수', name: 'hit', width: 'auto', align: 'center'}
        ],
        pageOptions: {
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
}

/*검색 버튼*/
$("#searchButton").on('click', function () {
    if ($("#keyword").val() === "") {
        alert("검색어를 입력하세요");
    }else{
        grid.destroy();
        getList();
    }
});

