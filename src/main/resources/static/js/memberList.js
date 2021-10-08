$(function () {
    getList();
});

let grid;

/*class CustomTextEditor {
    constructor(gender) {
        const el = document.createElement('input');

        el.type = 'text';
        el.value = String(gender.value);

        this.el = el;
    }

    getElement() {
        return this.el;
    }

    getValue() {
        return this.el.value;
    }

    mounted() {
        this.el.select();
    }
}*/

function getList() {
    const datasource = {
        api: {
            readData: {
                url: '/admin/list/table',
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
            {header: '이름(닉네임)', name: 'nickname', width: 'auto',align: 'center'},
            {header: '생년월일', name: 'birth', width: 'auto',align: 'center'},
            {header: '나이', name: 'age', align: 'center', width: 120},
            {header: '성별', name: 'gender', width: 'auto',align: 'center'},
            {header: '권한', name: 'role_name', width: 'auto',align: 'center'}
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
