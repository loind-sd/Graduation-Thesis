import {
    CREATE_NEW_FEEDBACK_SUCCESS,
    CREATE_NEW_FEEDBACK_FAILURE,
    GET_FEEDBACK_TYPE_SUCCESS
} from "../action-types/feedback-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    feedbackType: {},
    isLoaded: false,
    success: "",
    error: {},
    fType: '',
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

        case CREATE_NEW_FEEDBACK_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false,  fType: 'send'};

        case CREATE_NEW_FEEDBACK_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case GET_FEEDBACK_TYPE_SUCCESS:
            return { ...state, feedbackType: action.payload, isLoaded: false };

        default:
            return state;
    }
}

export default reducer;
