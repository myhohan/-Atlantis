import React, { useState, useEffect } from 'react'; // 상태 관리를 위해 추가
import axios from 'axios'; // 서버 통신을 위해 추가
import {
  ComposedChart, Line, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer
} from 'recharts';

export default function EntireApplicationofProducts({ isAnimationActive = true }) {
  // 1. 서버 데이터를 담을 상태 선언
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(true);

  // 2. 서버에서 데이터 가져오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        // 백엔드 주소 확인 (admin/payments/stats)
        const response = await axios.get('http://localhost:8080/admin/payments/stats');
        setChartData(response.data);
        setLoading(false);
      } catch (error) {
        console.error("데이터 로드 실패:", error);
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  if (loading) return <div style={{ textAlign: 'center', marginTop: '100px' }}>데이터 로딩 중...</div>;
  if (chartData.length === 0) return <div style={{ textAlign: 'center' }}>표시할 데이터가 없습니다.</div>;

  return (
    <div style={{ width: '100%', height: '500px' }}>
      <h3 style={{ textAlign: 'center' }}>통합 결제 및 접수 현황</h3>
      
      <ResponsiveContainer width="100%" height="100%">
        <ComposedChart
          data={chartData} // ★ 샘플 데이터 대신 서버에서 받은 chartData 사용
          margin={{ top: 20, right: 30, bottom: 20, left: 30 }}
        >
          <CartesianGrid stroke="#f5f5f5" />
          
          {/* X축: XML에서 정의한 별칭 paymentDate 사용 */}
          <XAxis 
            dataKey="paymentDate" 
            tick={{ fontSize: 12 }} 
          />
          
          {/* Y축: 금액과 건수 단위가 다르므로 주의 */}
          <YAxis tick={{ fontSize: 12 }} />
          
          <Tooltip formatter={(value) => value.toLocaleString()} />
          <Legend />
          
          {/* 매출액 (3000원씩 합산된 금액) */}
          <Bar
            name="매출액(원)"
            dataKey="amount" 
            fill="#413ea0" 
            barSize={20}
            isAnimationActive={isAnimationActive} 
          />

          {/* 접수 건수 */}
          <Line 
            name="접수 건수"
            type="monotone" 
            dataKey="totalCompletePayment" 
            stroke="#ff7300" 
            isAnimationActive={isAnimationActive} 
          />
        </ComposedChart>
      </ResponsiveContainer>
    </div>
  );
}