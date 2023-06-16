import { Link } from 'react-router-dom';

export default function Mypage() {
  return (
    <div class='myPageBox roundedRectangle darkModeElement'>
      <div class='myPageProfile'></div>
      <div class='mypageUserInfo'>
        <div>이메일</div>
        <div>닉네임</div>
        <div>점수</div>
        <div>자기소개</div>
      </div>
      <button class='btnElement'>
        <Link to='/mypage/update'>프로필 수정하기</Link>
      </button>
    </div>
  );
}
