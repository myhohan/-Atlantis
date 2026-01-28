import { Navigate, Outlet } from 'react-router-dom';
import useAuthStore from '../../stores/useAuthStore';

const AdminRoute = () => {
    const {isAuthenticated, user} = useAuthStore();

    // 1. 로그인이 안되어 있으면 -> 로그인 페이지로 쫒아냄
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    // 2. 로그인은 했는데 관리자가 아니면 -> 홈으로 쫒아냄
    if (user?.role !== 'ROLE_ADMIN') {
        alert("관리자 권한이 없습니다.");
        return <Navigate to="/" replace />;
    }

    // 3. 통과 -> 원래 가려던 페이지 보여줌
    return <Outlet />;
};

export default AdminRoute;