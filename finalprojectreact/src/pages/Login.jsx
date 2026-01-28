import React, { useState } from 'react';
import adminClient from '../api/adminApi'; // 위에서 만든 파일 import

const AdminLogin = () => {
  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');

  const handleLogin = async () => {
    try {
      // Spring Boot의 @RequestBody Member 구조에 맞춰 객체 전송
      const response = await adminClient.post('/login', {
        memberEmail: email, // Member DTO 필드명과 일치해야 함
        memberPw: pw
      });

      if (response.status === 200) {
        alert(`${response.data.memberName} 관리자님 환영합니다.`);
        console.log('로그인 정보:', response.data);
        // 이후 메인 페이지로 이동 로직 (useNavigate 등)
      }
    } catch (error) {
      console.error('로그인 실패:', error);
      alert('아이디 또는 비밀번호를 확인해주세요.');
    }
  };

  return (
    <div>
      <input type="text" onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
      <input type="password" onChange={(e) => setPw(e.target.value)} placeholder="Password" />
      <button onClick={handleLogin}>로그인</button>
    </div>
  );
};

export default AdminLogin;