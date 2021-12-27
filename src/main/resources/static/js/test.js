const paramDto = {
    id : $("#msg1").val(),
    writer : $("#msg2").val()
}

$("#test_btn").on('click',function (){
    $.ajax({
        type:'post',
        url : "/api/test/hello",
        data : paramDto,
        success: function (result){
            alert(result.result);
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n" + "error : " + error);
        }
    });
});
