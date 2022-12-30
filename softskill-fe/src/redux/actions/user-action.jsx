import {
    CHANGE_NAME_FAILURE,
    CHANGE_NAME_SUCCESS,
    CHANGE_NICKNAME_FAILURE,
    CHANGE_NICKNAME_SUCCESS,
    CHANGE_PASSWORD_FAILURE,
    CHANGE_PASSWORD_SUCCESS,
    FORGOT_PASSWORD_FAILURE,
    FORGOT_PASSWORD_SUCCESS,
} from "../action-types/user-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const forgotPasswordSuccess = (message) => ({
    type: FORGOT_PASSWORD_SUCCESS,
    payload: message,
});

export const forgotPasswordFailure = (error) => ({
    type: FORGOT_PASSWORD_FAILURE,
    payload: error,
});

export const changePasswordSuccess = (message) => ({
    type: CHANGE_PASSWORD_SUCCESS,
    payload: message,
});

export const changePasswordFailure = (errors) => ({
    type: CHANGE_PASSWORD_FAILURE,
    payload: errors,
});

export const changeNickNameSuccess = (message) => ({
    type: CHANGE_NICKNAME_SUCCESS,
    payload: message,
});

export const changeNickNameFailure = (errors) => ({
    type: CHANGE_NICKNAME_FAILURE,
    payload: errors,
});

export const changeNameSuccess = (message) => ({
    type: CHANGE_NAME_SUCCESS,
    payload: message,
});

export const changeNameFailure = (errors) => ({
    type: CHANGE_NAME_FAILURE,
    payload: errors,
});

export const resetFormData = () => ({
    type: FORM_RESET,
});