import requestService from "../../utils/request-service";
import { getListTaskDoingGroupFailure, getListTaskDoingGroupSuccess, getListTaskDoingPersonalFailure, getListTaskDoingPersonalSuccess, getListTaskDoneGroupFailure, getListTaskDoneGroupSuccess, getListTaskDonePersonalFailure, getListTaskDonePersonalSuccess, getListTaskFavoriteGroupFailure, getListTaskFavoriteGroupSuccess, getListTaskNotStartGroupFailure, getListTaskNotStartGroupSuccess, getTaskByIdFailure, getTaskByIdSuccess, updateFavoriteForTaskFailure, updateFavoriteForTaskSuccess, updateTaskPersonalFailure, updateTaskPersonalSuccess } from "../actions/task-action";
import { reset } from "../actions/_common-action";

export const getTaskById = (id) => async (dispatch) => {
    try {
        const response = await requestService.get("/tasks/detail/" + id, null, true);
        dispatch(getTaskByIdSuccess(response.data));
    } catch (error) {
        dispatch(getTaskByIdFailure(error.response));
    }
};

export const getListTaskNotStartGroup = () => async (dispatch) => {
    try {
        const response = await requestService.get("/roomTasks/notStart", null, true);
        dispatch(getListTaskNotStartGroupSuccess(response.data));
    } catch (error) {
        dispatch(getListTaskNotStartGroupFailure(error.response));
    }
};

export const getListTaskDoingGroup = (id) => async (dispatch) => {
    try {
        const response = await requestService.get("/roomTasks/allWithCondition/" + id, null, true);
        dispatch(getListTaskDoingGroupSuccess(response.data));

    } catch (error) {
        dispatch(getListTaskDoingGroupFailure(error.response));
    }
};

export const getListTaskDoneGroup = (id) => async (dispatch) => {
    try {
        const response = await requestService.get("/roomTasks/allWithCondition/" + id, null, true);
        dispatch(getListTaskDoneGroupSuccess(response.data));
    } catch (error) {
        dispatch(getListTaskDoneGroupFailure(error.response));
    }
};

export const getListTaskDoingPersonal = (statusId) => async (dispatch) => {
    try {
        const response = await requestService.get("/userTasks/" + statusId, null, true);
        dispatch(getListTaskDoingPersonalSuccess(response.data));
    } catch (error) {
        dispatch(getListTaskDoingPersonalFailure(error.response));
    }
};

export const getListTaskDonePersonal = (statusId) => async (dispatch) => {
    try {
        const response = await requestService.get("/userTasks/" + statusId, null, true);
        dispatch(getListTaskDonePersonalSuccess(response.data));
    } catch (error) {
        dispatch(getListTaskDonePersonalFailure(error.response));
    }
};

export const updateTaskPersonal = (taskId) => async (dispatch) => {
    try {
        const response = await requestService.put("/userTasks/updateStatus/" + taskId, null, true);
        dispatch(updateTaskPersonalSuccess(response.data));
    } catch (error) {
        dispatch(updateTaskPersonalFailure(error.response));
    }
};

export const getListTaskFavoriteGroup = () => async (dispatch) => {
    try {
        const response = await requestService.get("/roomTasks/favorite", null, true);
        dispatch(getListTaskFavoriteGroupSuccess(response.data));
    } catch (error) {
        dispatch(getListTaskFavoriteGroupFailure(error.response));
    }
};

export const updateFavoriteForTask = (data) => async (dispatch) => {
    try {
        const response = await requestService.post("/tasks/update-favorite", data, true);
        dispatch(updateFavoriteForTaskSuccess(response.data));
    } catch (error) {
        dispatch(updateFavoriteForTaskFailure(error.response));
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};
