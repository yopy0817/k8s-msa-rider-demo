import { MapContainer, Marker, Polyline, Popup, TileLayer} from 'react-leaflet'

import './App.css';
import { useEffect, useRef, useState } from 'react';

import Lottie from 'lottie-react'; //로티
import riderAnim from './anim/rider-anim.json' //로티 에님

/*
실행 모드별 .env 로딩 규칙 (2025/03/13 현재 사용하지 않음)
import config from "./config";
{config.mode}
{config.apiUrl}

npm start	  -> .env.development
npm run build -> .env.production
npm test 	  -> .env.test
*/
function App() {
  /*
  npm create react-app react-openmap

  리액트 오픈맵 - https://react-leaflet.js.org/
  1. npm install leaflet   
  2. npm install react-leaflet
  3. https://leafletjs.com/download.html에서 leaflet 1.9.4다운
  4. public폴더에 css, image파일 넣고 index.html에서 링크 
  5. 컨테이너 높이, 너비 설정
  */

  const position = [37.5642135, 127.0016985]; //중심좌표
  const [riders, setRiders] = useState([]) //데이터
  const [polylineCoords, setPolylineCoords] = useState([]); //v2.라이더 이동경로 데이터
  const [polylineSelectedId, setPolylineSelectedId] = useState(null); //현재 선택되어 있는 아이디

  //step.5 - 리액트 로티 로딩기능
  //npm install lottie-react
  //로티 파일 다운 -> anim폴더에 추가
  const [isLoading, setLoading] = useState(false) //로딩

  //step.6 - 중심좌표 이동기능 MapContainer에 ref={setMap} 추가, https://react-leaflet.js.org/docs/example-external-state/
  const [map, setMap] = useState(null);
  //step.7 - 팝업창 제어하기 Popup에 ref={(tag) => popupRef.current[index] = tag }
  const popupRef = useRef([]); 
  //step.6 ~ 7
  const moveMap = async (value, index) => {
    //map.setView([value.lat, value.lng], 12)
    map.flyTo([value.lat, value.lng], 17) 
    popupRef.current[index].openOn(); //팝업창 띄우기

    //v2. step.8 - 마커 클릭시 라이더 데이터 가져오기, 현재 선택한 라이더 정보저장
    var result = await fetch(`/api/getVehicle/${value.name}`).then( (response) => response.json()) 
    setPolylineCoords(result); 
    setPolylineSelectedId(value.name);

  }
  //step.1 - 맵 데이터 가져오기
  useEffect( () => {

    const mapInterval = window.setInterval( async () => {
     
      try {
		    //개발환경에서 package.json에 proxy설정
        //var result = await fetch('/api/test').then(response => response.text())
		    var result = await fetch(`/api/getVehicle`).then( response => response.json())

        setRiders(result) //데이터변경
        setLoading(true) //애니메이션 변경
      } catch(e) {
        console.log(e, `서버에 데이터 수신에 문제가 있습니다. 연결을 재시도 합니다.`);
        setLoading(true) //애니메이션 변경
      }
      
    }, 5000);
    
    return () => {
      clearInterval(mapInterval); //리렌더링 시 인터벌종료
    }

  }, []); //처음한번만 실행, 이후 5초 간격 주기적으로 실행


  //v2. step.8 라이더스 데이터가 도착하면, 유저가 마커를 선택하고 있을때 화면동기화
  useEffect( () => {
    try {
      if(polylineSelectedId) {
        const selectedObj = riders.find( item => item.name == polylineSelectedId);
  
        const addArr = [selectedObj.lat, selectedObj.lng]; //라이더스 데이터에서 선택된마커를 찾음
        const lastArr = polylineCoords[polylineCoords.length - 1]; //polly라인 마지막데이터
  
        //기존 poly의 마지막요소와, 현재 선택된 polylineSelectedId가 같지 않으면 화면 동기화
        if(addArr.toString() != lastArr.toString()) {
          const newArr = [...polylineCoords, addArr];
          setPolylineCoords(newArr)
        }     
      }
    } catch(e) {
      console.log(e, '라이더스에 데이터가 부정확 합니다');
    }

  }, [riders]); 


  //step.2 - 라이더 리스트
  const riderList = riders.map( (value, index) => 
  <li className='rider-item' key={index} onClick={ () => moveMap(value, index) }>
    <a href="#">
      <p className='rider-name'>{value.name}</p>
      <p className='rider-time'>시간: <br/> {value.date}</p>
      <p className='rider-coords'>좌표:<br/> {value.lat} {value.lng}</p>
    </a>
  </li>
  )

  //step.3 - 라이더 마커 
  //autoPan(팝업이 지도의 현재 화면에 완전히 보이지 않을 경우, 팝업이 보이도록 지도를 자동으로 이동시킴)
  //keepInView(팝업이 지도 밖으로 벗어나지 않도록 지도를 이동시키는 옵션)
  //마커가 화면에서 벗어나면, 마커를 지도에 띄우려고 자동이동하는데, 막는 속성이 autoPan={false} keepInView={false}
  const riderMackerList = riders.map( (value, index) =>
    <Marker 
      position={[value.lat, value.lng]} 
      key={index} 
      eventHandlers={{
        click: () => moveMap(value, index)
      }}
      >
      <Popup ref={(tag) => popupRef.current[index] = tag } autoPan={false} keepInView={false} >
        {value.name}<br/>
        {value.lat} {value.lng}
      </Popup>
    </Marker>
  )

  //v2. 마커 옵션
  const polylineOptions = {
    color: "rgba(0, 0, 255, 0.6)", // 노란색 형광펜 느낌 (RGB + 투명도)
    weight: 9, // 선의 두께
    lineCap: "round", // 끝을 둥글게
    lineJoin: "round", // 선 연결부 둥글게
  };

  return (
    <div className="App">

      <header className='header'>
        Coding404 라이더 관제 시스템
      </header>


      <section className='gis-wrap'>
        <ul className='item-left'>
          {/* 
          <li className='rider-item'>
            <a href="#">
              <p className='rider-name'>라이더명</p>
              <p className='rider-time'>2024-11-11 22:03:01</p>
              <p className='rider-coords'>위도 경도</p>
            </a>
          </li>
          */}
          {riderList}
        </ul>

        <div className='item-right' style={{position: 'relative'}}>
          {/* 로티애니메이션 로딩*/}
          { isLoading === false ?
          <div style={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)', //요소를 X축과 Y축 방향으로 이동시켜 위치를 조정하는 역할
                zIndex: 1000
              }}
            >
            <Lottie animationData={riderAnim} style={{ width: 200, height: 200 }} />
          </div>
          :
          <MapContainer 
            center={position} 
            zoom={12}
            scrollWheelZoom={true} 
            style={{height: "100%"}}
            ref={setMap}
            >
            <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> coding404'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            {/* 
            <Marker position={position}>
              <Popup>
                A pretty CSS3 popup. <br /> Easily customizable.
              </Popup>
            </Marker>
            */}
            {riderMackerList}
            
            {/* v2.<Polyline positions={[좌표]} color="blue" pathOptions={polylineOptions} /> */}
            {/* v2.선을 표시 (좌표 데이터가 있을 때만) */}
            {polylineCoords.length > 0 && <Polyline positions={polylineCoords} color="blue" pathOptions={polylineOptions}/>}

          </MapContainer>
          }
        </div>

      </section>
    </div>
  );
}

export default App;
