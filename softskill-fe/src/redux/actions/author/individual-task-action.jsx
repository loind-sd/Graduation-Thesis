import {
    ADD_INDIVIDUAL_TASKS_FAILURE,
    ADD_INDIVIDUAL_TASKS_SUCCESS,
    DELETE_INDIVIDUAL_TASKS_FAILURE,
    DELETE_INDIVIDUAL_TASKS_SUCCESS,
    GET_INDIVIDUAL_TASKS_FAILURE,
    GET_INDIVIDUAL_TASKS_SUCCESS,
    UPDATE_INDIVIDUAL_TASKS_FAILURE,
    UPDATE_INDIVIDUAL_TASKS_SUCCESS,
} from '../../action-types/author/individual-task-action-type';

export const getIndividualTasksSuccess = (individualTasks) => ({
    type: GET_INDIVIDUAL_TASKS_SUCCESS,
    payload: individualTasks,
});

export const getIndividualTasksFailure = (error) => ({
    type: GET_INDIVIDUAL_TASKS_FAILURE,
    payload: error,
});

export const addIndividualTasksSuccess = (individualTasks) => ({
    type: ADD_INDIVIDUAL_TASKS_SUCCESS,
    payload: individualTasks,
});

export const addIndividualTasksFailure = (error) => ({
    type: ADD_INDIVIDUAL_TASKS_FAILURE,
    payload: error,
});

export const updateIndividualTasksSuccess = (individualTasks) => ({
    type: UPDATE_INDIVIDUAL_TASKS_SUCCESS,
    payload: individualTasks,
});

export const updateIndividualTasksFailure = (error) => ({
    type: UPDATE_INDIVIDUAL_TASKS_FAILURE,
    payload: error,
});

export const deleteIndividualTasksSuccess = (individualTasks) => ({
    type: DELETE_INDIVIDUAL_TASKS_SUCCESS,
    payload: individualTasks,
});

export const deleteIndividualTasksFailure = (error) => ({
    type: DELETE_INDIVIDUAL_TASKS_FAILURE,
    payload: error,
});
