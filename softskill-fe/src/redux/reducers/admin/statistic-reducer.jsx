import {
    FORM_RESET,
    GET_NUMBER_JOIN_SUCCESS,
    GET_NUMBER_ROOM_SUCCESS,
    GET_NUMBER_USER_SUCCESS,
    GET_RANK_INFO_SUCCESS,
    GET_SOFTSKILL_SUCCESS,
    GET_TOTAL_ROOMS_SUCCESS,
} from './../../action-types/admin/statistic-action-types';

const initialState = {
    numberRoom: {},
    numberJoin: {},
    rankInfo: {},
    totalRoom: {},
    softSkill: {},
    numberUser: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case FORM_RESET:
            return { ...state };
        case GET_NUMBER_USER_SUCCESS:
            return { ...state, numberUser: action.payload };
        case GET_NUMBER_JOIN_SUCCESS:
            return { ...state, numberJoin: action.payload };
        case GET_NUMBER_ROOM_SUCCESS:
            return { ...state, numberRoom: action.payload };
        case GET_RANK_INFO_SUCCESS:
            return { ...state, rankInfo: action.payload };
        case GET_TOTAL_ROOMS_SUCCESS:
            return { ...state, totalRoom: action.payload };
        case GET_SOFTSKILL_SUCCESS:
            return { ...state, softSkill: action.payload };
        default: {
            return state;
        }
    }
};

export default reducer;
