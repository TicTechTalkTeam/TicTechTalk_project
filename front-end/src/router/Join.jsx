import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

export default function Join() {
  const { register, handleSubmit } = useForm();
  const dispatch = useDispatch();
  const { join } = require('../store/userSlice');
  const navigate = useNavigate();
  const joinHandler = (data) => {
    dispatch(join(data));
    navigate('/login');
  };

  return (
    <div className='loginNjoinBox roundedRectangle darkModeElement'>
      <form className='formBox' onSubmit={handleSubmit(joinHandler)}>
        <div className='formInput'>
          <input
            type='text'
            name='inputEmail'
            placeholder='📧이메일'
            {...register('userEmail', { required: true })}
          />
          <input
            type='password'
            name='password'
            placeholder='🔑비밀번호'
            {...register('password', { required: true })}
          />
          <input
            type='password'
            name='inputPasswordCheck'
            placeholder='🔑비밀번호 확인'
          />
          <input
            type='text'
            name='inputPassword'
            placeholder='👤닉네임'
            {...register('userNick', { required: true })}
          />
        </div>
        <button className='btnElement' type='submit'>
          JOIN
        </button>
      </form>
    </div>
  );
}
