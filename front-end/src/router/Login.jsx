import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { login, logout, deleteUser } from '../store/userSlice';

export default function Login() {
  const navigate = useNavigate();
  const { register, handleSubmit } = useForm();

  const dispatch = useDispatch();

  const loginHandler = (data) => {
    dispatch(login(data));
  };

  const logoutHandler = () => {
    dispatch(logout());
  };

  const deleteHandler = () => {
    dispatch(deleteUser());
  };

  return (
    <div className='loginNjoinBox roundedRectangle darkModeElement'>
      <div className='goTojoinBtn btnElement' onClick={() => navigate('/join')}>
        JOIN US
      </div>
      <form
        className='formBox'
        method='post'
        onSubmit={handleSubmit(loginHandler)}
      >
        <div className='formInput'>
          <input
            type='text'
            name='inputEmail'
            placeholder='📧이메일을 입력해주세요.'
            {...register('userEmail', { required: true })}
          />
          <input
            type='password'
            name='inputPassword'
            placeholder='🔑비밀번호를 입력해주세요.'
            {...register('password', { required: true })}
          />
        </div>
        <button className='btnElement' type='submit'>
          LOGIN
        </button>
      </form>
      <button className='btnElement' onClick={logoutHandler}>
        LOGOUT
      </button>
      <button className='btnElement' onClick={deleteHandler}>
        DELETE
      </button>
    </div>
  );
}
