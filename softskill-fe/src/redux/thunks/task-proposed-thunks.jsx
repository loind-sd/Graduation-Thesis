import requestService from "../../utils/request-service";
import {
    addTaskProposedFailure,
    addTaskProposedSuccess,
    getTimeCheckAddRecommendTaskSuccess,
    getTimeCheckAddRecommendTaskFailure,
    getTasksProposedFailure,
    getTasksProposedSuccess,
} from "../actions/task-proposed-action";
import { reset } from "../actions/_common-action";
import { showLoader } from "./../actions/_common-action";

export const getTimeCheckAddRecommendTask = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get("/recommend-task/checkTime", null, true);
        dispatch(getTimeCheckAddRecommendTaskSuccess(response.data));
    } catch (error) {
        dispatch(getTimeCheckAddRecommendTaskFailure(error.response));
    }
};

export const getTasksProposed = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get("/recommend-task", null, true);
        dispatch(getTasksProposedSuccess(response.data));
    } catch (error) {
        dispatch(getTasksProposedFailure(error.response));
    }
};

export const addTaskProposed =
    (file, softSkillId, typeTask, taskDescription, guideline, history) =>
        async (dispatch) => {
            try {
                const response = await requestService.post(
                    `/recommend-task?softSkillId=${softSkillId}&&typeTask=${typeTask}&&taskDescription=${taskDescription}&&guideline=${guideline}`,
                    file,
                    true
                );
                dispatch(addTaskProposedSuccess(response.data));
                history.push("/tasksProposed");
            } catch (error) {
                dispatch(addTaskProposedFailure(error.response.data));
            }
        };

export const formResetTaskProposed = () => async (dispatch) => {
    dispatch(reset());
};
