import { GET_ROOMS_FOR_CHAT_SUCCESS } from "../action-types/room-action-noti-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const getRoomsForChatSuccess = (rooms) => ({
    type: GET_ROOMS_FOR_CHAT_SUCCESS,
    payload: rooms
});
export const resetFormData = () => ({
    type: FORM_RESET,
});
