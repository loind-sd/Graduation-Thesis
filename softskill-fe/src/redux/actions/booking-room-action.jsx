import { FORM_RESET, GET_CREATED_ROOM_SUCCESS, GET_JOINED_ROOM_SUCCESS, GET_ROOMS_SUCCESS, REGISTER_BOOKING_ROOM_FAILURE, REGISTER_BOOKING_ROOM_SUCCESS, SHOW_LOADER } from "../action-types/booking-room-action-types";

export const getRoomCreatedSuccess = (rooms) => ({
    type: GET_CREATED_ROOM_SUCCESS,
    payload: rooms
});
export const getRoomJoinedSuccess = (rooms) => ({
    type: GET_JOINED_ROOM_SUCCESS,
    payload: rooms
});
export const getRoomsSuccess = (rooms) => ({
    type: GET_ROOMS_SUCCESS,
    payload: rooms
});

export const registerBookingRoomSuccess = (success) => ({
    type: REGISTER_BOOKING_ROOM_SUCCESS,
    payload: success
});

export const registerBookingRoomFailure = (error) => ({
    type: REGISTER_BOOKING_ROOM_FAILURE,
    payload: error
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
export const showLoader = () => ({
    type: SHOW_LOADER,
  });
