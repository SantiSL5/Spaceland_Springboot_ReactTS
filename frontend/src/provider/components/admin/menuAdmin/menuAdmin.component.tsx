import { NavLink } from "react-router-dom"
import "./style.css"

const MenuAdmin = () => {
    return (
        <>
            <nav className="menuAdmin container navbar navbar-expand-lg navbar-light">
                <div className="container">
                    <button type="button" className="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapseAdmin">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse container-fluid" id="navbarCollapseAdmin">
                        <div className="container-fluid navbar-nav justify-content-evenly">
                            <NavLink className={({isActive}) => isActive ? "active col-xl-1 col-lm-1 col-md-3 col-8 nav-item nav-link text-center": "inactive col-xl-1 col-lm-1 col-md-3 col-8 nav-item nav-link text-center" } to="/admin/users">Users</NavLink>
                        </div>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default MenuAdmin;