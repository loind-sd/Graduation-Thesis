import {
    ACCEPT_FRIEND_REQUEST_FAILURE,
    ACCEPT_FRIEND_REQUEST_SUCCESS,
    ADD_FRIENDS_FAILURE,
    ADD_FRIENDS_SUCCESS,
    DELETE_FRIEND_FAILURE,
    DELETE_FRIEND_SUCCESS,
    DENY_FRIEND_REQUEST_FAILURE,
    DENY_FRIEND_REQUEST_SUCCESS,
    FORM_RESET,
    GET_FRIENDS_BY_TASK_FAILURE,
    GET_FRIENDS_BY_TASK_SUCCESS,
    GET_FRIENDS_EVEN_REQUEST_SUCCESS,
    GET_FRIENDS_RECENT,
    GET_FRIENDS_SUCCESS,
    SEARCH_FRIENDS_FAILURE,
    SEARCH_FRIENDS_SUCCESS,
    SHOW_LOADER,
} from "../action-types/friend-action-types";

export const showLoader = () => ({
    type: SHOW_LOADER,
});

export const reset = () => ({
    type: FORM_RESET,
});

export const getFriendSuccess = (friends) => ({
    type: GET_FRIENDS_SUCCESS,
    payload: friends,
});

export const getFriendEvenSuccess = (friends) => ({
    type: GET_FRIENDS_EVEN_REQUEST_SUCCESS,
    payload: friends,
});

export const addFriendSuccess = (success) => ({
    type: ADD_FRIENDS_SUCCESS,
    payload: success,
});

export const addFriendFailure = (error) => ({
    type: ADD_FRIENDS_FAILURE,
    payload: error,
});

export const getFriendsByTaskSuccess = (users) => ({
    type: GET_FRIENDS_BY_TASK_SUCCESS,
    payload: users,
});

export const getFriendsByTaskFailure = (error) => ({
    type: GET_FRIENDS_BY_TASK_FAILURE,
    payload: error,
});

export const getFriendsRecentSuccess = (friends) => ({
    type: GET_FRIENDS_RECENT,
    payload: friends,
});

export const acceptFriendSuccess = (friends) => ({
    type: ACCEPT_FRIEND_REQUEST_SUCCESS,
    payload: friends,
});

export const acceptFriendFailure = (error) => ({
    type: ACCEPT_FRIEND_REQUEST_FAILURE,
    payload: error,
});

export const denyFriendSuccess = (friends) => ({
    type: DENY_FRIEND_REQUEST_SUCCESS,
    payload: friends,
});

export const denyFriendFailure = (error) => ({
    type: DENY_FRIEND_REQUEST_FAILURE,
    payload: error,
});

export const searchFriendSuccess = (friends) => ({
    type: SEARCH_FRIENDS_SUCCESS,
    payload: friends,
});

export const searchFriendFailure = (error) => ({
    type: SEARCH_FRIENDS_FAILURE,
    payload: error,
});

export const deleteFriendSuccess = (success) => ({
    type: DELETE_FRIEND_SUCCESS,
    payload: success,
});

export const deleteFriendFailure = (error) => ({
    type: DELETE_FRIEND_FAILURE,
    payload: error,
});
