import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { removeCookie } from '../util/Cookie';
import Instance from '../util/axiosConfig';

let initialState = {};

export const join = createAsyncThunk(
  'users/join',
  async (userData, thunkAPI) => {
    try {
      const res = await Instance.post('users/join', userData);
      alert('회원가입이 완료되었습니다.');
      return thunkAPI.fulfillWithValue(res.data);
    } catch (err) {
      console.log(err);
    }
  }
);

export const login = createAsyncThunk(
  'users/login',
  async (userData, thunkAPI) => {
    try {
      const res = await Instance.post('users/login', userData);
      console.log('로그인 성공');
      return thunkAPI.fulfillWithValue(res.data);
    } catch (err) {
      console.log(err);
      throw err;
    }
  }
);

export const logout = createAsyncThunk(
  'users/logout',
  async (payload, thunkAPI) => {
    try {
      const res = await Instance.post('users/logout');
      localStorage.removeItem('accessToken');
      removeCookie('refreshToken');
      console.log('로그아웃 성공');
      return thunkAPI.fulfillWithValue(res.data);
    } catch (err) {
      console.log(err);
    }
  }
);

export const deleteUser = createAsyncThunk(
  'users/deleteUser',
  async (payload, thunkAPI) => {
    try {
      const res = await Instance.post('users/delete');
      localStorage.removeItem('accessToken');
      removeCookie('refreshToken');
      console.log('회원탈퇴 성공');
      return thunkAPI.fulfillWithValue(res.data);
    } catch (err) {
      console.log(err);
    }
  }
);

export const getInfo = createAsyncThunk(
  'users/getInfo',
  async (payload, thunkAPI) => {
    try {
      const res = await Instance.get('mypage/info');
      return thunkAPI.fulfillWithValue(res.data);
    } catch (err) {
      console.log(err);
    }
  }
);

export const updateInfo = createAsyncThunk(
  'users/updateInfo',
  async (payload, thunkAPI) => {
    try {
      const res = await Instance.post('mypage/update', payload);
      if (res.data === 'NIKCNAME_DUPLICATED') {
        alert('중복된 닉네임입니다.');
        return;
      }
      alert('회원정보 수정이 완료되었습니다.');
      return thunkAPI.fulfillWithValue(res.data);
    } catch (err) {
      console.log(err);
    }
  }
);

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {},
});

export const { setUser } = userSlice.actions;
export default userSlice.reducer;
