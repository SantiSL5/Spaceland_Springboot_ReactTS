import { Navigate, Outlet } from "react-router-dom"
import Spinner from "../../components/spinner/spinner.component";
import { useUsers } from "../../hooks/useUsers";

function AdminGuard() {
    const { isAdmin, loading } = useUsers();

    if (!loading) {
        return !isAdmin ? <Navigate to="/login" /> : <Outlet />;
    }
    return <Spinner />;
}

export default AdminGuard;