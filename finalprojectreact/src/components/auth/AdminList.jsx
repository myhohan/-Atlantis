import React, { useEffect, useState } from 'react';
import adminClient from '../api/adminApi';

const AdminList = () => {
  const [admins, setAdmins] = useState([]);

  useEffect(() => {
    // 컴포넌트 마운트 시 데이터 조회
    adminClient.get('/adminAccountList')
      .then((res) => {
        setAdmins(res.data); // List<Member>가 JSON 배열로 들어옴
      })
      .catch((err) => {
        console.error('목록 조회 실패:', err);
      });
  }, []);

  return (
    <div>
      <h2>관리자 계정 목록</h2>
      <ul>
        {admins.map((admin) => (
          <li key={admin.memberNo}>
            {admin.memberName} ({admin.memberEmail})
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AdminList;