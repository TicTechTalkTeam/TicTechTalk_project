import { useNavigate } from 'react-router-dom';

export default function Login() {
  const navigate = useNavigate();
  return (
    <div class='loginNjoinBox roundedRectangle darkModeElement'>
      <div class='goTojoinBtn btnElement' onClick={() => navigate('/join')}>
        JOIN US
      </div>
      <form class='formBox' action=''>
        <div class='formInput'>
          <input
            type='text'
            name='inputEmail'
            placeholder='📧이메일을 입력해주세요.'
          />
          <input
            type='password'
            name='inputPassword'
            placeholder='🔑비밀번호를 입력해주세요.'
          />
        </div>
        <button class='btnElement' type='submit'>
          LOGIN
        </button>
      </form>
    </div>
  );
}
