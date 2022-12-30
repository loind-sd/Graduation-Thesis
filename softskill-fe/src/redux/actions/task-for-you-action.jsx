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

export const getTasksSuccess = (tasks) => ({
    type: GET_TASKS_SUCCESS,
    payload: tasks,
});

export const getTasksFailure = (error) => ({
    type: GET_TASKS_FAILURE,
    payload: error,
});

export const addTaskSuccess = (success) => ({
    type: ADD_TASK_SUCCESS,
    payload: success,
});

export const addTaskFailure = (error) => ({
    type: ADD_TASK_FAILURE,
    payload: error,
});

export const editTaskSuccess = (success) => ({
    type: EDIT_TASK_SUCCESS,
    payload: success,
});

export const editTaskFailure = (error) => ({
    type: EDIT_TASK_FAILURE,
    payload: error,
});

export const deleteTaskSuccess = (success) => ({
    type: DELETE_TASK_SUCCESS,
    payload: success,
});

export const deleteTaskFailure = (error) => ({
    type: DELETE_TASK_FAILURE,
    payload: error,
});
