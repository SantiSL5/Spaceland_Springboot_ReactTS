import Home from "../pages/Home";
import Admin from "../pages/Admin";
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { NoAuthGuard } from "./guards/AuthGuard";
import Layout from "../layout";
import Login from "../pages/Login";
import AdminGuard from "./guards/AdminGuard";

const Router = () => {
    return (
        <BrowserRouter>
            <Layout>
                <Routes>
                    <Route path="/" element={<Home />}></Route>
                    <Route element={<NoAuthGuard />}>
                        <Route path="/login/" element={<Login />}></Route>
                    </Route>
                    <Route element={<AdminGuard />}>
                        <Route path="/admin/" >
                            <Route index element={<Admin />} />
                        </Route>
                    </Route>
                </Routes>
            </Layout>
            <ToastContainer />
        </BrowserRouter>
    );
}

export default Router;