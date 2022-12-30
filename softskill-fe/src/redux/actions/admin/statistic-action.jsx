import {
    FORM_RESET,
    GET_NUMBER_JOIN_SUCCESS,
    GET_NUMBER_ROOM_SUCCESS,
    GET_NUMBER_USER_SUCCESS,
    GET_RANK_INFO_SUCCESS,
    GET_SOFTSKILL_SUCCESS,
    GET_TOTAL_ROOMS_SUCCESS,
    SHOW_LOADER,
} from './../../action-types/admin/statistic-action-types';

export const showLoader = () => ({
    type: SHOW_LOADER,
});

export const reset = () => ({
    type: FORM_RESET,
});

export const getNumberUserSuccess = (user) => ({
    type: GET_NUMBER_USER_SUCCESS,
    payload: user,
});

export const getNumberRoomSuccess = (room) => ({
    type: GET_NUMBER_ROOM_SUCCESS,
    payload: room,
});

export const getRankInfoSuccess = (rankInfo) => ({
    type: GET_RANK_INFO_SUCCESS,
    payload: rankInfo,
});

export const getNumberJoinSuccess = (join) => ({
    type: GET_NUMBER_JOIN_SUCCESS,
    payload: join,
});

export const getTotalRoomSuccess = (room) => ({
    type: GET_TOTAL_ROOMS_SUCCESS,
    payload: room,
});

export const getSoftSkillSuccess = (ss) => ({
    type: GET_SOFTSKILL_SUCCESS,
    payload: ss,
});
