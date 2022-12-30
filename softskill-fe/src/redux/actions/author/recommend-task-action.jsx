import {
    DELETE_RECOMMEND_TASKS_FAILURE,
    DELETE_RECOMMEND_TASKS_SUCCESS,
    GET_RECOMMEND_TASKS_FAILURE,
    GET_RECOMMEND_TASKS_SUCCESS,
    UPDATE_RECOMMEND_TASKS_FAILURE,
    UPDATE_RECOMMEND_TASKS_SUCCESS,
} from '../../action-types/author/recommend-task-action-type';

export const getRecommendTasksSuccess = (recommendTasks) => ({
    type: GET_RECOMMEND_TASKS_SUCCESS,
    payload: recommendTasks,
});

export const getRecommendTasksFailure = (error) => ({
    type: GET_RECOMMEND_TASKS_FAILURE,
    payload: error,
});

export const updateRecommendTasksSuccess = (recommendTasks) => ({
    type: UPDATE_RECOMMEND_TASKS_SUCCESS,
    payload: recommendTasks,
});

export const updateRecommendTasksFailure = (error) => ({
    type: UPDATE_RECOMMEND_TASKS_FAILURE,
    payload: error,
});

export const deleteRecommendTasksSuccess = (recommendTasks) => ({
    type: DELETE_RECOMMEND_TASKS_SUCCESS,
    payload: recommendTasks,
});

export const deleteRecommendTasksFailure = (error) => ({
    type: DELETE_RECOMMEND_TASKS_FAILURE,
    payload: error,
});
