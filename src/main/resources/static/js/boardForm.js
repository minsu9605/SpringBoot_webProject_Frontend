function nullCheck() {
    if (!titleCheck()) {
        return false;
    } else if (!contentCheck()) {
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

/**
 * 이미지 파일 업로드
 */
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


/*
$(document).ready(function () {

    const toolbar = [
        // 글꼴 설정
        ['fontname', ['fontname']],
        // 글자 크기 설정
        ['fontsize', ['fontsize']],
        // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
        ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
        // 글자색
        ['color', ['forecolor', 'color']],
        // 표만들기
        ['table', ['table']],
        // 글머리 기호, 번호매기기, 문단정렬
        ['para', ['ul', 'ol', 'paragraph']],
        // 줄간격
        ['height', ['height']],
        // 그림첨부, 링크만들기, 동영상첨부
        ['insert', ['picture', 'link', 'video']],
        // 코드보기, 확대해서보기, 도움말
        ['view', ['codeview', 'fullscreen', 'help']]
    ];

    const setting = {
        height: 300,
        minHeight: null,
        maxHeight: null,
        focus: true,
        lang: 'ko-KR',
        toolbar: toolbar,
        callbacks: { //여기 부분이 이미지를 첨부하는 부분
            onImageUpload: function (files, editor, welEditable) {
                for (let i = 0; i < files.length; i++) {
                    uploadSummernoteImageFile(files[i], this);
                }
            }
        }
    };

    $('#content').summernote(setting);
});

function uploadSummernoteImageFile(file, editor) {
    const data = new FormData();
    data.append("file", file);
    $.ajax({
        data: data,
        type: "POST",
        url: "/uploadSummernoteImageFile",
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (url) {
            $(editor).summernote('InsertImage', url, function ($Image) {
                $(Image).css('width',"25%");
            });
        }
    });
}*/
