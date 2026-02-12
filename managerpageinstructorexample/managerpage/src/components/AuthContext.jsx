import axios from "axios";
import { createContext, useState } from "react";
// axiosApi 설정 파일 (baseURL: http://localhost:8080 이 설정되어 있어야 함)
import { axiosApi } from "../api/axiosAPI"; 

export const AuthContext = createContext();

// 전역 상태 제공자(Provider) 정의
export const AuthProvider = ({ children }) => {
  
  // 1. 상태값 정의
  const [user, setUser] = useState(() => {
    const storeUser = localStorage.getItem("userData");
    return storeUser ? JSON.parse(storeUser) : null;
  });
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // 2. 이메일 입력 핸들러 
  const changeInputEmail = (e) => {
    setEmail(e.target.value);
  }

  // 3. 패스워드 입력 핸들러
  const changeInputPw = (e) => {
    setPassword(e.target.value);
  }

  // 4. 로그인 처리 함수 (★ 핵심 수정 부분)
  const handleLogin = async(e) => {
    e.preventDefault(); // 새로고침 방지

    try {
        // [수정 1] 중복된 도메인 제거 (http://localhost:8080 삭제)
        // axiosApi에 baseURL이 설정되어 있다면 "/admin/login"만 적어야 합니다.
        const response = await axiosApi.post("/admin/login", { 
            memberEmail : email, 
            memberPw : password 
        });

        console.log("로그인 응답:", response); 
        const adminInfo = response.data;

        // [수정 2] 객체({})는 length 속성이 없습니다. 데이터 존재 여부로 확인
        if(!adminInfo) {
            alert("로그인 실패: 서버로부터 받은 정보가 없습니다.");
            return;
        }

        // 로그인 성공 시 상태 업데이트
        setUser(adminInfo);
        localStorage.setItem("userData", JSON.stringify(adminInfo));
        
        alert(`${adminInfo.memberNickname || '관리자'}님 환영합니다!`);

        // [타이머 설정] 1시간 뒤 자동 로그아웃
        setTimeout(() => {
            localStorage.removeItem("userData");
            setUser(null);
            alert("로그인 세션이 만료되었습니다. 재로그인 해주세요.");
            window.location.href = "/";
        }, 60 * 60 * 1000); // 1시간 (ms 단위)

    } catch (error) {
        // [수정 3] 에러 핸들링 (try-catch) 추가
        console.error("로그인 에러 발생:", error);

        // 서버가 401(Unauthorized)을 보냈을 때 (비밀번호 틀림 등)
        if (error.response && error.response.status === 401) {
            alert("이메일 혹은 비밀번호가 일치하지 않습니다.");
        } else {
            alert("로그인 중 오류가 발생했습니다. 관리자에게 문의하세요.");
        }
    }
  }

  // 5. 로그아웃 처리 함수
  const handleLogout = async() => {
    try {
      // 로그아웃 요청도 상대 경로로 변경
      const resp = await axiosApi.get("/admin/logout");

      if(resp.status === 200) {
        localStorage.removeItem("userData");
        setUser(null);
        alert("로그아웃 되었습니다.");
      }
    } catch(error) {
      console.error("로그아웃 중 문제 발생 : ", error);
    }
  }

  // 6. 전역으로 보낼 데이터 묶기
  const globalState = {
    user, 
    email, 
    password,
    changeInputEmail,
    changeInputPw,
    handleLogin,
    handleLogout
  }

  return (
    <AuthContext.Provider value={globalState}>
      {children}
    </AuthContext.Provider>
  )
}