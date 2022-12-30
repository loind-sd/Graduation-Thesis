import {
    GET_LIST_TASK_DOING_GROUP_FAILURE,
    GET_LIST_TASK_DOING_GROUP_SUCCESS,
    GET_LIST_TASK_DONE_GROUP_FAILURE,
    GET_LIST_TASK_DONE_GROUP_SUCCESS,
    GET_LIST_TASK_FAVORITE_GROUP_FAILURE,
    GET_LIST_TASK_FAVORITE_GROUP_SUCCESS,
    GET_LIST_TASK_NOT_START_GROUP_FAILURE,
    GET_LIST_TASK_NOT_START_GROUP_SUCCESS,
    GET_LIST_TASK_DOING_PERSONAL_SUCCESS,
    GET_LIST_TASK_DOING_PERSONAL_FAILURE,
    GET_LIST_TASK_DONE_PERSONAL_SUCCESS,
    GET_LIST_TASK_DONE_PERSONAL_FAILURE,
    UPDATE_TASK_PERSONAL_SUCCESS,
    UPDATE_TASK_PERSONAL_FAILURE,
    GET_TASK_BY_ID_FAILURE,
    GET_TASK_BY_ID_SUCCESS,
    UPDATE_FAVORITE_FOR_TASK_FAILURE,
    UPDATE_FAVORITE_FOR_TASK_SUCCESS,
} from "../action-types/task-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const getTaskByIdSuccess = (task) => ({
    type: GET_TASK_BY_ID_SUCCESS,
    payload: task,
});

export const getTaskByIdFailure = (error) => ({
    type: GET_TASK_BY_ID_FAILURE,
    payload: error,
});

export const getListTaskNotStartGroupSuccess = (tasks) => ({
    type: GET_LIST_TASK_NOT_START_GROUP_SUCCESS,
    payload: tasks,
});

export const getListTaskNotStartGroupFailure = (error) => ({
    type: GET_LIST_TASK_NOT_START_GROUP_FAILURE,
    payload: error,
});

export const getListTaskDoingGroupSuccess = (tasks) => ({
    type: GET_LIST_TASK_DOING_GROUP_SUCCESS,
    payload: tasks,
});

export const getListTaskDoingGroupFailure = (error) => ({
    type: GET_LIST_TASK_DOING_GROUP_FAILURE,
    payload: error,
});

export const getListTaskDoneGroupSuccess = (tasks) => ({
    type: GET_LIST_TASK_DONE_GROUP_SUCCESS,
    payload: tasks,
});

export const getListTaskDoneGroupFailure = (error) => ({
    type: GET_LIST_TASK_DONE_GROUP_FAILURE,
    payload: error,
});

export const getListTaskDoingPersonalSuccess = (tasks) => ({
    type: GET_LIST_TASK_DOING_PERSONAL_SUCCESS,
    payload: tasks,
});

export const getListTaskDoingPersonalFailure = (error) => ({
    type: GET_LIST_TASK_DOING_PERSONAL_FAILURE,
    payload: error,
});

export const getListTaskDonePersonalSuccess = (tasks) => ({
    type: GET_LIST_TASK_DONE_PERSONAL_SUCCESS,
    payload: tasks,
});

export const getListTaskDonePersonalFailure = (error) => ({
    type: GET_LIST_TASK_DONE_PERSONAL_FAILURE,
    payload: error,
});

export const updateTaskPersonalSuccess = (success) => ({
    type: UPDATE_TASK_PERSONAL_SUCCESS,
    payload: success,
});

export const updateTaskPersonalFailure = (error) => ({
    type: UPDATE_TASK_PERSONAL_FAILURE,
    payload: error,
});

export const getListTaskFavoriteGroupSuccess = (tasks) => ({
    type: GET_LIST_TASK_FAVORITE_GROUP_SUCCESS,
    payload: tasks,
});

export const getListTaskFavoriteGroupFailure = (error) => ({
    type: GET_LIST_TASK_FAVORITE_GROUP_FAILURE,
    payload: error,
});

export const updateFavoriteForTaskSuccess = (success) => ({
    type: UPDATE_FAVORITE_FOR_TASK_SUCCESS,
    payload: success,
});

export const updateFavoriteForTaskFailure = (error) => ({
    type: UPDATE_FAVORITE_FOR_TASK_FAILURE,
    payload: error,
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
