const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
const options = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(37.160865, 127.754386), //지도의 중심좌표.
    level: 3 //지도의 레벨(확대, 축소 정도)
};
const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 지도에 마커를 생성하고 표시한다
const marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(37.56706, 126.98006), // 마커의 좌표
    draggable : true, // 마커를 드래그 가능하도록 설정한다
    map: map // 마커를 표시할 지도 객체
});

// 마커 위에 표시할 인포윈도우를 생성한다
const infowindow = new kakao.maps.InfoWindow({
    content : '<div style="padding:5px;">현재위치 :D</div>' // 인포윈도우에 표시할 내용
});

// 인포윈도우를 지도에 표시한다
infowindow.open(map, marker);

function locationLoadSuccess(pos){
    // 현재 위치 받아오기
    const currentPos = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);

    // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
    map.panTo(currentPos);

    // 마커 생성
    marker.setPosition(currentPos);
    infowindow.open(map, marker);

    /*const marker = new kakao.maps.Marker({
        position: currentPos
    });*/

    // 기존에 마커가 있다면 제거
    marker.setMap(null);
    marker.setMap(map);
};
function locationLoadError(pos){
    alert('위치 정보를 가져오는데 실패했습니다.');
};

/*버튼 클릭*/
$("#location").on('click',function (){
    navigator.geolocation.getCurrentPosition(locationLoadSuccess,locationLoadError);
});

