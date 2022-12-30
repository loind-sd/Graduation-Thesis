import {
    GET_GUIDELINE_BY_TASK_FAILURE,
    GET_GUIDELINE_BY_TASK_SUCCESS,
} from "../action-types/guideline-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const getGuidelineByTaskSuccess = (guideline) => ({
    type: GET_GUIDELINE_BY_TASK_SUCCESS,
    payload: guideline,
});

export const getGuidelineByTaskFailure = (error) => ({
    type: GET_GUIDELINE_BY_TASK_FAILURE,
    payload: error,
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
