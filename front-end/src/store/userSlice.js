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

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {},
});

export const { setUser } = userSlice.actions;
export default userSlice.reducer;
