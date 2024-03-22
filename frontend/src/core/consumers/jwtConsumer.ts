const userConsumer: any = {
    get: () => {
        return localStorage.getItem("token");
    },
    set: (token: string) => {
        localStorage.setItem("token", token);
    },
    remove: () => {
        localStorage.removeItem("token");
    },
}

export default userConsumer;