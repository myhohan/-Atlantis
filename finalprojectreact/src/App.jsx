import { Routes, Route } from 'react-router-dom'
import Login from './pages/Login';
import Dashboard from './pages/admin/Dashboard';
import AdminLayout from './components/layout/AdminLayout';
import AdminRoute from './components/auth/AdminRoute';
import './App.css'

function App() {
  return 
  (
    <Routes>
      {/* 1. 로그인 페이지 */}
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<Login />} /> {/* 기본 경로도 로그인으로 */}
      
      {/* 2. 관리자 전용 구역 (보안 적용) */}
      <Routes element={<AdminRoute />}>
      <Route path="/admin" element={<AdminLayout />}>
      <Route path="dashboard" elements={<Dashboard />}
      {/* 여기에 회원관리, 배송관리 등 계속 추가하면 됨 */}
      {/* <Route path="members" element={<MemberList />} /> */}

      </Route>
      </Route>
      </Routes>
  );
}

export default App;