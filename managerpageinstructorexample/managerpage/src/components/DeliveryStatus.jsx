import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Pie, PieChart, Cell, Tooltip, ResponsiveContainer, Legend } from 'recharts';

// 상태별 색상 매핑 (DTO의 transportStatus 값과 일치해야 함)
const STATUS_COLORS = {
  '접수대기': '#8884d8', 
  '배송중': '#00C49F',   
  '배송완료': '#FFBB28', 
  '처리중': '#FF8042',   // 👈 SQL 가데이터에 있는 '처리중' 상태 추가
  '반품/취소': '#eb2f06' 
};

export default function DeliveryStatus({ isAnimationActive = true }) {
  const [chartData, setChartData] = useState([]); // 서버 데이터를 담을 상태
  const [loading, setLoading] = useState(true);

 useEffect(() => {
  axios.get('http://localhost:8080/admin/delivery/stats') 
    .then(response => {
      // JSON.stringify를 쓰면 객체 내부가 자세히 보입니다.
      console.log("데이터 상세 확인:", JSON.stringify(response.data)); 
      setChartData(response.data);
      setLoading(false);
    })
    .catch(error => {
      console.error("데이터 로드 실패:", error);
      setLoading(false);
    });
}, []);

  if (loading) return <div style={{ textAlign: 'center' }}>데이터 로딩 중...</div>;
  if (chartData.length === 0) return <div style={{ textAlign: 'center' }}>데이터가 없습니다.</div>;

  return (
    <div style={{ width: '100%', height: '400px' }}>
      <h3 style={{ textAlign: 'center' }}>배송 현황 (실시간)</h3>
      <ResponsiveContainer width="100%" height="90%">
        <PieChart>
          <Pie
  data={chartData}
  cx="50%" cy="50%"
  innerRadius={60}
  outerRadius={80}
  paddingAngle={5}
  dataKey="totalCount"      // 👈 XML 별칭 "totalCount"와 일치 (정상)
  nameKey="transportStatus" // 👈 "status" 대신 "transportStatus"로 수정!
  isAnimationActive={isAnimationActive}
>
  {chartData.map((entry, index) => (
    <Cell 
      key={`cell-${index}`} 
      // XML 별칭이 "transportStatus"이므로 entry.transportStatus로 접근
      fill={STATUS_COLORS[entry.transportStatus] || '#ccc'} 
    />
  ))}
</Pie>
          <Tooltip formatter={(value) => `${value}건`} />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
}