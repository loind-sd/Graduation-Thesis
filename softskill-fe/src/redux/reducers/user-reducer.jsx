import {
    CHANGE_NAME_FAILURE,
    CHANGE_NAME_SUCCESS,
    CHANGE_NICKNAME_FAILURE,
    CHANGE_NICKNAME_SUCCESS,
    CHANGE_PASSWORD_FAILURE,
    CHANGE_PASSWORD_SUCCESS,
    FORGOT_PASSWORD_FAILURE,
    FORGOT_PASSWORD_SUCCESS,
} from "../action-types/user-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    user: {},
    isLoaded: false,
    success: "",
    error: "",
};

const reducer = (state = initialState, action) => {

    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return { ...state, isLoaded: false, success: "", error: "" };

        case FORGOT_PASSWORD_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case FORGOT_PASSWORD_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case CHANGE_PASSWORD_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case CHANGE_PASSWORD_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case CHANGE_NICKNAME_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case CHANGE_NICKNAME_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case CHANGE_NAME_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case CHANGE_NAME_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};

export default reducer;

