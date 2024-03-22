import { useState } from "react";
// import CustomButton from "../../components/button.component";
// import consume from "../../router/consumer";
// import { queryConsumer, userQueries } from "../../../core/queries";
import "./style.css"
import RegisterForm from "../../components/login/register.component";
import LoginForm from "../../components/login/login.component";
import { useUsers } from "../../hooks/useUsers";


const Login = () => {
    const { registerH, login } = useUsers();
    const [op, setOp]: any = useState("register");

    // const changeForm = (data: any, op: string) => {
    //     setOp(op);
    // }
    return (
        <div className="container dflex justify-content-center text-center mt-5 bg-white pb-5 pt-5">
            <div className="continer">
                {op === "register" ? 
                    <div>
                        <button type="submit" className="menu-sel border-menu col-3">Register</button> 
                        <button type="submit" className="menu-notsel border-menu col-3" onClick={() => {
                            setOp("login")
                        }}>Login</button> 
                    </div>
                        
                    : <div>
                        <button type="submit" className="menu-notsel border-menu col-3" onClick={() => {
                            setOp("register")
                        }}>Register</button> 
                        <button className="menu-sel border-menu col-3">Login</button> 
                    </div>
                }
            </div>

            {op === "register" ? <RegisterForm registerH={registerH}/> : <LoginForm login={login}/> }
        </div>
    );
}

export default Login;