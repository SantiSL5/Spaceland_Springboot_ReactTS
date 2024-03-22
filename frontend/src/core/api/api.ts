import axios from "axios";
import consume from "../../provider/router/consumer";
import { jwtQueries, queryConsumer } from "../queries";

interface AxiosInterface {
    method: string,
    url: string,
    data?: any
}

const Api = ({ method, url, data }: AxiosInterface) => {
    const token = consume(queryConsumer.apiJwt, jwtQueries.getToken);
    let res: any;

    if (token) {
        try {
            res = axios({
                method: method,
                url: url,
                data: data,
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
        } catch (e) {
            return e;
        }
    } else {
        try {
            res = axios({
                method: method,
                url: url,
                data: data
            });
        } catch (e) {
            return e;
        }
    }

    axios.interceptors.response.use(
        (response: any) => response,
        (error: any) => {
            if (error.response.status === 403) {
                sessionStorage.removeItem("time")
                consume(queryConsumer.apiJwt, jwtQueries.removeToken);
                window.location.reload();
            }
            return Promise.reject(error);
        }
    );

    return res;
};


export default Api;