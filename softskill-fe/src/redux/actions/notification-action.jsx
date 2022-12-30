import { SEND_INVITE_FAILURE } from "../action-types/mail-action-types";
import {
    DELETE_NOTIFICATION_FAILURE,
    DELETE_NOTIFICATION_SUCCESS,
    GET_LIST_NOTIFICATION_FAILURE,
    GET_LIST_NOTIFICATION_SUCCESS,
    SEND_INVITE_SUCCESS,
} from "../action-types/notification-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const getListNotificationSuccess = (notify) => ({
    type: GET_LIST_NOTIFICATION_SUCCESS,
    payload: notify,
});

export const getListNotificationFailure = (error) => ({
    type: GET_LIST_NOTIFICATION_FAILURE,
    payload: error,
});

export const deleteNotificationSuccess = (notify) => ({
    type: DELETE_NOTIFICATION_SUCCESS,
    payload: notify,
});

export const deleteNotificationFailure = (error) => ({
    type: DELETE_NOTIFICATION_FAILURE,
    payload: error,
});


export const sendInviteSuccess = (success) => ({
    type: SEND_INVITE_SUCCESS,
    payload: success,
});

export const sendInviteFailure = (error) => ({
    type: SEND_INVITE_FAILURE,
    payload: error,
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
