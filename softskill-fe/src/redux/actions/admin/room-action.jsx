import {
    DELETE_ROOM_FAILURE,
    DELETE_ROOM_SUCCESS,
    GET_LIST_ROOM_FAILURE,
    GET_LIST_ROOM_SUCCESS
} from "../../action-types/admin/room-action-type";

export const getRoomsSuccess = (rooms) => ({
    type: GET_LIST_ROOM_SUCCESS,
    payload: rooms
});

export const getRoomsFailure = (error) => ({
    type: GET_LIST_ROOM_FAILURE,
    payload: error
});

export const deleteRoomSuccess = (rooms) => ({
    type: DELETE_ROOM_SUCCESS,
    payload: rooms
});

export const deleteRoomFailure = (error) => ({
    type: DELETE_ROOM_FAILURE,
    payload: error
});