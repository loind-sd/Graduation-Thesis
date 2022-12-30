import requestService from "../../utils/request-service";
import {
    addTaskFailure,
    addTaskSuccess,
    deleteTaskFailure,
    deleteTaskSuccess,
    editTaskFailure,
    editTaskSuccess,
    getTasksFailure,
    getTasksSuccess,
} from "../actions/task-for-you-action";
import { reset } from "../actions/_common-action";
import { showLoader } from './../actions/_common-action';

export const getTasks = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get(
            "/task-for-you",
            null,
            true
        );
        dispatch(getTasksSuccess(response.data));
    } catch (error) {
        dispatch(getTasksFailure(error.response));
    }
};

export const addTask = (data) => async (dispatch) => {
    try {
        const response = await requestService.post(
            "/task-for-you",
            data,
            true
        );
        dispatch(addTaskSuccess(response.data));
    } catch (error) {
        dispatch(addTaskFailure(error.response));
    }
};

export const editTask = (data) => async (dispatch) => {
    try {
        const response = await requestService.put(
            "/task-for-you",
            data,
            true
        );
        dispatch(editTaskSuccess(response.data));
    } catch (error) {
        dispatch(editTaskFailure(error.response));
    }
};

export const deleteTask = (id) => async (dispatch) => {
    try {
        const response = await requestService.delete(
            "/task-for-you",
            { id: id },
            true
        );
        dispatch(deleteTaskSuccess(response.data));
    } catch (error) {
        dispatch(deleteTaskFailure(error.response));
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};
