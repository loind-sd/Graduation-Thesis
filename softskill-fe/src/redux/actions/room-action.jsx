import { DELETE_ACCOUNTS_SUCCESS } from "../action-types/admin/acc-action-type";
import {
    CREATE_NEW_ROOM_SUCCESS,
    CREATE_NEW_ROOM_FAILURE,
    GET_ROOMS_SUCCESS,
    GET_JOINED_ROOM_SUCCESS,
    GET_CREATED_ROOM_SUCCESS,
    DELETE_MY_ROOM_SUCCESS,
    DELETE_MY_ROOM_FAILURE,
    SHOW_LOADER,
} from "../action-types/room-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const createNewRoomSuccess = (user) => ({
    type: CREATE_NEW_ROOM_SUCCESS,
    payload: user,
});

export const createNewRoomFailure = (error) => ({
    type: CREATE_NEW_ROOM_FAILURE,
    payload: error,
});

export const getRoomsSuccess = (rooms) => ({
    type: GET_ROOMS_SUCCESS,
    payload: rooms
});

export const getRoomCreatedSuccess = (rooms) => ({
    type: GET_CREATED_ROOM_SUCCESS,
    payload: rooms
});
export const getRoomJoinedSuccess = (rooms) => ({
    type: GET_JOINED_ROOM_SUCCESS,
    payload: rooms
});

export const deleteRoomSuccess = (success) => ({
    type: DELETE_MY_ROOM_SUCCESS,
    payload: success
});

export const deleteRoomFailure = (error) => ({
    type: DELETE_MY_ROOM_FAILURE,
    payload: error
});

export const showLoader = (status) => ({
    type: SHOW_LOADER,
    payload:status
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
