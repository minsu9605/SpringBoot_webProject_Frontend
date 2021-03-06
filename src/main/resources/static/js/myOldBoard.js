$(function () {
    getList();
});

let grid;

function getList(){
    const datasource = {
        api: {
            readData: {
                url: '/api/board/myOldBoard/table',
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
            {header: '상태', name: 'status', width: 60, align: 'center',renderer : {type: ColumnConverter}},
            {header: '제목', name: 'title', width: 600},
            {header: '작성자', name: 'writer', width: 'auto', align: 'center'},
            {header: '카테고리', name: 'categoryName', width: 75,align: 'center'},
            {header: '작성일', name: 'createdDate', align: 'center', width: 125},
            {header: '조회수', name: 'hit', width: 60, align: 'center'}
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

/*문자 변환 함수*/
function converter(value){
    let result;
    if(value=="sell"){
        result=value.replace(/sell/gi,"[판매중]");
    }else if(value=="soldOut"){
        result=value.replace(/soldOut/gi,"[판매완료]");
    }else
        result=value.replace(/old/gi,"[판매중]");

    return result;
}

class ColumnConverter {
    constructor(props) {
        const el = document.createElement('div');

        this.el = el;
        this.render(props);
    }
    render(props) {
        this.el.innerText = converter(props.formattedValue);
    }ㅋ
    getElement() {
        return this.el;
    }
}

