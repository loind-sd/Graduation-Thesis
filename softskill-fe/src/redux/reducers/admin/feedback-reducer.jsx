import {
    DELETE_FEEDBACK_FAILURE,
    DELETE_FEEDBACK_SUCCESS,
    GET_LIST_FEEDBACK_FAILURE,
    GET_LIST_FEEDBACK_SUCCESS,
    CONFIRM_FEEDBACK_SUCCESS,
    CONFIRM_FEEDBACK_FAILURE
} from "../../action-types/admin/feedback-action-type"


const initialState = {
    feedbacks: [],
    isLoaded: false,
    success: "",
    error: {},
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_LIST_FEEDBACK_SUCCESS:
            return { ...state, feedbacks: action.payload, loading: false };
        case GET_LIST_FEEDBACK_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case DELETE_FEEDBACK_SUCCESS:
            return { ...state, feedbacks: action.payload, loading: false };
        case DELETE_FEEDBACK_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case CONFIRM_FEEDBACK_SUCCESS:
            return { ...state, feedbacks: action.payload, loading: false };
        case CONFIRM_FEEDBACK_FAILURE:
            return { ...state, error: action.payload, loading: false };
        default: {
            return state;
        }
    }
};

export default reducer;