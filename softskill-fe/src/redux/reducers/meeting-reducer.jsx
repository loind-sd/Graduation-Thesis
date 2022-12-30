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
import { SHOW_LOADER } from "./../action-types/_common-action-types";

const initialState = {
    isLoaded: false,
    information: {},
    room: {},
    friends: [],
    task: {},
    tasks: [],
    success: {},
    error: {},
    currentTask: {},
    outRoomStatus: {},
    members: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return {
                ...state,
                loading: false,
                success: "",
                error: {},
            };

        case GET_INFORMATION_JOIN_SUCCESS:
            return { ...state, information: action.payload, loading: false };

        case GET_INFORMATION_JOIN_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case GET_ROOM_INFORMATION_SUCCESS:
            return { ...state, room: action.payload, loading: false };

        case GET_ROOM_INFORMATION_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case UPDATE_LOCK_ROOM_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case UPDATE_LOCK_ROOM_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case GET_LIST_FRIEND_SUCCESS:
            return { ...state, friends: action.payload, loading: false };

        case GET_LIST_FRIEND_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case SEND_INVITE_TO_ALL_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case SEND_INVITE_TO_ALL_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case GET_LIST_TASK_TO_CHOOSE_SUCCESS:
            return { ...state, tasks: action.payload, loading: false };

        case GET_LIST_TASK_TO_CHOOSE_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case GET_TASK_CHOOSE_BY_ID_SUCCESS:
            return { ...state, currentTask: action.payload, loading: false };

        case GET_TASK_CHOOSE_BY_ID_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case ADD_TASK_CHOOSE_FOR_USER_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case ADD_TASK_CHOOSE_FOR_USER_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case COMPLETE_TASK_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case COMPLETE_TASK_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case CANCEL_TASK_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case CANCEL_TASK_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case ACCEPT_ROOM_TASK_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case ACCEPT_ROOM_TASK_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case OUT_MEET_SUCCESS:
            return { ...state, outRoomStatus: action.payload, loading: false };

        case OUT_MEET_FAILURE:
            return { ...state, outRoomStatus: action.payload, loading: false };

        case GET_MEMBERS_SUCCESS:
            return { ...state, members: action.payload, loading: false };

        case GET_MEMBERS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case RESET_USER_ROOM_TASK_SUCCESS:
            return { ...state, success: action.payload, loading: false };

        case RESET_USER_ROOM_TASK_FAILURE:
            return { ...state, error: action.payload, loading: false };
        default:
            return state;
    }
};

export default reducer;
