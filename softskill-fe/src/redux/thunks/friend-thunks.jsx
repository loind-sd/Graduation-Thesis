import requestService from '../../utils/request-service';
import {
    addFriendFailure,
    addFriendSuccess,
    getFriendsByTaskFailure,
    getFriendsByTaskSuccess,
    getFriendSuccess,
    getFriendsRecentSuccess,
    showLoader,
    acceptFriendSuccess,
    acceptFriendFailure,
    denyFriendSuccess,
    denyFriendFailure,
    searchFriendSuccess,
    searchFriendFailure,
    getFriendEvenSuccess,
    deleteFriendSuccess,
    deleteFriendFailure,
} from '../actions/friend-action';
import { reset } from '../actions/_common-action';

export const getFriendList = () => async (dispatch) => {
    // dispatch(showLoader());
    const response = await requestService.get('/friend', null, true);
    dispatch(getFriendSuccess(response.data.item));
};

export const getFriendListEvenRequest = () => async (dispatch) => {
    // dispatch(showLoader());
    const response = await requestService.get('/friend/plus', null, true);
    dispatch(getFriendEvenSuccess(response.data.item));
};

export const addFriend = (data) => async (dispatch) => {
    try {
        const response = await requestService.post('/friend', data, true);
        dispatch(addFriendSuccess(response.data));
    } catch (error) {
        dispatch(addFriendFailure(error.response));
    }
};

export const acceptFriendRequest = (data) => async (dispatch) => {
    try {
        const response = await requestService.put('/friend', data, true);
        dispatch(acceptFriendSuccess(response.data));
    } catch (error) {
        dispatch(acceptFriendFailure(error.response));
    }
};

export const denyFriendRequest = (data) => async (dispatch) => {
    try {
        const response = await requestService.put('/friend', data, true);
        dispatch(denyFriendSuccess(response.data));
    } catch (error) {
        dispatch(denyFriendFailure(error.response));
    }
};

export const getFriendsByTask = (data) => async (dispatch) => {
    try {
        const response = await requestService.post('/roomTasks/othersNotStart', data, true);
        dispatch(getFriendsByTaskSuccess(response.data));
    } catch (error) {
        dispatch(getFriendsByTaskFailure(error.response));
    }
};

export const getFriendsByTaskDoingOrDone = (data) => async (dispatch) => {
    try {
        const response = await requestService.post('/roomTasks/others', data, true);
        dispatch(getFriendsByTaskSuccess(response.data));
    } catch (error) {
        dispatch(getFriendsByTaskFailure(error.response));
    }
};

export const getFriendsByTaskFavorite = (data) => async (dispatch) => {
    try {
        const response = await requestService.post('/roomTasks/othersFavourite', data, true);
        dispatch(getFriendsByTaskSuccess(response.data));
    } catch (error) {
        dispatch(getFriendsByTaskFailure(error.response));
    }
};

export const getFriendsRecent = () => async (dispatch) => {
    const response = await requestService.get('/friend/recent', null, true);
    dispatch(getFriendsRecentSuccess(response.data.item));
};

export const getFriends = () => async (dispatch) => {
    const response = await requestService.get('/friend', null, true);
    dispatch(getFriendSuccess(response.data.item));
};

export const getFriendsBySearch = (data) => async (dispatch) => {
    try {
        const response = await requestService.get('/friend/search/' + data, null, true);
        dispatch(searchFriendSuccess(response.data.item));
    } catch (error) {
        dispatch(searchFriendFailure(error.response));
    }
};

export const deleteFriend = (id) => async (dispatch) => {
    try {
        const response = await requestService.get('/friend/deleteFriend/' + id, null, true);
        dispatch(deleteFriendSuccess(response.data.item));
    } catch (error) {
        dispatch(deleteFriendFailure(error.response));
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};
