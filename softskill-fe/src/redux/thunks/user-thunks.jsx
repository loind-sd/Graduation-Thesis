import RequestService from '../../utils/request-service';
import {
    forgotPasswordSuccess,
    forgotPasswordFailure,
    changePasswordSuccess,
    changePasswordFailure,
    changeNickNameFailure,
    resetFormData,
    changeNameSuccess,
    changeNameFailure,
} from '../actions/user-action';
import { changeNickNameSuccess } from './../actions/user-action';

export const forgotPassword = (email) => async (dispatch) => {
    try {
        const response = await RequestService.post('/user/forgot', email);
        dispatch(forgotPasswordSuccess(response.data));
    } catch (error) {
        dispatch(forgotPasswordFailure(error.response.data));
    }
};

export const changePassword = (data) => async (dispatch) => {
    try {
        const response = await RequestService.put('/user/change-password', data, true);
        dispatch(changePasswordSuccess(response.data.status));
    } catch (error) {
        dispatch(changePasswordFailure(error.response.data));
    }
};

export const changeNickName = (data) => async (dispatch) => {
    try {
        const response = await RequestService.put('/user/change-nickname', data, true);
        dispatch(changeNickNameSuccess(response.data.status));
    } catch (error) {
        dispatch(changeNickNameFailure(error.response.data.status));
    }
};

export const changeName = (data) => async (dispatch) => {
    try {
        const response = await RequestService.put('/user/change-name', data, true);
        dispatch(changeNameSuccess(response.data.status));
    } catch (error) {
        dispatch(changeNameFailure(error.response.data.status));
    }
};
export const resetFormUser = () => async (dispatch) => {
    dispatch(resetFormData());
};
