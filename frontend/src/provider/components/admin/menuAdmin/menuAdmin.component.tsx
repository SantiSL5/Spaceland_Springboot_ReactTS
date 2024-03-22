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
                            Admin
                        </div>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default MenuAdmin;