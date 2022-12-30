import {
    ACCEPT_ROOM_TASK_FAILURE,
    ACCEPT_ROOM_TASK_SUCCESS,
    ADD_TASK_CHOOSE_FOR_USER_FAILURE,
    ADD_TASK_CHOOSE_FOR_USER_SUCCESS,
    CANCEL_TASK_FAILURE,
    CANCEL_TASK_SUCCESS,
    COMPLETE_TASK_FAILURE,
    COMPLETE_TASK_SUCCESS,
    GET_INFORMATION_JOIN_FAILURE,
    GET_INFORMATION_JOIN_SUCCESS,
    GET_LIST_FRIEND_FAILURE,
    GET_LIST_FRIEND_SUCCESS,
    GET_LIST_TASK_TO_CHOOSE_FAILURE,
    GET_LIST_TASK_TO_CHOOSE_SUCCESS,
    GET_MEMBERS_FAILURE,
    GET_MEMBERS_SUCCESS,
    GET_ROOM_INFORMATION_FAILURE,
    GET_ROOM_INFORMATION_SUCCESS,
    GET_TASK_CHOOSE_BY_ID_FAILURE,
    GET_TASK_CHOOSE_BY_ID_SUCCESS,
    OUT_MEET_FAILURE,
    OUT_MEET_SUCCESS,
    RESET_USER_ROOM_TASK_FAILURE,
    RESET_USER_ROOM_TASK_SUCCESS,
    SEND_INVITE_TO_ALL_FAILURE,
    SEND_INVITE_TO_ALL_SUCCESS,
    UPDATE_LOCK_ROOM_FAILURE,
    UPDATE_LOCK_ROOM_SUCCESS,
} from "../action-types/meeting-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const getInformationJoinSuccess = (information) => ({
    type: GET_INFORMATION_JOIN_SUCCESS,
    payload: information,
});

export const getInformationJoinFailure = (error) => ({
    type: GET_INFORMATION_JOIN_FAILURE,
    payload: error,
});

export const getRoomInformationSuccess = (room) => ({
    type: GET_ROOM_INFORMATION_SUCCESS,
    payload: room,
});

export const getRoomInformationFailure = (error) => ({
    type: GET_ROOM_INFORMATION_FAILURE,
    payload: error,
});

export const updateLockRoomSuccess = (success) => ({
    type: UPDATE_LOCK_ROOM_SUCCESS,
    payload: success,
});

export const updateLockRoomFailure = (error) => ({
    type: UPDATE_LOCK_ROOM_FAILURE,
    payload: error,
});

export const getListFriendSuccess = (friends) => ({
    type: GET_LIST_FRIEND_SUCCESS,
    payload: friends,
});

export const getListFriendFailure = (error) => ({
    type: GET_LIST_FRIEND_FAILURE,
    payload: error,
});

export const sendInviteToAllSuccess = (success) => ({
    type: SEND_INVITE_TO_ALL_SUCCESS,
    payload: success,
});

export const sendInviteToAllFailure = (error) => ({
    type: SEND_INVITE_TO_ALL_FAILURE,
    payload: error,
});

export const getListTaskToChooseSuccess = (tasks) => ({
    type: GET_LIST_TASK_TO_CHOOSE_SUCCESS,
    payload: tasks,
});

export const getListTaskToChooseFailure = (error) => ({
    type: GET_LIST_TASK_TO_CHOOSE_FAILURE,
    payload: error,
});

export const getTaskChooseByIdSuccess = (task) => ({
    type: GET_TASK_CHOOSE_BY_ID_SUCCESS,
    payload: task,
});

export const getTaskChooseByIdFailure = (error) => ({
    type: GET_TASK_CHOOSE_BY_ID_FAILURE,
    payload: error,
});

export const addTaskChooseForUserSuccess = (success) => ({
    type: ADD_TASK_CHOOSE_FOR_USER_SUCCESS,
    payload: success,
});

export const addTaskChooseForUserFailure = (error) => ({
    type: ADD_TASK_CHOOSE_FOR_USER_FAILURE,
    payload: error,
});

export const completeTaskSuccess = (success) => ({
    type: COMPLETE_TASK_SUCCESS,
    payload: success,
});

export const completeTaskFailure = (error) => ({
    type: COMPLETE_TASK_FAILURE,
    payload: error,
});

export const cancelTaskSuccess = (success) => ({
    type: CANCEL_TASK_SUCCESS,
    payload: success,
});

export const cancelTaskFailure = (error) => ({
    type: CANCEL_TASK_FAILURE,
    payload: error,
});

export const acceptRoomTaskSuccess = (success) => ({
    type: ACCEPT_ROOM_TASK_SUCCESS,
    payload: success,
});

export const acceptRoomTaskFailure = (error) => ({
    type: ACCEPT_ROOM_TASK_FAILURE,
    payload: error,
});
export const outMeetSuccess = (success) => ({
    type: OUT_MEET_SUCCESS,
    payload: success,
});

export const outMeetFailure = (error) => ({
    type: OUT_MEET_FAILURE,
    payload: error,
});

export const getMembersSuccess = (members) => ({
    type: GET_MEMBERS_SUCCESS,
    payload: members,
});

export const getMembersFailure = (error) => ({
    type: GET_MEMBERS_FAILURE,
    payload: error
});

export const resetUserRoomTaskInfoSuccess = (success) => ({
    type: RESET_USER_ROOM_TASK_SUCCESS,
    payload: success,
});

export const resetUserRoomTaskInfoFailure = (error) => ({
    type: RESET_USER_ROOM_TASK_FAILURE,
    payload: error
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
