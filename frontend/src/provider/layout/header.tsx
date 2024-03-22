import { NavLink, Link } from "react-router-dom";
import "./style.css"
import { useUsers } from "../hooks/useUsers";

interface HeaderProps {
    title: string,
}

const Header = (props: HeaderProps) => {
    const { user, isAdmin, logout } = useUsers();
    return (
        <>
            <nav className="navbar navbar-expand-lg navbar-light bg-white">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">{props.title}</Link>
                    <button type="button" className="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-nav navbar-collapse" id="navbarCollapse">
                        <div className="container-fluid navbar-nav justify-content-start">
                            <NavLink className={({isActive}) => isActive ? "active nav-item col-xl-1 col-lm-1 col-2 nav-link text-center rounded": "inactive col-xl-1 col-lm-1 col-2 nav-item nav-link text-center rounded" } to="/">Home</NavLink>
                            {isAdmin ? <NavLink className={({isActive}) => isActive ? "active col-xl-1 col-lm-1 col-2 nav-item nav-link text-center rounded": "inactive col-xl-1 col-lm-1 col-2 nav-item nav-link text-center rounded" } to="/admin">Admin</NavLink> : <></>}
                        </div>
                        <div className="navbar-nav">
                            {user
                                ?
                                <div className="navbar-collapse text-center">
                                    <NavLink to="/profile"><img src={user.data.photo} alt="pfp" className="img-fluid" width="60" /></NavLink>
                                    <NavLink className="nav-item nav-link text-center" to="/profile">{user.data.username}</NavLink>
                                    <span className="nav-item nav-link text-center btn" onClick={logout}>Logout</span>
                                </div>
                                : <NavLink className="nav-item nav-link text-center" to="/login">Login</NavLink>
                            }
                        </div>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default Header;