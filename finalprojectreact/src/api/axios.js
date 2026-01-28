//Axios 설정 (Interceptor 등)
import axios from 'axios';
import useAuthStore from '../stores/useAuthStore';

// 스프링부트 주소 (포트 확인하세요!)
const BASE_URL = 'http://localhost:8080/api';

export const api = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// [요청 인터셉터] 모든 요청을 보내기 직전에 "토큰"을 헤더에 끼워넣음
api.interceptors.request.use((config) => {
    const token = useAuthStore.getState().token; // 스토어에서 토큰 꺼냄
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;

    }
    return config;
});

export default api;