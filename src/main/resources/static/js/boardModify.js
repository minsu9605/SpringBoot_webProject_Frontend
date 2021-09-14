ClassicEditor
    .create(document.querySelector('#content'))
    .then(editor => {
        console.log(editor);
    })
    .catch(error=>{
        console.error(error);
    });

$("#boardDelete").on("click",function() {
    if(!confirm("게시물을 삭제하시겠습니까?")){
        return false;
    }else{
        $.ajax({
            type:"delete",
            url : "/board/delete",
            data : {"id" : $("#id").val()},
            success: function (result){
                    console.log("결과: " +result);
                    alert("게시물을 삭제 하였습니다.");
                    window.location.href='/board/list';
            },
            error: function (request, status, error){
                alert("code : " +  request.status + "\n" + "error : " + error);
            }
        });
    }
});