import {
    ADD_GROUP_TASKS_FAILURE,
    ADD_GROUP_TASKS_SUCCESS,
    DELETE_GROUP_TASKS_FAILURE,
    DELETE_GROUP_TASKS_SUCCESS,
    GET_GROUP_TASKS_FAILURE,
    GET_GROUP_TASKS_SUCCESS,
    UPDATE_GROUP_TASKS_FAILURE,
    UPDATE_GROUP_TASKS_SUCCESS,
} from '../../action-types/author/group-task-action-type';

export const getGroupTasksSuccess = (groupTasks) => ({
    type: GET_GROUP_TASKS_SUCCESS,
    payload: groupTasks,
});

export const getGroupTasksFailure = (error) => ({
    type: GET_GROUP_TASKS_FAILURE,
    payload: error,
});

export const addGroupTasksSuccess = (groupTasks) => ({
    type: ADD_GROUP_TASKS_SUCCESS,
    payload: groupTasks,
});

export const addGroupTasksFailure = (error) => ({
    type: ADD_GROUP_TASKS_FAILURE,
    payload: error,
});

export const updateGroupTasksSuccess = (groupTasks) => ({
    type: UPDATE_GROUP_TASKS_SUCCESS,
    payload: groupTasks,
});

export const updateGroupTasksFailure = (error) => ({
    type: UPDATE_GROUP_TASKS_FAILURE,
    payload: error,
});

export const deleteGroupTasksSuccess = (groupTasks) => ({
    type: DELETE_GROUP_TASKS_SUCCESS,
    payload: groupTasks,
});

export const deleteGroupTasksFailure = (error) => ({
    type: DELETE_GROUP_TASKS_FAILURE,
    payload: error,
});
