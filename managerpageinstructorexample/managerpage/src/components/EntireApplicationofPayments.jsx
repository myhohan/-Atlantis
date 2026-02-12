import React, { useState, useEffect } from 'react'; // 1. 상태 관리를 위한 Hook 추가
import axios from 'axios'; // 2. 서버 통신을 위한 axios 추가
import {
  ComposedChart, XAxis, YAxis, CartesianGrid, Tooltip,
  Legend, Area, Bar, Line, ResponsiveContainer
} from 'recharts';

export default function EntireApplicationofPayments({ isAnimationActive = true }) {
  
  // 3. 서버에서 받은 데이터를 담을 바구니(state)
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(true);

  // 4. 컴포넌트가 로드될 때 백엔드 API 호출
  useEffect(() => {
    const fetchData = async () => {
      try {
        // 백엔드 컨트롤러 주소 (포트와 경로 확인!)
        const response = await axios.get('http://localhost:8080/admin/payments/stats');
        setChartData(response.data); // 서버에서 온 List<PaymentManagement> 저장
        setLoading(false);
      } catch (error) {
        console.error("데이터 로드 실패:", error);
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  // 로딩 처리
  if (loading) return <div style={{textAlign: 'center', padding: '50px'}}>실시간 데이터를 가져오는 중...</div>;

  return (
    <div style={{ width: '100%', height: '500px' }}>
      <h3 style={{ textAlign: 'center' }}>통합 결제 현황 (실시간 연동)</h3>
      
      <ResponsiveContainer width="100%" height="100%">
        <ComposedChart
          data={chartData} // ★ 샘플 데이터(data) 대신 서버에서 받은 chartData 사용
          margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
        >
          <CartesianGrid stroke="#f5f5f5" />
          
          {/* X축: DTO의 paymentDate */}
          <XAxis 
            dataKey="paymentDate" 
            tick={{ fontSize: 12 }} 
            tickMargin={10} 
            scale="band" 
          />
          
          <YAxis 
            tick={{ fontSize: 12 }}
            tickFormatter={(value) => value.toLocaleString() + '원'} 
          />
          
          <Tooltip formatter={(value) => value.toLocaleString() + '원'} />
          <Legend />
          
          {/* Bar: 당일 매출액 (DTO 필드명 일치 확인) */}
          <Bar
            name="당일 매출액"
            dataKey="amount" 
            barSize={30} 
            fill="#413ea0" 
            isAnimationActive={isAnimationActive} 
          />

          {/* Bar: 결제 대기 금액 (DTO 필드명 일치 확인) */}
          <Bar
            name="결제 대기 금액"
            dataKey="amount" 
            barSize={30} 
            fill="#8884d8" 
            isAnimationActive={isAnimationActive} 
          />

          {/* Line: 서비스 임플에서 계산한 누적 완료 금액 */}
          <Line 
            name="누적 완료 추이"
            type="monotona" 
            dataKey="amount" 
            stroke="#ff7300" 
            strokeWidth={3}
            isAnimationActive={isAnimationActive} 
          />
        </ComposedChart>
      </ResponsiveContainer>
    </div>
  );
}