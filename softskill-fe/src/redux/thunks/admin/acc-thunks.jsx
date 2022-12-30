import RequestService from '../../../utils/request-service';

import {
    addAccountFailure,
    addAccountSuccess,
    deleteAccountFailure,
    deleteAccountSuccess,
    getAccountFailure,
    getAccountSuccess,
    updateAccountFailure,
    updateAccountSuccess,
} from '../../actions/admin/acc-action';
import { showLoader } from '../../actions/_common-action';

export const getAccount = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.get('/admin/account-manager', null, true);
        dispatch(getAccountSuccess(response.data.item));
    } catch (error) {
        dispatch(getAccountFailure(error.response));
    }
};

export const addAccount = (body) => async (dispatch) => {
    try {
        const response = await RequestService.post('/admin/account-manager', body, true);
        dispatch(addAccountSuccess(response.data.item));
    } catch (error) {
        dispatch(addAccountFailure(error.response));
    }
};

export const updateAccount = (account) => async () => {
    try {
        const response = await RequestService.put('/admin/account-manager', account, true);
        updateAccountSuccess(response.data.item);
    } catch (error) {
        updateAccountFailure(error.response);
    }
};

export const deleteAccounts = (ids) => async () => {
    try {
        const response = await RequestService.post('/admin/account-manager/delete', ids, true);
        deleteAccountSuccess(response.data.item);
    } catch (error) {
        deleteAccountFailure(error.response);
    }
};
