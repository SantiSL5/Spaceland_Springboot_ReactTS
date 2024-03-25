import secrets from "../../secret";
import Api from "../api/api";

const userConsumer: any = {

    register: (data: any) => {
        return Api({
            method: "post",
            url: secrets.SPRINGBOOT_APP_URL + "/user/register",
            data: data,
        })
    },
    login: (data: any) => {
        return Api({
            method: "post",
            url: secrets.SPRINGBOOT_APP_URL + "/user/login",
            data: data,
        })
    },
    getUser: () => {
        return Api({
            method: "get",
            url: secrets.SPRINGBOOT_APP_URL + "/user/profile",
        })
    },
    logout: (data:any) => {
        return Api({
            method: "post",
            url: secrets.SPRINGBOOT_APP_URL + "/user/logout",
            data: data,
        })
    },
    getAll: () => {
        return Api({
            method: "get",
            url: secrets.SPRINGBOOT_APP_URL + "/admin/user",
        })
    },
    create: (data: any) => {
        return Api({
            method: "post",
            url: secrets.SPRINGBOOT_APP_URL + "/admin/user",
            data: data,
        })
    },
    update: (data: any) => {
        return Api({
            method: "put",
            url: secrets.SPRINGBOOT_APP_URL + "/admin/user/" + data.id,
            data: data,
        })
    },
    delete: (data: any) => {
        return Api({
            method: "delete",
            url: secrets.SPRINGBOOT_APP_URL + "/admin/user/" + data,
            data: data,
        })
    },
    deleteMany: (data: any) => {
        return Api({
            method: "delete",
            url: secrets.SPRINGBOOT_APP_URL + "/admin/user/deleteMany",
            data: data,
        })
    },
}

export default userConsumer;