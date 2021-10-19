document.body.style.overflow = "hidden";

const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스

const options = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(37.5667914, 126.97867567), //지도의 중심좌표.
    level: 3 //지도의 레벨(확대, 축소 정도)
};
const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 지도에 마커를 생성하고 표시한다
const marker = new kakao.maps.Marker({
    map: map // 마커를 표시할 지도 객체
});

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

const infowindow = new kakao.maps.InfoWindow();

let myLng;
let myLat;


/*지도 직접 클릭*/
kakao.maps.event.addListener(map, 'click', function (mouseEvent) {

    showInfoWindow(mouseEvent.latLng);

    // 마커를 클릭한 위치에 표시합니다
    marker.setPosition(mouseEvent.latLng);
    marker.setMap(map);

    myLat = mouseEvent.latLng.getLat();
    myLng = mouseEvent.latLng.getLng();
});

/*현재위치 버튼 클릭*/
$("#location").on('click', function () {
    /*현재위치 받아오기*/

    function locationLoadSuccess(pos) {
        // 현재 위치 받아오기
        const currentPos = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);

        showInfoWindow(currentPos);
        // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
        map.panTo(currentPos);

        // 마커 생성
        marker.setPosition(currentPos);

        // 기존에 마커가 있다면 제거
        marker.setMap(null);
        marker.setMap(map);

        myLat = currentPos.getLat();
        myLng = currentPos.getLng();
    };

    function locationLoadError(pos) {
        alert('위치 정보를 가져오는데 실패했습니다.');
    };

    navigator.geolocation.getCurrentPosition(locationLoadSuccess, locationLoadError);
});

/*검색*/
$("#searchBtn").on('click', function () {
    const keyword = $("#address").val()

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(keyword, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            showInfoWindow(coords);

            marker.setPosition(coords);
            marker.setMap(null);
            marker.setMap(map)

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);

            myLat = coords.getLat();
            myLng = coords.getLng();
        }
    });
});

// 좌표로 법정동 상세 주소 정보를 요청합니다
function searchDetailAddrFromCoords(coords, callback) {
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

/*법정 주소 인포윈도우*/
function showInfoWindow(myLocation) {
    searchDetailAddrFromCoords(myLocation, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';

            var content = '<div class="bAddr">'
                + detailAddr +
                '</div>';
            infowindow.setContent(content);
            infowindow.open(map, marker);
        }
    });
}

// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
searchAddrFromCoords(map.getCenter(), displayCenterInfo);

kakao.maps.event.addListener(map, 'idle', function() {
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById('centerAddr');

        for(var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === 'H') {
                infoDiv.innerHTML = result[i].address_name;
                break;
            }
        }
    }
}

//마커 클릭 이벤트
kakao.maps.event.addListener(marker, 'click', function (mouseEvent) {
    if (confirm("이 위치로 하시겠습니까?")) {
        alert("위치가 지정되었습니다");
        /*console.log(myLat);
        console.log(latlng.getLat());
        console.log(myLng);*/
        opener.document.getElementById("myLat").value = myLat;
        opener.document.getElementById("myLng").value = myLng;
        close();
    }
});

// 지도를 표시하는 div 크기를 변경하는 함수입니다
$(window).resize(function () {
    container.style.width = $(window).width() - 25 + 'px';
    container.style.height = $(window).height() - 92 + 'px';
});
