import { Link } from 'react-router-dom';
import logo from '../logo.svg';

export default function Header() {
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
        <Link to='/mypage'>
          <div className='profile'></div>
        </Link>
      </header>
    </>
  );
}
