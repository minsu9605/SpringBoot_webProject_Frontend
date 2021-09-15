
function commentPost() {
    $.ajax({
        type: "post",
        url: "/comment/post",
        data: {"content":$("#content").val(), "bid" : $("#bid").val()},
        success : function (data) {
            if(data=="success"){
                getCommentList();
                $("#comment-input").val("");
            }
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }

    });
}

function getCommentList(){
    $.ajax({
        type: "get",
        url: "/comment/list",
        data: $("#commentForm").serialize(),
        success : function (data) {
            let html="";
            const count = data.length;
            if(data.length>0){

                for(i=0; i<data.length; i++){
                    html += "<div>";
                    html += "<div><table class='table'><h6><strong>"+data[i].writer+"</strong></h6>";
                    html += data[i].comment + "<tr><td></td></tr>";
                    html += "</table></div>";
                    html += "</div>";
                }

            } else {
                html += "<div>";
                html += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
                html += "</table></div>";
                html += "</div>";
            }
            $("#count").html(count);
            $("#commentList").html(html);
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }

    });
}