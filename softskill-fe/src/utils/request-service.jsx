import axios from 'axios';
import { environment } from '../utils/constant/environment';

class RequestService {

    get = (url, body, isAuthRequired = false, contentType = "application/json") => {
        return createRequest("GET", url, body, isAuthRequired, contentType);
    };

    post = (url, body, isAuthRequired = false, contentType = "application/json") => {
        return createRequest("POST", url, body, isAuthRequired, contentType);
    };

    put = (url, body, isAuthRequired = false, contentType = "application/json") => {
        return createRequest("PUT", url, body, isAuthRequired, contentType);
    };

    delete = (url, body, isAuthRequired = false, contentType = "application/json") => {
        return createRequest("DELETE", url, body, isAuthRequired, contentType);
    };
}

const createRequest = (method, url, body, isAuthRequired, contentType) => {
    return axios({
        method: method,
        url: environment.API_BASE_URL + url,
        data: body,
        headers: setHeader(isAuthRequired, contentType)
    });
};

const setHeader = (isAuthRequired, contentType) => {
    if (isAuthRequired) {
        axios.defaults.headers.common["Authorization"] = localStorage.getItem("token");
    } else {
        delete axios.defaults.headers.common['Authorization']
    }
    axios.defaults.headers.common["Content-Type"] = contentType
};

export default new RequestService();
