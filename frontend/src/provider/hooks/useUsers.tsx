import consume from '../router/consumer';
import { useEffect, useState } from 'react'
import { queryConsumer, userQueries, jwtQueries } from "../../core/queries";
import { toast } from 'react-toastify'
import { useNavigate } from "react-router-dom";

export function useUsers() {

    const navigate = useNavigate();
    const [user, setUser]: any = useState(undefined);
    const [users, setUsers]: any = useState(undefined);
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
        }).catch((res:any) => {
            console.log(res.response.data);
            if (res.response.data != null) {
                toast.error(res.response.data, { theme: "dark" })
            } else {
                toast.error("Something went wrong", { theme: "dark" })
            }
        })
    })

    const login = ((data: any) => {
        consume(queryConsumer.apiUser, userQueries.login, data).then((res: any) => {
            console.log(data);
            setUser(res.data.user);
            setToken(res.data.token);
            consume(queryConsumer.apiJwt, jwtQueries.setToken, res.data.token);
            toast.success("Logging in...", { theme: "dark" })
            navigate('/');
            window.location.reload();
        }).catch((res:any) => {
            console.log(res.response.data);
            if (res.response.data != null) {
                toast.error(res.response.data, { theme: "dark" })
            } else {
                toast.error("Something went wrong", { theme: "dark" })
            }
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

    const getAllUsers = (() => {
        consume(queryConsumer.apiUser, userQueries.getAllUsers).then((res: any) => {
            setUsers(res.data)
        }).catch((e: any) => {
            console.log(e);
        })
    })

    const createUser = ((data: any) => {
        if (data.password.length === 0) delete data.password
        consume(queryConsumer.apiUser, userQueries.createUser, data).then((res: any) => {
            
            const aux = [...users, res.data]
            toast.success("Created successfully", { theme: "dark" })
            
            setUsers(aux)
        }).catch((res: any) => {
            if (res.response.data != null) {
                toast.error(res.response.data, { theme: "dark" })
            } else {
                toast.error("Something went wrong", { theme: "dark" })
            }
        })
    })

    const deleteUser = ((data: any) => {
        consume(queryConsumer.apiUser, userQueries.deleteUser, data).then((res: any) => {
            toast.success("Deleted successfully", { theme: "dark" })
            setUsers(users.filter((item: any) => data !== item.id));
        }).catch((res: any) => {
            if (res.response.data != null) {
                toast.error(res.response.data, { theme: "dark" })
            } else {
                toast.error("Something went wrong", { theme: "dark" })
            }
        })
    })

    const deleteManyUsers = ((data: any) => {
        let req: any = { ids: [] };
        data.map((e: any) => {
            return req.ids.push(e.id);
        })
        consume(queryConsumer.apiUser, userQueries.deleteManyUsers, req).then((res:any) => {
            const array = users.filter((x: any) => {
                return req.ids.indexOf(x.id) < 0;
            });
            setUsers(array);
            toast.success("Deleted successfully", { theme: "dark" })
        }).catch((res: any) => {
            if (res.response.data != null) {
                toast.error(res.response.data, { theme: "dark" })
            } else {
                toast.error("Something went wrong", { theme: "dark" })
            }
        })
    })

    const updateUser = ((data: any) => {
        consume(queryConsumer.apiUser, userQueries.updateUser, data).then((res: any) => {
            let aux = [...users];
            let index = aux.findIndex((x: any) => x.id === data.id);
            aux[index] = res.data;
            setUsers(aux)
            toast.success("Updated successfully", { theme: "dark" })
        }).catch((res: any) => {
            if (res.response.data != null) {
                toast.error(res.response.data, { theme: "dark" })
            } else {
                toast.error("Something went wrong", { theme: "dark" })
            }
        })
    })


    return { user, users, token, isAdmin, loading, setIsAdmin, registerH, login, logout, refreshToken, getAllUsers, createUser, updateUser, deleteUser, deleteManyUsers };
}
