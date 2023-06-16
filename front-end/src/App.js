import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './style.css';

// 컴포넌트
import Layout from './router/Layout';
import Main from './router/Main';
import Board from './router/Board';
import BoardView from './router/BoardView';
import Comment from './router/Comment';
import Mypage from './router/Mypage';
import MypageUpdate from './router/MypageUpdate';
import MypageNav from './router/MypageNav';
import BoardWrite from './router/BoardWrite';
import Login from './router/Login';
import Join from './router/Join';

function App() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <Layout />,
      children: [
        {
          path: '',
          element: <Main />,
        },
        {
          path: 'login',
          element: <Login />,
        },
        {
          path: 'join',
          element: <Join />,
        },
      ],
    },
    {
      path: '/boards',
      element: <Layout />,
      children: [
        {
          path: 'forum',
          element: <Board />,
        },
        {
          path: 'qna',
          element: <Board />,
        },
        {
          path: 'reference',
          element: <Board />,
        },
        {
          path: ':postno',
          element: (
            <>
              <BoardView />
              <Comment />
            </>
          ),
        },
        {
          path: 'write',
          element: <BoardWrite />,
        },
      ],
    },
    {
      path: '/mypage',
      element: <Layout />,
      children: [
        {
          path: '',
          element: (
            <>
              <MypageNav />
              <Mypage />
            </>
          ),
        },
        {
          path: ':userno',
          element: (
            <>
              <MypageNav />
              <MypageUpdate />
            </>
          ),
        },
        {
          path: 'bookmark',
          element: (
            <>
              <MypageNav />
              <Board />
            </>
          ),
        },
        {
          path: 'mypost',
          element: (
            <>
              <MypageNav />
              <Board />
            </>
          ),
        },
      ],
    },
    {
      path: '/chat',
      element: <Layout />,
    },
  ]);

  return (
    <div className='App'>
      <RouterProvider router={router} />
    </div>
  );
}

export default App;
