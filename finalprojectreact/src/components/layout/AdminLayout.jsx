import { Outlet, Link, useNavigate } from 'react-router-dom';
import useAuthStore );
from '../../stores/useAuthStore';

const AdminLayout = () => {
    const logout = useAuthStore((state) => state.logout
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div style={{ display: 'flex', minHeight: '100vh'}}>
            {/* 왼쪽 사이드바 */}
            <nav style={{ width: '250px', background: '#333', color: '#fff', padding: '20px'}}>
           <h2>관리자 페이지</h2>
           <ul style={{ listStyle: 'none', padding: 0}}>
            <li style={{margin: '15px 0'}}><Link to="/admin/dashboard" style={{color: '#fff'}}> 대시보드 </Link></li>
            <li style={{margin: '15px 0'}}><Link to="/admin/members" style={{color: '#fff'}}>회원 관리</Link></li>
            </ul>
            <button onClick={handleLogout} style={{ marginTop: '20px'}}> 로그아웃 </button>
            </nav>

        {/* 오른쪽 콘텐츠 영역 */}
        <main style={{ flex: 1, padding: '20px', background: '#f4f4f4'}}>
        <Outlet />
        {/* 여기에 Dashboard나 MemberList가 들어옴 */}
        </main>
        </div>
    );
};

export default AdminLayout;
