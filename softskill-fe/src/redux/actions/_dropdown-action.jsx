import { FORM_RESET } from "../action-types/_common-action-types";
import {
    DROPDOWN_RECOMMEND_TASK_TYPE_FAILURE,
    DROPDOWN_RECOMMEND_TASK_TYPE_SUCCESS,
    DROPDOWN_TASKS_BY_SOFT_SKILL_ID_FAILURE,
    DROPDOWN_TASKS_BY_SOFT_SKILL_ID_SUCCESS,
} from "../action-types/_dropdown-action-types";

export const getTasksBySoftSkillIdSuccess = (task) => ({
    type: DROPDOWN_TASKS_BY_SOFT_SKILL_ID_SUCCESS,
    payload: task,
});

export const getTasksBySoftSkillIdFailure = (error) => ({
    type: DROPDOWN_TASKS_BY_SOFT_SKILL_ID_FAILURE,
    payload: error,
});

export const getRecommendTaskTypeSuccess = (taskType) => ({
    type: DROPDOWN_RECOMMEND_TASK_TYPE_SUCCESS,
    payload: taskType,
});

export const getRecommendTaskTypeFailure = (error) => ({
    type: DROPDOWN_RECOMMEND_TASK_TYPE_FAILURE,
    payload: error,
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
