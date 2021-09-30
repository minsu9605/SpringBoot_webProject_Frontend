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
/*댓글 목록*/
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
                    html += "<div class='mb-1' >"
                    html += "<h5 id='commentText' style='display: inline'>" + data.list[i].comment +"</h5>";
                    html += "<span id='ccCount' style='color: red'> ["+data.commentCnt[i]+"]</span>"
                    html += "</div>"
                    // html += "<a href='#none' id='reComment'>답글 달기 </a>";
                    html += "<span style='cursor: pointer; color: blue' id='reCommentBtn'>답글 달기 </span>";
                    html += "<span style='display:none; cursor: pointer; color: blue' id='reCommentCloseBtn'>답글 닫기 </span>";
                    if (data.list[i].writer === $("#sessionNickname").val()) {
                        html += "<span style='cursor: pointer; color: blue' id='commentMod' data-toggle='modal' data-target='#modifyModal' >수정 </span>";
                        html += "<span style='cursor: pointer; color: blue' id='commentDel'>삭제</span>";

                    }  else if($("#sessionRole").val() === "ROLE_ADMIN"){
                        html += "<span style='cursor: pointer; color: blue' id='commentDel'>삭제</span>";
                    }
                    html += "<hr>";
                    html += "<div class='mx-4' id='reCommentDiv'></div></div>";
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
            alert("code: " + request.status + "\n"  + "error: " + error);
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

/*답글 닫기 버튼*/
$(document).on("click","#reCommentCloseBtn",function (){
    const reComment = $(this).parent();
    reComment.find("#reCommentDiv").hide();
    reComment.find("#reCommentBtn").show();
    reComment.find("#reCommentCloseBtn").hide();
});

/*답글 달기 버튼 클릭*/
$(document).on("click","#reCommentBtn",function (){

    const reComment = $(this).parent();
    const cid = reComment.find("#commentId").val();

    reComment.find("#reCommentDiv").show();
    reComment.find("#reCommentBtn").hide();
    reComment.find("#reCommentCloseBtn").show();

    $.ajax({
        type: "get",
        url: "/comment/reComment/list",
        data: {"cid": cid},
        success: function (data) {
            let html = "";

            if (data.list.length > 0) {
                for (let i = 0; i < data.list.length; i++) {
                    html += "<div class='mb-2'>";
                    html += "<input type='hidden' id='commentId' value='" + data.list[i].id + "'>"
                    html += "<b id='commentWriter'>" + data.list[i].writer + "</b>";
                    html += "<span style='float:right;' align='right' id='commentDate'> " + displayTime(data.list[i].updateDate) + " </span>";
                    html += "<h5 id='commentText'>" + data.list[i].comment + "</h5>";
                    if (data.list[i].writer === $("#sessionNickname").val()) {
                        html += "<span style='cursor: pointer; color: blue' id='commentMod' data-toggle='modal' data-target='#modifyModal' >수정 </span>";
                        html += "<span style='cursor: pointer; color: blue' id='commentDel'>삭제</span>";
                    } else if($("#sessionRole").val() === "ROLE_ADMIN"){
                        html += "<span style='cursor: pointer; color: blue' id='commentDel'>삭제</span>";
                    }
                    html += "<hr></div>";
                }
            } else {
                html += "<div class='mb-2'>";
                html += "<h6><strong>등록된 댓글이 없습니다.</strong></h6>";
                html += "</div>";
            }
            html += "<input style='width: 90%' id='reComment' name='reComment' placeholder='댓글을 입력해 주세요'>";
            html += "<button class='btn btn-primary mx-2' id='reCommentSubmit'>등록</button>";

            reComment.find("#reCommentDiv").html(html);

        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    });

    $(document).on("click", "#reCommentSubmit", function () {

        const cComment = reComment.find("#reComment").val();
        $.ajax({
            type: "post",
            url: "/comment/reComment/post",
            data: {"comment": cComment, "bid": $("#bid").val(), "cid": cid},
            success: function (data) {
                if (data == "success") {
                    location.reload();
                }
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + error);
            }
        });
    });
});

/*모달창에 값 넣기*/
$(document).on("click", "#commentMod", function () {
    const comment = $(this).parent();
    const comment_id = comment.find("#commentId").val()
    const comment_text = comment.find("#commentText").text();
    const comment_writer = comment.find("#commentWriter").text();


    console.log("수정 id : "+comment_id);

    $("#comment_id").val(comment_id);
    $("#comment_text").val(comment_text);
    $("#comment_writer").val(comment_writer);
});

/*댓글 삭제*/
$(document).on("click","#commentDel",function (){
    const comment = $(this).parent();
    const comment_id = comment.find("#commentId").val();

    console.log("id : "+comment_id);

    if (!confirm("댓글을 삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: 'delete',
            url: "/comment/delete",
            data: {"cid": comment_id},
            success: function (count) {
                if(count == 0){
                    alert("댓글을 삭제하였습니다.");
                    location.reload();
                }else{
                    alert(count+"개의 답글이 있습니다. 댓글을 삭제 할 수 없습니다.")
                }
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