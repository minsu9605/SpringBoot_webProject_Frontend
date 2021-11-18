var ctx = document.getElementById('myChart').getContext('2d');

// 각 게시글
const now = new Date();
const nowYear = now.getFullYear();
const nowMonth = now.getMonth();
let monthOption = nowMonth + 1;
let yearOption = nowYear;

let sellData = [];
let soldOutData = [];
let oldData = [];
let labels = [];

/*년도 option설정*/
for (let i = yearOption - 3; i <= yearOption; i++) {
    $("#year").append("<option value='" + i + "'>" + i + "년" + "</option>");
}
/*현재 날짜 select*/
$("#year").val(yearOption).prop("selected", true);
$("#month").val(monthOption).prop("selected", true);

const config = {
    type: "line",
    data: {
        labels: labels,
        datasets: [
            {
                label: "판매중",
                backgroundColor: "transparent",
                borderColor: "red",
                borderWidth: "2",
                data: sellData, //data 부분에 위에서 선언해둔 최저 기온 sellData 넣는다.
            },
            {
                label: "판매 완료",
                backgroundColor: "transparent",
                borderColor: "blue",
                borderWidth: "2",
                data: soldOutData, //data 부분에 위에서 선언해둔 최대 기온 maxData를 넣는다.
            },
            {
                label: "판매중 7일 경과",
                backgroundColor: "transparent",
                borderColor: "green",
                borderWidth: "2",
                data: oldData, //data 부분에 위에서 선언해둔 최대 기온 maxData를 넣는다.
            },
        ], //dataset 끝
    },//data 끝
    // 옵션
    options: {
        legend: {display: true},
        title: {display: true, text: '상태별 게시글 갯수'}
    }
}

// status별 차트 그래프
const boardCountChart = new Chart(ctx, config);

boardData();

// 그래프 data 세팅
function boardData() {
    $.ajax({
        method: "GET",
        url: '/api/admin/adminBoardChart',
        data: {"monthOption": monthOption, "yearOption": yearOption},
        success: function (data) {
            if (data.sell.length === 0 && data.soldOut.length === 0 && data.old.length === 0)
                alert("해당기간에 조회된 데이터가 없습니다.");
            for (let i = 0; i < data.sell.length; i++) {
                sellData.push(data.sell[i].statusCount);
                soldOutData.push(data.soldOut[i].statusCount);
                oldData.push(data.old[i].statusCount);
                labels.push(data.sell[i].batchDate);

                boardCountChart.update();
            }
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    });
}

/*month를 변경 시*/
$("#month").change(function (){
    sellData =[];
    soldOutData=[];
    oldData=[];
    labels=[];

    const dataNameArr = [sellData,soldOutData,oldData];
    let i=0;
    boardCountChart.data.labels=labels;
    boardCountChart.data.datasets.forEach((dataset) => {
        dataset.data=dataNameArr[i];
        i++
    });

    monthOption = $(this).val();
    boardData();
});

$("#year").change(function (){
    sellData =[];
    soldOutData=[];
    oldData=[];
    labels=[];

    const dataNameArr = [sellData,soldOutData,oldData];
    let i=0;
    boardCountChart.data.labels=labels;
    boardCountChart.data.datasets.forEach((dataset) => {
        dataset.data=dataNameArr[i];
        i++
    });
        yearOption = $(this).val();
    boardData();
});
