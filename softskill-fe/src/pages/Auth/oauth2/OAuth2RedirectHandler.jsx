import React from 'react';
import { Redirect } from 'react-router-dom'
import { ADMIN_ROLE, AUTHOR_ROLE, USER_ROLE } from '../../../utils/constant/CommonConstant';

const OAuth2RedirectHandler = () => {
    const url = window.location;
    const token = new URLSearchParams(url.search).get('token');
    const role = new URLSearchParams(url.search).get('role');
    if (token) {
        localStorage.setItem("token", token);
        if (role == "USER") {
            localStorage.setItem("static", USER_ROLE);
            return <Redirect to="/active-room" />
        } else if (role == "AUTHOR") {
            localStorage.setItem("static", AUTHOR_ROLE);
            return <Redirect to="/author-manage/group-task" />
        } else if (role == "ADMIN") {
            localStorage.setItem("static", ADMIN_ROLE);
            return <Redirect to="/admin-manage/dashboard" />
        }
    }

    return <Redirect to="/login" />
};

export default OAuth2RedirectHandler;
