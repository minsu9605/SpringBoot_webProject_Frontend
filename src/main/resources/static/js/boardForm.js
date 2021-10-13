function nullCheck() {
    if (!titleCheck()) {
        return false;
    } else if (!contentCheck()) {
        return false;
    } else if (!mapCheck()){
        return false;
    }
    return true;
}

function titleCheck() {
    const title = $("#title").val();
    if (title == "") {
        alert("제목을 입력해 주세요. 필수항목 입니다.");
        return false;
    } else if (title.length > 20) {
        alert("제목은 20글자를 초과할수 없습니다");
        return false;
    }
    return true;
}

function contentCheck() {
    const content = $("#content").val();
    if (window.editor.getData() == "") {
        alert("내용을 입력해 주세요. 필수항목 입니다.");
        return false;
    } else if (content.length < 11) {
        alert("10글자 이상 입력해주세요!");
        return false;
    }
    return true;
}

function mapCheck() {
    const myLat = $("#myLat").val();
    const myLng = $("#myLng").val();
    if (myLat == "" || myLng == "") {
        alert("장소를 선택해 주세요. 필수항목 입니다.");
        return false;
    }
    return true;
}

$(document).ready(function () {

    $('#content').summernote({
        height: 500,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '내용을 입력해 주세요',	//placeholder 설정
        callbacks: {	//여기 부분이 이미지를 첨부하는 부분
            onImageUpload: function (files) {
                uploadSummernoteImageFile(files[0], this);
            },
            onPaste: function (e) {
                let clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    let item = clipboardData.items[0];
                    if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                        e.preventDefault();
                    }
                }
            }
        }
    });
});

/*이미지 파일 업로드*/
function uploadSummernoteImageFile(file, editor) {
    const data = new FormData();
    data.append("file", file);
    $.ajax({
        data: data,
        type: "POST",
        url: "/uploadSummernoteImageFile",
        contentType: false,
        processData: false,
        success: function (data) {
            //항상 업로드된 파일의 url이 있어야 한다.
            $(editor).summernote('insertImage', data.url);
        }
    });
}

$("#mapButton").on('click',function (){
    window.open("/board/map","map","width=900,height=550,left=300,top=100");

});


