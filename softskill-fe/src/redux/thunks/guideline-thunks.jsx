import { reset } from "../actions/_common-action";
import requestService from "../../utils/request-service";
import {
    getGuidelineByTaskFailure,
    getGuidelineByTaskSuccess,
} from "../actions/guideline-action";

export const getGuidelineByTask = (data) => async (dispatch) => {
    try {
        const response = await requestService.get(
            "/tasks/guideline/" + data,
            null,
            true
        );
        dispatch(getGuidelineByTaskSuccess(response.data));
    } catch (error) {
        dispatch(getGuidelineByTaskFailure(error.response));
    }
};

export const formReset = () => async (dispatch) => {
    dispatch(reset());
};
