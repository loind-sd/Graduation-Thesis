import { SEND_INVITE_SUCCESS } from "../action-types/mail-action-types";
import {
    DELETE_NOTIFICATION_FAILURE,
    DELETE_NOTIFICATION_SUCCESS,
    GET_LIST_NOTIFICATION_FAILURE,
    GET_LIST_NOTIFICATION_SUCCESS,
    SEND_INVITE_FAILURE,
} from "../action-types/notification-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    notifications: [],
    notification: {},
    isLoaded: false,
    success: {},
    error: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return {
                ...state,
                isLoaded: false,
                success: "",
                error: {},
            };

        case GET_LIST_NOTIFICATION_SUCCESS:
            return { ...state, notifications: action.payload, isLoaded: false };

        case GET_LIST_NOTIFICATION_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case DELETE_NOTIFICATION_SUCCESS:
            return { ...state, success: action.payload };

        case DELETE_NOTIFICATION_FAILURE:
            return { ...state, error: action.payload };

        case SEND_INVITE_SUCCESS:
            return { ...state, success: action.payload };

        case SEND_INVITE_FAILURE:
            return { ...state, error: action.payload };

        default:
            return state;
    }
};

export default reducer;
