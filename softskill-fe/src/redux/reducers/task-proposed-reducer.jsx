import {
    ADD_TASK_PROPOSED_FAILURE,
    ADD_TASK_PROPOSED_SUCCESS,
    GET_TASKS_PROPOSED_FAILURE,
    GET_TASKS_PROPOSED_SUCCESS,
    GET_TIME_CHECK_ADD_RECOMMEND_TASK_FAILURE,
    GET_TIME_CHECK_ADD_RECOMMEND_TASK_SUCCESS,
} from "../action-types/task-proposed-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    tasks: [],
    time: {},
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

        case GET_TIME_CHECK_ADD_RECOMMEND_TASK_SUCCESS:
            return { ...state, time: action.payload, isLoaded: false };

        case GET_TIME_CHECK_ADD_RECOMMEND_TASK_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case GET_TASKS_PROPOSED_SUCCESS:
            return { ...state, tasks: action.payload, isLoaded: false, type: 'load' };

        case GET_TASKS_PROPOSED_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case ADD_TASK_PROPOSED_SUCCESS:
            return { ...state, tasks: action.payload, isLoaded: false, type: 'add' };

        case ADD_TASK_PROPOSED_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};

export default reducer;
