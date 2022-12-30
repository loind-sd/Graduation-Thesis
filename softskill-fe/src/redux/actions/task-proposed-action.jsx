import {
    ADD_TASK_PROPOSED_FAILURE,
    ADD_TASK_PROPOSED_SUCCESS,
    GET_TASKS_PROPOSED_FAILURE,
    GET_TASKS_PROPOSED_SUCCESS,
    GET_TIME_CHECK_ADD_RECOMMEND_TASK_SUCCESS,
    GET_TIME_CHECK_ADD_RECOMMEND_TASK_FAILURE,
} from "../action-types/task-proposed-action-types";

export const getTimeCheckAddRecommendTaskSuccess = (task) => ({
    type: GET_TIME_CHECK_ADD_RECOMMEND_TASK_SUCCESS,
    payload: task,
});

export const getTimeCheckAddRecommendTaskFailure = (error) => ({
    type: GET_TIME_CHECK_ADD_RECOMMEND_TASK_FAILURE,
    payload: error,
});

export const getTasksProposedSuccess = (tasks) => ({
    type: GET_TASKS_PROPOSED_SUCCESS,
    payload: tasks,
});

export const getTasksProposedFailure = (error) => ({
    type: GET_TASKS_PROPOSED_FAILURE,
    payload: error,
});

export const addTaskProposedSuccess = (success) => ({
    type: ADD_TASK_PROPOSED_SUCCESS,
    payload: success,
});

export const addTaskProposedFailure = (error) => ({
    type: ADD_TASK_PROPOSED_FAILURE,
    payload: error,
});
