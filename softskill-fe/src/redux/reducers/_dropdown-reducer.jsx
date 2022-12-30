import { SHOW_LOADER } from "../action-types/_common-action-types";
import {
    DROPDOWN_RECOMMEND_TASK_TYPE_FAILURE,
    DROPDOWN_RECOMMEND_TASK_TYPE_SUCCESS,
    DROPDOWN_TASKS_BY_SOFT_SKILL_ID_FAILURE,
    DROPDOWN_TASKS_BY_SOFT_SKILL_ID_SUCCESS,
} from "../action-types/_dropdown-action-types";

const initialState = {
    tasksBySoftSkillId: [],
    recommendTaskType: [],
    isLoaded: false,
    error: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case DROPDOWN_TASKS_BY_SOFT_SKILL_ID_SUCCESS:
            return { ...state, tasksBySoftSkillId: action.payload, isLoaded: false };

        case DROPDOWN_TASKS_BY_SOFT_SKILL_ID_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case DROPDOWN_RECOMMEND_TASK_TYPE_SUCCESS:
            return { ...state, recommendTaskType: action.payload, isLoaded: false };

        case DROPDOWN_RECOMMEND_TASK_TYPE_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};

export default reducer;
