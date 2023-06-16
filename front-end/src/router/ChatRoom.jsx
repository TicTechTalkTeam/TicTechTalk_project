import SockJS from 'sockjs-client';
import StompJs from '@stomp/stompjs';

export default function ChatRoom() {
  // 클라이언트 객체 생성
  const client = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/app/ws/chat', // 백엔드에서 설정한 endPoint
    connectHeaders: {
      login: 'user',
      passcode: 'password',
    },
    debug: (str) => console.log(str),
    reconnectDelay: 5000, // 자동 재연결
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  client.onConnect = (frame) => {
    // 클라이언트가 연결되었을때 실행할 내용
  };

  client.onStompError = (frame) => {
    // 오류 발생시 실행할 내용
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  client.activate(); // 클라이언트 활성화
  // client.deactivate(); // 클라이언트 비활성화

  // SocketJS로 소켓 미지원 브라우저 대응
  // if(typeof WebSocket !== 'function') {
  //   client.webSocketFactory = () => new SockJS('http://localhost:8080/stomp');
  // }

  // 😎메세지 보내기
  client.publish({
    destination: 'topic/general',
    body: 'SSIBAL!',
    headers: { headerSubject: 'headerTestContent' },
  });

  // 서버나 브로커에서 요구하지 않는 경우 메시지의 크기를 나타내는 content-length 헤더를 생략할 수 있음
  // client.publish({
  //   destination: '/topic/general',
  //   body: 'Hello world',
  //   skipContentLengthHeader: true,
  // });

  // 헤더 포함
  // client.publish({
  //   destination: '/topic/general',
  //   body: 'Hello world',
  //   headers: { priority: '9' },
  // });

  // 😎메세지 받기
  // subscribe(목적지, 콜백함수)
  // 구독 ID에 해당하는 하나의 속성 id와 이 친구의 구독을 취소할 수 있는 unsubscribe 메서드가 있는 객체를 반환
  // 서버로부터 STOMP 메세지를 수신할 때 콜백함수 호출
  const subscription = client.subscribe('/queue/test', (message) => {
    if (message.body) {
      console.log('수신한 메세지 내용: ' + message.body);
    } else {
      console.log('엥 메세지가 비었음');
    }
  });

  // 구독 해제
  // subscription.unsubscribe();

  return <div class='myPageBox roundedRectangle darkModeElement'></div>;
}
