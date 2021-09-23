$(function (){
    getCommentList();
});

function commentPost() {
    $.ajax({
        type: "post",
        url: "/comment/post",
        data: {"comment":$("#comment").val(), "bid" : $("#bid").val()},
        success : function (data) {
            if(data == "success"){
                location.reload();
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
        data: {"bid" : $("#bid").val()},
        success : function (data) {
            console.log(data.list);
            let html="";
            const count = data.list.length;
            if(data.list.length>0){

                for(let i=0; i<data.list.length; i++){
                    html += "<div>";
                    html += "<div><table class='table'><h6><strong>"+data.list[i].writer+"</strong></h6>";
                    html += "<h6 class=''>"+data.list[i].createDate+"</h6>";
                    html += data.list[i].comment + "<tr><td></td></tr>";
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
            alert("code: " + request.status + "\n" + "message" + request.responseText + "\n" + "error: " + error);
        }

    });
}