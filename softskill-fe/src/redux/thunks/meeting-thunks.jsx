import requestService from "../../utils/request-service";
import { getMemberFailure, getMemberSuccess } from "../actions/chat-action";
import {
    acceptRoomTaskFailure,
    acceptRoomTaskSuccess,
    addTaskChooseForUserFailure,
    addTaskChooseForUserSuccess,
    cancelTaskFailure,
    cancelTaskSuccess,
    completeTaskFailure,
    completeTaskSuccess,
    getInformationJoinFailure,
    getInformationJoinSuccess,
    getListFriendFailure,
    getListFriendSuccess,
    getListTaskToChooseFailure,
    getListTaskToChooseSuccess,
    getRoomInformationFailure,
    getRoomInformationSuccess,
    getTaskChooseByIdFailure,
    getTaskChooseByIdSuccess,
    outMeetFailure,
    outMeetSuccess,
    resetUserRoomTaskInfoFailure,
    resetUserRoomTaskInfoSuccess,
    sendInviteToAllFailure,
    sendInviteToAllSuccess,
    updateLockRoomFailure,
    updateLockRoomSuccess,
} from "../actions/meeting-action";
import { reset } from "../actions/_common-action";
import { showLoader } from "./../actions/_common-action";

export const getInformationJoinRoom = (id) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get(
            "/roomTasks/joinRoom/" + id,
            null,
            true
        );
        dispatch(getInformationJoinSuccess(response.data));
    } catch (error) {
        dispatch(getInformationJoinFailure(error.response));
    }
};

export const getRoomInformation = (id) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get(
            "/rooms/" + id,
            null,
            true
        );
        dispatch(getRoomInformationSuccess(response.data));
    } catch (error) {
        dispatch(getRoomInformationFailure(error.response));
    }
};

export const updateLockRoom = (id) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get(
            "/roomTasks/changeLockRoom/" + id,
            null,
            true
        );
        dispatch(updateLockRoomSuccess(response.data));
    } catch (error) {
        dispatch(updateLockRoomFailure(error.response));
    }
};

export const getListFriend = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get(
            "/friend/people-room",
            null,
            true
        );
        dispatch(getListFriendSuccess(response.data));
    } catch (error) {
        dispatch(getListFriendFailure(error.response));
    }
};

export const sendInviteToAll = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get("/roomTasks/tasks", null, true);
        dispatch(sendInviteToAllSuccess(response.data));
    } catch (error) {
        dispatch(sendInviteToAllFailure(error.response));
    }
};

export const getListTaskToChoose = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.post("/roomTasks/tasks", {
            softSkillId: data.softSkillId,
            statusId: data.softSkillId
        }, true);
        dispatch(getListTaskToChooseSuccess(response.data));
    } catch (error) {
        dispatch(getListTaskToChooseFailure(error.response));
    }
};

export const getTaskChooseById = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get(
            "/roomTasks/tasks/" + data,
            null,
            true
        );
        dispatch(getTaskChooseByIdSuccess(response.data.item.taskDetails));
    } catch (error) {
        dispatch(getTaskChooseByIdFailure(error.response));
    }
};

export const addTaskChooseForUser = (data) => async (dispatch) => {
    try {
        let dataReq = {
            tasksId: data.tasksId,
            roomId: data.roomId,
            softSkillId: data.softSkillId
        }
        const response = await requestService.post("/roomTasks/submitTasks", dataReq, true);
        dispatch(addTaskChooseForUserSuccess(response.data));
    } catch (error) {
        dispatch(addTaskChooseForUserFailure(error.response));
    }
};

export const completeTask = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.put(
            "/roomTasks/completed",
            data,
            true
        );
        dispatch(completeTaskSuccess(response.data));
    } catch (error) {
        dispatch(completeTaskFailure(error.response));
    }
};

export const cancelTask = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.put(
            "/roomTasks/cancel",
            data,
            true
        );
        dispatch(cancelTaskSuccess(response.data));
    } catch (error) {
        dispatch(cancelTaskFailure(error.response));
    }
};

export const acceptRoomTask = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.post(
            "/roomTasks/acceptDoRoomTask",
            data,
            true
        );
        dispatch(acceptRoomTaskSuccess(response.data));
    } catch (error) {
        dispatch(acceptRoomTaskFailure(error.response));
    }
};

export const outMeeting = (roomId, taskId) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.post(
            "/roomTasks/outRoom",
            { roomId: roomId, taskId: taskId },
            true
        );
        dispatch(outMeetSuccess(response.data));
    } catch (error) {
        dispatch(outMeetFailure(error.response));
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};


export const getMember = (data) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.post(
            "/roomTasks/members",
            data,
            true
        );
        dispatch(getMemberSuccess(response.data.item));
    } catch (error) {
        dispatch(getMemberFailure(error.response));
    }
};

export const resetUserRoomTaskInfo = (data) => async (dispatch) => {
    try {
        const response = await requestService.get(
            "/roomTasks/reset-task-info/" + data,
            null,
            true
        );
        dispatch(resetUserRoomTaskInfoSuccess(response.data.item));
    } catch (error) {
        dispatch(resetUserRoomTaskInfoFailure(error.response));
    }
};