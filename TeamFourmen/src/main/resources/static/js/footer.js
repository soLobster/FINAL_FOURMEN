/**
 * 
 */

    var mapOptions = {
        center: new naver.maps.LatLng(37.4987, 127.0315), // 서울 시청 좌표
        zoom: 15, // 확대 수준
    };

    var map = new naver.maps.Map('map', mapOptions);

    var markerOptions = {
        position: new naver.maps.LatLng(37.4987, 127.0315), // 서울 시청 좌표
        map: map,
    };

    var marker = new naver.maps.Marker(markerOptions);

 

    
