import { configureStore } from '@reduxjs/toolkit';
import memberReducer from './memberSlice';

export const store = configureStore({
  reducer: { member: memberReducer },
});
