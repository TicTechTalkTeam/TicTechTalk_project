import { Link, useNavigate } from 'react-router-dom';
import logo from '../logo.svg';

export default function Header() {
  const navigate = useNavigate();
  ////////////다크모드////////////
  // setCookie('쿠키', '쿠키테스트');
  sessionStorage.setItem('theme', 'light');
  const darkMode = () => {
    const theme = sessionStorage.getItem('theme');
    if (theme === 'light') {
      document.querySelector('body').setAttribute('data-theme', 'dark');
      sessionStorage.setItem('theme', 'dark');
    } else if (theme === 'dark') {
      document.querySelector('body').removeAttribute('data-theme');
      sessionStorage.setItem('theme', 'light');
    }
  };
  ////////////다크모드////////////
  return (
    <>
      <header>
        <div className='logo'>
          <Link to='/'>
            <img src={logo} alt='' />
          </Link>
        </div>
        <div className='forumBoard'>
          <Link to='/boards/forum'>FORUM</Link>
        </div>
        <div className='QNABoard'>
          <Link to='/boards/qna'>QNA</Link>
        </div>
        <div className='referenceBoard'>
          <Link to='/boards/reference'>REFERENCE</Link>
        </div>
        <input
          type='text'
          className='searchTap darkModeElement'
          placeholder='search'
        />
        <label className='toggle' htmlFor='togleBtn'>
          <input className='togleBtn darkModeElement' type='checkbox' />
        </label>
        <div
          className='profile'
          onClick={() => {
            localStorage.getItem('accessToken')
              ? navigate('/mypage')
              : navigate('/login');
          }}
        ></div>
      </header>
    </>
  );
}
