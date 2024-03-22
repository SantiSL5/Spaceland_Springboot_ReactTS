import { Navigate, Outlet } from "react-router-dom"
import Spinner from "../../components/spinner/spinner.component";
import { useUsers } from "../../hooks/useUsers";

export function NoAuthGuard() {
    const { user, loading } = useUsers();
    
    if (!loading) {
        return !user ? <Outlet /> : <Navigate to="/" />
    }
    return <Spinner />
}

export function AuthGuard() {
    const { user, loading } = useUsers();
    
    if (!loading) {
        return user ? <Outlet /> : <Navigate to="/login" />
    }
    return <Spinner />
}
