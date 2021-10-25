$(function () {
    getList();
});

let grid;

function getList() {
    const datasource = {
        api: {
            readData: {
                url: '/api/admin/list/table',
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
            {header: 'NO', name: 'id', width: 'auto',align: 'center'},
            {header: '아이디', name: 'username', width: 300, align: 'center'},
            {header: '이름(닉네임)', name: 'nickname', width: 120,align: 'center'},
            {header: '생년월일', name: 'birth', width: 120,align: 'center'},
            {header: '나이', name: 'age', align: 'center', width: 100},
            {header: '성별', name: 'gender', width: 'auto',align: 'center',renderer : {type: ColumnConverter}},
            {header: '권한', name: 'role', width: 'auto',align: 'center',renderer : {type: ColumnConverter}}
        ],
        pageOptions: {
            perPage: 5
        }
    });
    grid.on('click', ev => {
        const rowKey = ev.rowKey;
        const id = grid.getFormattedValue(rowKey, "id");

        if (ev.columnName === 'username' && id!=null) {
            location.href = '/admin/memberform?id=' + id;
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
    if(value=="Male"){
        result=value.replace(/Male/gi,"남자");
    }else if(value=="Female"){
        result=value.replace(/Female/gi,"여자");
    }else if (value == "ROLE_MEMBER"){
        result=value.replace(/ROLE_MEMBER/gi,"회원");
    }else if (value == "ROLE_ADMIN"){
        result=value.replace(/ROLE_ADMIN/gi,"관리자");
    }
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
