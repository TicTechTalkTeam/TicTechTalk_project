import axios from 'axios';
import { getCookie, setCookie } from './Cookie';

// 서버단 주소 설정
const apiUrl = 'http://localhost:8080/';

const Instance = axios.create({
  baseURL: apiUrl,
  timeout: 1000,
});

// 요청 쌔비지
Instance.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem('accessToken');

  // 액세스 토큰이 없으면
  if (!accessToken) {
    // header에 null 담아 보내기
    config.headers['authorization'] = null;
    config.headers['refreshToken'] = null;
    return config;
  }

  // 액세스 토큰이 있으면
  if (accessToken) {
    config.headers['authorization'] = `Bearer ${accessToken}`;
    return config;
  }
});

// 응답 쌔비지
Instance.interceptors.response.use(
  // fullfiled
  async (res) => {
    console.log(res.header);
    console.log(res.data);
    const accessToken = res.headers['authorization'].split(' ')[1];
    const refreshToken = res.headers['refreshtoken'];
    console.log(accessToken);
    localStorage.setItem('accessToken', accessToken);
    setCookie('refreshToken', refreshToken);
    return res;
  },

  // rejected
  async (err) => {
    const originalConfig = err.config;
    // 토큰 만료 오류일때
    if (err.response.data.code === 'TOKEN-001') {
      const accessToken = localStorage.getItem('accessToken');
      const refreshToken = getCookie('refreshToken');
      try {
        const res = await axios.post(apiUrl + 'users/reissue', null, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            RefreshToken: refreshToken,
          },
        });
        if (res) {
          const accessToken = (res.headers['authorization'] || '').split(
            ' '
          )[1];
          const refreshToken = res.headers['refreshtoken'];
          localStorage.setItem('accessToken', accessToken);
          setCookie('refreshToken', refreshToken);
          return await Instance.request(originalConfig);
        }
      } catch (err) {
        console.log('토큰 재발급 오류 발생');
      }
    }
  }
);

export default Instance;
