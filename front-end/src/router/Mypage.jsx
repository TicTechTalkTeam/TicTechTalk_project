import { Link } from 'react-router-dom';

export default function Mypage() {
  return (
    <div class='myPageBox roundedRectangle darkModeElement'>
      <div class='myPageProfile'></div>
      <div class='mypageUserInfo'>
        <div>abcde@gmail.com</div>
        <div>김블랙맘바</div>
        <div>123 point</div>
        <div>안녕하세요 동에번쩍 서에번쩍 김블랙맘바입니다!</div>
      </div>
      <button class='btnElement'>
        <Link to='/mypage/update'>프로필 수정하기</Link>
      </button>
    </div>
  );
}
