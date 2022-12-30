import requestService from '../../utils/request-service';
import {
    deleteNotificationFailure,
    deleteNotificationSuccess,
    getListNotificationFailure,
    getListNotificationSuccess,
    sendInviteFailure,
    sendInviteSuccess,
} from '../actions/notification-action';
import { reset, showLoader } from '../actions/_common-action';

export const getListNotification = (data) => async (dispatch) => {
    try {
        // dispatch(showLoader());
        const response = await requestService.get('/notification/all?page=' + data, null, true);
        dispatch(getListNotificationSuccess(response.data));
    } catch (error) {
        dispatch(getListNotificationFailure(error.response.data));
    }
};

export const deleteNotification = (data) => async (dispatch) => {
    try {
        const response = await requestService.delete('/notification?id=' + data, null, true);
        dispatch(deleteNotificationSuccess(response.data));
    } catch (error) {
        dispatch(deleteNotificationFailure(error.response.data));
    }
};

export const sendInvite = (data) => async (dispatch) => {
    try {
        const response = await requestService.post('/notification', data, true);
        dispatch(sendInviteSuccess(response.data.status));
    } catch (error) {
        dispatch(sendInviteFailure(error.response.data.status));
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};
