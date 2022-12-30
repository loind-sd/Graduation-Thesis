import { ADMIN_ROLE, AUTHOR_ROLE, USER_ROLE } from '../../utils/constant/CommonConstant';
import RequestService from '../../utils/request-service';
import {
    loginFailure,
    loginSuccess,
    logoutSuccess,
    logoutFailure,
    getUserSuccess,
    getUserFailure,
} from '../actions/auth-action';
import { reset, showLoader } from '../actions/_common-action';

export const login = (userData, history) => async (dispatch) => {
    try {
        const response = await RequestService.post("/auth/login", userData);
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("mail", response.data.mail);
        // localStorage.setItem("roles", response.data.roles[0]);
        dispatch(loginSuccess());
        if (response.data.roles[0] === "USER") {
            localStorage.setItem("static", USER_ROLE);
            history.push("/active-room");
        } else if (response.data.roles[0] === "AUTHOR") {
            localStorage.setItem("static", AUTHOR_ROLE);
            history.push("/author-manage/group-task");
        } else if (response.data.roles[0] === "ADMIN") {
            localStorage.setItem("static", ADMIN_ROLE);
            history.push("/admin-manage/dashboard");
        }

    } catch (error) {
        dispatch(loginFailure(error.response.data));
    }
};

export const logout = (history) => async (dispatch) => {
    try {
        let token = {};
        token["token"] = localStorage.getItem("token");
        await RequestService.post("/auth/logout", token, true);
        localStorage.removeItem("token");
        localStorage.removeItem("mail");
        localStorage.removeItem("static");
        dispatch(logoutSuccess());
        history.push("/login");

        window.location.reload(true);
    } catch (error) {
        localStorage.removeItem('token');
        dispatch(logoutFailure(error.response.data));
        history.push('/');
    }
};

export const beforeLogin = () => (dispatch) => {
    try {
        let token = {};
        if (
            localStorage.getItem('token') !== undefined &&
            localStorage.getItem('token') !== null &&
            localStorage.getItem('token') !== ''
        ) {
            token['token'] = localStorage.getItem('token');
            RequestService.post('/auth/logout', token, true);
            localStorage.removeItem('token');
            dispatch(logoutSuccess());
        }
    } catch (error) {
        dispatch(logoutFailure(error.response.data));
    }
};

export const getUserInfo = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.get('/user/info', null, true);
        dispatch(getUserSuccess(response.data.item));
    } catch (error) {
        localStorage.removeItem('token');
        dispatch(getUserFailure(error.response.data));
        window.location.reload(true);
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};
