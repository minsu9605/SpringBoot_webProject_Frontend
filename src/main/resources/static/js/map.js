document.body.style.overflow = "hidden";

const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스

const options = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(37.5667914, 126.97867567), //지도의 중심좌표.
    level: 3 //지도의 레벨(확대, 축소 정도)
};
const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 지도에 마커를 생성하고 표시한다
const marker = new kakao.maps.Marker({
    //position: new kakao.maps.LatLng(37.56706, 126.98006), // 마커의 좌표
    //draggable : true, // 마커를 드래그 가능하도록 설정한다
    map: map // 마커를 표시할 지도 객체
});

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

/*// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();*/

const infowindow = new kakao.maps.InfoWindow();

let myLng;
let myLat;

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
    searchDetailAddrFromCoords(mouseEvent.latLng, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
            detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';

            var content = '<div class="bAddr">' +
                '<span class="title"></span>' +
                detailAddr +
                '</div>';

            // 마커를 클릭한 위치에 표시합니다
            marker.setPosition(mouseEvent.latLng);
            marker.setMap(map);

            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
            infowindow.setContent(content);
            infowindow.open(map, marker);

            myLat=mouseEvent.latLng.getLat();
            myLng=mouseEvent.latLng.getLng();

        }
    });
});

/*현재위치 버튼 클릭*/
$("#location").on('click', function () {
    /*현재위치 받아오기*/

    function locationLoadSuccess(pos) {
        // 현재 위치 받아오기
        const currentPos = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);

        searchDetailAddrFromCoords(currentPos, function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';

                var content = '<div class="bAddr">' +
                    '<span class="title"></span>' +
                    detailAddr +
                    '</div>';

                // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
                map.panTo(currentPos);

                infowindow.setContent(content);

                // 마커 생성
                marker.setPosition(currentPos);
                infowindow.open(map, marker);

                // 기존에 마커가 있다면 제거
                marker.setMap(null);
                marker.setMap(map);

                myLat=currentPos.getLat();
                myLng=currentPos.getLng();
            }
        });

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

            searchDetailAddrFromCoords(coords, function (result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                    detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';

                    var content = '<div class="bAddr">' +
                        '<span class="title"></span>' +
                        detailAddr +
                        '</div>';
                    infowindow.setContent(content);
                }
            });

            marker.setPosition(coords);
            marker.setMap(null);
            marker.setMap(map)
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);

            mylat=currentPos.getLat();
            myLng=currentPos.getLng();
        }
    });
});

//마커 클릭 이벤트
kakao.maps.event.addListener(marker, 'click', function () {
    // 마커 위에 인포윈도우를 표시합니다
    if(confirm("이 위치로 하시겠습니까?")){
        alert("위치가 지정되었습니다");
        opener.document.getElementById("myLat").value = myLat;
        opener.document.getElementById("myLng").value = myLng;
        close();
    }
});

// 지도를 표시하는 div 크기를 변경하는 함수입니다
$(window).resize(function (){
    container.style.width = $(window).width()-25 + 'px';
    container.style.height = $(window).height()-92 + 'px';
});
