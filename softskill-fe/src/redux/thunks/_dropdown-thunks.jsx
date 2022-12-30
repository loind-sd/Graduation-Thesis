import requestService from "../../utils/request-service";
import {
    getRecommendTaskTypeFailure,
    getRecommendTaskTypeSuccess,
    getTasksBySoftSkillIdFailure,
    getTasksBySoftSkillIdSuccess,
} from "../actions/_dropdown-action";

export const getTasksBySoftSkillId = (id) => async (dispatch) => {
    try {
        const response = await requestService.get(
            "/dropdown/taskBySoftSkillId/" + id, null,
            true
        );
        dispatch(getTasksBySoftSkillIdSuccess(response.data));
    } catch (error) {
        dispatch(getTasksBySoftSkillIdFailure(error.response.data));
    }
};

export const getRecommendTaskType = () => async (dispatch) => {
    try {
        const response = await requestService.get(
            "/dropdown/recommendTaskType", null,
            true
        );
        dispatch(getRecommendTaskTypeSuccess(response.data));
    } catch (error) {
        dispatch(getRecommendTaskTypeFailure(error.response.data));
    }
};