$(document).ready(function () {
    const now = new Date();
    const nowYear = now.getFullYear();
    const nowAge_input = nowYear - $("#year").val() + 1;
    const nowAge = nowYear - $("#year option:selected").val() + 1;
    $("#age").val(nowAge);
    $("#age_input").val(nowAge_input);
});


function updateInfo() {
    if(!confirm("회원 정보를 수정하시겠습니까?")){
        return false
    }
}

