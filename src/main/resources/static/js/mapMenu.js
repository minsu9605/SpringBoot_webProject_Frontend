var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 4 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

/*인포윈도우*/
const infowindow = new kakao.maps.InfoWindow({
    removable: true
});
// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

let positions = [];
let bounds;


// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status) {
    if (status === kakao.maps.services.Status.OK) {
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    }
}

/*검색버튼 클릭*/
$("#searchBtn").on('click', function () {
    const keyword = $("#address").val();
    // 키워드로 장소를 검색합니다
    ps.keywordSearch(keyword, placesSearchCB);
});

/*엔터키 검색*/
$("#address").on('keyup',function (e){
    if(e.keyCode==13){
        const keyword = $("#address").val();
        ps.keywordSearch(keyword, placesSearchCB);
    }
});


/*현재위치 버튼 클릭*/
$("#location").on('click', function () {
    /*현재위치 받아오기*/
    function locationLoadSuccess(pos) {
        // 현재 위치 받아오기
        const currentPos = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);

        // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
        map.panTo(currentPos);
        bounds = map.getBounds();
        showMarker(bounds);
    };

    function locationLoadError(pos) {
        alert('위치 정보를 가져오는데 실패했습니다.');
    };

    navigator.geolocation.getCurrentPosition(locationLoadSuccess, locationLoadError);

});

/*처음 로드*/
$(function () {
    bounds = map.getBounds();
    showMarker(bounds);
});

/*드래그 이벤트*/
kakao.maps.event.addListener(map, 'dragend', function () {
    bounds = map.getBounds();
    showMarker(bounds);
});

/*DB연동 마커 표시*/
function showMarker(bounds) {
    positions.length = 0;
    $.ajax({
        method: 'get',
        url: "/api/mapMenu/list",
        data: {startLat: bounds.qa, startLng: bounds.ha, endLat: bounds.pa, endLng: bounds.oa},
        success: function (success) {
            for (let i = 0; i < success.data.length; i++) {
                positions.push({title: success.data[i].title, latlng: new kakao.maps.LatLng(success.data[i].myLat, success.data[i].myLng), id: success.data[i].id, category : success.data[i].categoryName});
            }

            for (let i = 0; i < positions.length; i++) {
                let imageSrc;
                switch (success.data[i].categoryName) {
                    case "디지털기기" :
                        imageSrc = "/images/marker_blue.png";
                        break;
                    case "가구/인테리어":
                        imageSrc = "/images/marker_green.png";
                        break;
                    case "생활/가공식품":
                        imageSrc = "/images/marker_navy.png";
                        break;
                    case "스포츠/레저":
                        imageSrc = "/images/marker_orange.png";
                        break;
                    case "게임/취미":
                        imageSrc = "/images/marker_purple.png";
                        break;
                    case "도서/티켓/음반":
                        imageSrc = "/images/marker_scarlet.png";
                        break;
                    case "기타 중고물품":
                        imageSrc = "/images/marker_yellow.png";
                        break;
                }
                var imageSize = new kakao.maps.Size(40, 45), // 마커이미지의 크기입니다
                    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

                var marker = new kakao.maps.Marker({
                    map: map,
                    position: positions[i].latlng,
                    title: positions[i].title,
                    image : markerImage
                });
                var iwContent = '<div class="customoverlay" style="padding:5px; width: max-content;">제목 : '+ positions[i].title +'<br><a href="http://localhost:8080/board/content?id=' + positions[i].id + '" style="color:blue" target="_blank">게시글보기</a></div>',
                    iwPosition = positions[i].latlng; //인포윈도우 표시 위치입니다

                /*for문 밖에 있으면 마지막 마커에만 적용*/
                kakao.maps.event.addListener(marker, 'click', clickMarker(map, marker, iwContent, iwPosition));

            }
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    });
}

/*마커 클릭*/
function clickMarker(map, marker, iwContent, iwPosition) {
    return function () {
        infowindow.setContent(iwContent);
        infowindow.setPosition(iwPosition);
        infowindow.open(map, marker);
    }
}




