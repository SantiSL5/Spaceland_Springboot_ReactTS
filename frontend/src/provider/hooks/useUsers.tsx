import consume from '../router/consumer';
import { useEffect, useState } from 'react'
import { queryConsumer, userQueries, jwtQueries } from "../../core/queries";
import { toast } from 'react-toastify'
import { useNavigate } from "react-router-dom";

export function useUsers() {

    const navigate = useNavigate();
    const [user, setUser]: any = useState(undefined);
    const [isAdmin, setIsAdmin]: any = useState(undefined);
    const [loading, setLoading]: any = useState(true);
    const [token, setToken]: any = useState(consume(queryConsumer.apiJwt, jwtQueries.getToken));

    useEffect(() => {
        if (token) {
            consume(queryConsumer.apiUser, userQueries.getUser).then((res: any) => {
                setUser(res);
                if (res.data.role === "ROLE_ADMIN") {
                    setIsAdmin(true);
                }

                setLoading(false);
            }).catch((e: any) => {
                if (e.response.status === 403) {
                    toast.error("Your session has expired, logging out...", { theme: "dark" })
                    consume(queryConsumer.apiUser, userQueries.logout, {"token":token}).then((res: any) => {
                        setUser(null);
                        setToken(null);
                        consume(queryConsumer.apiJwt, jwtQueries.removeToken);
                        window.location.reload();
                    }).catch(() => {
                        toast.error("Something went wrong", { theme: "dark" })
                    })
                }
            })
        } else {
            setLoading(false);
        }

        // if (token) {
        // const interval = setInterval(() => {
        //     if (sessionStorage.getItem("time")) {
        //         sessionStorage.setItem("time", Number(sessionStorage.getItem("time")) + Number(1))
        //         if (sessionStorage.getItem("time") >= 10) {
        //             refreshToken();
        //         }
        //     } else {
        //         sessionStorage.setItem("time", Number(1))
        //     }
        // }, 60000);


        // return () => clearInterval(interval);
        // }
    }, [token, isAdmin]);


    const registerH = ((data: any) => {
        consume(queryConsumer.apiUser, userQueries.register, data).then((res: any) => {
            setUser(res.data.user);
            setToken(res.data.token);
            consume(queryConsumer.apiJwt, jwtQueries.setToken, res.data.token);
            toast.success("User registered successfully, logging in...", { theme: "dark" })
            navigate('/');
            window.location.reload();
        }).catch(() => {
            toast.error("Email is already taken", { theme: "dark" })
        })
    })

    const login = ((data: any) => {
        consume(queryConsumer.apiUser, userQueries.login, data).then((res: any) => {
            setUser(res.data.user);
            setToken(res.data.token);
            consume(queryConsumer.apiJwt, jwtQueries.setToken, res.data.token);
            toast.success("Logging in...", { theme: "dark" })
            navigate('/');
            window.location.reload();
        }).catch(() => {
            toast.error("Wrong email or password", { theme: "dark" })
        })
    })


    const logout = (() => {
        consume(queryConsumer.apiUser, userQueries.logout, {"token":token}).then((res: any) => {
            setUser(null);
            setToken(null);
            consume(queryConsumer.apiJwt, jwtQueries.removeToken);
            window.location.reload();
        }).catch(() => {
            toast.error("Something went wrong", { theme: "dark" })
        })
    })

    const refreshToken = ((data: any) => {

    })

    return { user, token, isAdmin, loading, setIsAdmin, registerH, login, logout, refreshToken };

}
