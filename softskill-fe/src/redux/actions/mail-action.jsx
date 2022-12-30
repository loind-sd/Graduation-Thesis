import {
    SEND_RESET_PASSWORD_FAILURE,
    SEND_RESET_PASSWORD_SUCCESS,
    SEND_INVITE_FAILURE,
    SEND_INVITE_SUCCESS,
    SEND_THANK_FAILURE,
    SEND_THANK_SUCCESS,
} from '../action-types/mail-action-types';

export const sendInviteSuccess = (mails) => ({
    type: SEND_INVITE_SUCCESS,
    payload: mails,
});

export const sendInviteFailure = (error) => ({
    type: SEND_INVITE_FAILURE,
    payload: error,
});

export const sendThankSuccess = (mails) => ({
    type: SEND_THANK_SUCCESS,
    payload: mails,
});

export const sendThankFailure = (error) => ({
    type: SEND_THANK_FAILURE,
    payload: error,
});

export const sendResetPasswordSuccess = (mails) => ({
    type: SEND_RESET_PASSWORD_SUCCESS,
    payload: mails,
});

export const sendResetPasswordFailure = (error) => ({
    type: SEND_RESET_PASSWORD_FAILURE,
    payload: error,
});
