$(function () {
    getCommentList();
});

/*댓글 등록*/
function commentPost() {
    $.ajax({
        type: "post",
        url: "/comment/post",
        data: {"comment": $("#comment").val(), "bid": $("#bid").val()},
        success: function (data) {
            if (data == "success") {
                location.reload();
            }
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }

    });
}

function getCommentList() {
    $.ajax({
        type: "get",
        url: "/comment/list",
        data: {"bid": $("#bid").val()},
        success: function (data) {
            let html = "";
            const count = data.list.length;

            if (data.list.length > 0) {
                for (let i = 0; i < data.list.length; i++) {
                    html += "<div class='mb-2'>";
                    html += "<input type='hidden' id='commentId' value='" + data.list[i].id + "'>"
                    html += "<b id='commentWriter'>" + data.list[i].writer + "</b>";
                    html += "<span style='float:right;' align='right' id='commentDate'> " + displayTime(data.list[i].updateDate) + " </span>";
                    html += "<h6 id='commentText'>" + data.list[i].comment + "</h6>";
                    html += "<a href='#none' id='reComment'>답글 달기 </a>";
                    html += "<div id='reCommentDiv'></div>"
                    if (data.list[i].writer === $("#sessionNickname").val()) {
                        html += "<a href='#none' id='commentMod' data-toggle='modal' data-target='#modifyModal' >수정 </a>";
                        html += "<a href='#none' id='commentDel'>삭제</a>";
                    }
                    html += "<hr></div>";
                }
            } else {
                html += "<div class='mb-2'>";
                html += "<h6><strong>등록된 댓글이 없습니다.</strong></h6>";
                html += "</div>";
            }
            $("#count").html(count);
            $("#commentList").html(html);

        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "message" + request.responseText + "\n" + "error: " + error);
        }
    });
}

/*날짜 계산*/
function displayTime(timeValue) {
    const dateObj = new Date(timeValue);
    const yy = dateObj.getFullYear();
    const MM = dateObj.getMonth();
    const dd = dateObj.getDate();
    const hh = dateObj.getHours();
    const mm = dateObj.getMinutes();
    return [yy, '/', (MM > 9 ? '' : '0') + MM, '/', (dd > 9 ? '' : '0') + dd, ' ', (hh > 9 ? '' : '0') + hh, ':', (mm > 9 ? '' : '0') + mm].join('');
}


/*모달창에 값 넣기*/
$(document).on("click", "#commentMod", function () {
    const comment = $(this).parent();
    const comment_id = comment.find("#commentId").val()
    const comment_text = comment.find("#commentText").text();
    const comment_writer = comment.find("#commentWriter").text();

    $("#comment_id").val(comment_id);
    $("#comment_text").val(comment_text);
    $("#comment_writer").val(comment_writer);
});

/*댓글 삭제*/
$(document).on("click","#commentDel",function (){
    const comment = $(this).parent();
    const comment_id = comment.find("#commentId").val();

    if (!confirm("댓글을 삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: 'delete',
            url: "/comment/delete",
            data: {"cid": comment_id},
            success: function () {
                alert("댓글을 삭제하였습니다.");
                location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + error);
            }
        });
    }
});

/*댓글 수정*/
$(".modalModBtn").on("click", function () {
    const comment_id = $("#comment_id").val();
    const comment_text = $("#comment_text").val();
    if (!confirm("댓글을 수정하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: 'put',
            url: "/comment/modify",
            data: {"cid": comment_id, "comment": comment_text},
            success: function (result) {
                if (result == "success") {
                    alert("댓글을 수정하였습니다.");
                } else {
                    alert("오류가 발생하였습니다. 다시 시도해주세요")
                }
                location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + error);
            }
        });
    }
});