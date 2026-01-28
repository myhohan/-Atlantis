import {create} from 'zustand';
import {persist} from 'zustand/middleware';

// persist : 새로고침 해도 로그인이 풀리지 않게 로컬스토리지에 저장해주는 기능
const useAuthStore = create(
    persist(
        (set) => ({
            user: null, // 사용자 정보 ({name : '홍길동', role: 'ROLE_ADMIN' })
            token: null, // JWT 토큰
            isAuthenticated: false, // 로그인 여부

            // 로그인 성공 시 실행
            login: (userData, token) => set({
                user: userData,
                token: token,
                isAuthenticated: true
            }),

            // 로그아웃 시 실행
            logout: () => set({
                user : null,
                token : null,
                isAuthenticated: false
            }),
        }),
        {
            name: 'auth-storage', // 로컬스토리지에 저장될 이름
        }
    )
);

export default useAuthStore;