import {
    ADD_ACCOUNTS_FAILURE,
    ADD_ACCOUNTS_SUCCESS,
    DELETE_ACCOUNTS_FAILURE,
    DELETE_ACCOUNTS_SUCCESS,
    GET_ACCOUNTS_FAILURE,
    GET_ACCOUNTS_SUCCESS,
    UPDATE_ACCOUNTS_FAILURE,
    UPDATE_ACCOUNTS_SUCCESS,
} from '../../action-types/admin/acc-action-type';

export const getAccountSuccess = (accounts) => ({
    type: GET_ACCOUNTS_SUCCESS,
    payload: accounts,
});

export const getAccountFailure = (error) => ({
    type: GET_ACCOUNTS_FAILURE,
    payload: error,
});

export const addAccountSuccess = (accounts) => ({
    type: ADD_ACCOUNTS_SUCCESS,
    payload: accounts,
});

export const addAccountFailure = (error) => ({
    type: ADD_ACCOUNTS_FAILURE,
    payload: error,
});

export const updateAccountSuccess = (accounts) => ({
    type: UPDATE_ACCOUNTS_SUCCESS,
    payload: accounts,
});

export const updateAccountFailure = (error) => ({
    type: UPDATE_ACCOUNTS_FAILURE,
    payload: error,
});

export const deleteAccountSuccess = (accounts) => ({
    type: DELETE_ACCOUNTS_SUCCESS,
    payload: accounts,
});

export const deleteAccountFailure = (error) => ({
    type: DELETE_ACCOUNTS_FAILURE,
    payload: error,
});
