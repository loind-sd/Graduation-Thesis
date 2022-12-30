import requestService from "../../utils/request-service";
import {
    getListSoftSkillFailure,
    getListSoftSkillSuccess,
} from "../actions/soft-skill-action";

export const getListSoftSkill = () => async (dispatch) => {
    try {
        const response = await requestService.get("/softSkill", null, true);
        dispatch(getListSoftSkillSuccess(response.data));
    } catch (error) {
        dispatch(getListSoftSkillFailure(error.response.data));
    }
};
