$(document).readt
const startIndex =1;
const searchStep = 5;

readOldNotify(startIndex);
$('#searchMoreNotify').click(function (){
    startIndex +=searchStep;
})
function moreList(){

    $.ajax({
        type:'post',
        dataType:'JSON',
        data : {

        }

    });
}
