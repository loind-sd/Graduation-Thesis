import {
    ADD_TASK_FAILURE,
    ADD_TASK_SUCCESS,
    DELETE_TASK_FAILURE,
    DELETE_TASK_SUCCESS,
    EDIT_TASK_FAILURE,
    EDIT_TASK_SUCCESS,
    GET_TASKS_FAILURE,
    GET_TASKS_SUCCESS,
} from "../action-types/task-for-you-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    tasks: [],
    isLoaded: false,
    success: {},
    error: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return { ...state, isLoaded: false, success: {}, error: {} };

        case GET_TASKS_SUCCESS:
            return { ...state, tasks: action.payload, isLoaded: false };

        case GET_TASKS_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case ADD_TASK_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case ADD_TASK_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case EDIT_TASK_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case EDIT_TASK_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case DELETE_TASK_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case DELETE_TASK_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};

export default reducer;
