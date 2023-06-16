import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';
import { setCookie } from '../util/Cookie';

const initialState = {
  userNo: 0,
  userNick: '비회원',
  userPic: null,
};

export const login = createAsyncThunk('users/login', async (userData) => {
  try {
    const res = await axios.post('http://localhost:8080/users/login', userData);
    console.log(res.data);
    const accessToken = res.headers['authorization'].split(' ')[1];
    const refreshToken = res.headers['refreshtoken'];
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    localStorage.setItem('accessToken', accessToken);
    setCookie('refreshToken', refreshToken);
    // return res.data;
  } catch (err) {
    console.log(err);
  }
});

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.userNo = action.payload.userNo;
      state.userNick = action.payload.userNick;
    },
  },
});

export const { setUser } = userSlice.actions;
export default userSlice.reducer;
