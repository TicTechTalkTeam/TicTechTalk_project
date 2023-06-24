import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { deleteUser, getInfo, updateInfo } from '../store/userSlice';
import { useForm } from 'react-hook-form';

export default function MypageUpdate() {
  const dispatch = useDispatch();
  const [userInfo, setUserInfo] = useState({});
  const { register, handleSubmit } = useForm();
  const navigate = useNavigate();

  useEffect(() => {
    async function getList() {
      const res = await dispatch(getInfo());
      setUserInfo(res.payload);
    }
    getList();
  }, []);

  const infoUpdateHandler = async (data) => {
    try {
      const res = await dispatch(updateInfo(data));
    } catch (err) {
      console.log(err);
    }
    navigate('/mypage');
  };

  const deleteHandler = () => {
    dispatch(deleteUser());
    sessionStorage.removeItem('TTT_login', 'login');
    navigate('/');
  };

  return (
    <div className='myPageBox roundedRectangle darkModeElement'>
      <div className='profileInfo'>{userInfo.userEmail}</div>
      <div className='profileInfo'>{userInfo.point} POINT</div>
      <form onSubmit={handleSubmit(infoUpdateHandler)}>
        <input
          defaultValue={userInfo.userNick}
          className='darkModeElement'
          placeholder='닉네임을 변경해주세요!'
          {...register('userNick', { required: true })}
        />
        <textarea
          className='darkModeElement'
          defaultValue={userInfo.userInfo}
          placeholder='자기소개를 적어주세요!🫥'
          {...register('userInfo')}
        ></textarea>
        <button className='btnElement' type='submit'>
          프로필 수정
        </button>
      </form>
      <button
        className='btnElement'
        onClick={deleteHandler}
        style={{ backgroundColor: '#8a8a8a' }}
      >
        회원탈퇴
      </button>
    </div>
  );
}
