import {
    GET_TASK_BY_ID_SUCCESS,
    GET_TASK_BY_ID_FAILURE,
    GET_LIST_TASK_NOT_START_GROUP_SUCCESS,
    GET_LIST_TASK_NOT_START_GROUP_FAILURE,
    GET_LIST_TASK_DOING_GROUP_SUCCESS,
    GET_LIST_TASK_DOING_GROUP_FAILURE,
    GET_LIST_TASK_FAVORITE_GROUP_SUCCESS,
    GET_LIST_TASK_FAVORITE_GROUP_FAILURE,
    GET_LIST_TASK_DONE_GROUP_SUCCESS,
    GET_LIST_TASK_DONE_GROUP_FAILURE,
    GET_LIST_TASK_DOING_PERSONAL_SUCCESS,
    GET_LIST_TASK_DOING_PERSONAL_FAILURE,
    GET_LIST_TASK_DONE_PERSONAL_SUCCESS,
    GET_LIST_TASK_DONE_PERSONAL_FAILURE,
    UPDATE_FAVORITE_FOR_TASK_SUCCESS,
    UPDATE_FAVORITE_FOR_TASK_FAILURE,
    UPDATE_TASK_PERSONAL_SUCCESS,
    UPDATE_TASK_PERSONAL_FAILURE,
} from "../action-types/task-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    tasks: [],
    task: {},
    tasksGroupNotStart: [],
    tasksGroupDoing: [],
    tasksGroupDone: [],
    tasksPersonalDoing: [],
    tasksPersonalDone: [],
    tasksGroupFavorite: [],
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

        case GET_TASK_BY_ID_SUCCESS:
            return { ...state, task: action.payload, isLoaded: false };

        case GET_TASK_BY_ID_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case GET_LIST_TASK_NOT_START_GROUP_SUCCESS:
            return { ...state, tasksGroupNotStart: action.payload, isLoaded: false };

        case GET_LIST_TASK_NOT_START_GROUP_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case GET_LIST_TASK_DOING_GROUP_SUCCESS:
            return { ...state, tasksGroupDoing: action.payload, isLoaded: false };

        case GET_LIST_TASK_DOING_GROUP_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case GET_LIST_TASK_DONE_GROUP_SUCCESS:
            return { ...state, tasksGroupDone: action.payload, isLoaded: false };

        case GET_LIST_TASK_DONE_GROUP_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        // tasksPersonal
        case GET_LIST_TASK_DOING_PERSONAL_SUCCESS:
            return { ...state, tasksPersonalDoing: action.payload, isLoaded: false };

        case GET_LIST_TASK_DOING_PERSONAL_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case GET_LIST_TASK_DONE_PERSONAL_SUCCESS:
            return { ...state, tasksPersonalDone: action.payload, isLoaded: false };

        case GET_LIST_TASK_DONE_PERSONAL_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case UPDATE_TASK_PERSONAL_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case UPDATE_TASK_PERSONAL_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        //
        case GET_LIST_TASK_FAVORITE_GROUP_SUCCESS:
            return { ...state, tasksGroupFavorite: action.payload, isLoaded: false };

        case GET_LIST_TASK_FAVORITE_GROUP_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case UPDATE_FAVORITE_FOR_TASK_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case UPDATE_FAVORITE_FOR_TASK_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};

export default reducer;
