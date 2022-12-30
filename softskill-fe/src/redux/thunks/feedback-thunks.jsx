import requestService from "../../utils/request-service";
import { createNewFeedbackFailure, createNewFeedbackSuccess, getFeedbackTypeSuccess } from "../actions/feedback-action";
// import { showLoader } from './../actions/_common-action';


export const createNewFeedback = (data) => async (dispatch) => {
    try {
        const response = await requestService.post("/feedback", data, true);
        dispatch(createNewFeedbackSuccess(response));
    } catch (error) {
        dispatch(createNewFeedbackFailure(error.response.data));
    }
};

export const getFeedbackType = () => async (dispatch) => {
    const response = await requestService.get("/feedback-title", true);
    dispatch(getFeedbackTypeSuccess(response.data.item));
};