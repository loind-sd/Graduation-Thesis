import {
    CREATE_NEW_FEEDBACK_SUCCESS,
    CREATE_NEW_FEEDBACK_FAILURE,
    GET_FEEDBACK_TYPE_SUCCESS
} from "../action-types/feedback-action-types";
import { FORM_RESET } from "../action-types/_common-action-types";

export const createNewFeedbackSuccess = (feedback) => ({
    type: CREATE_NEW_FEEDBACK_SUCCESS,
    payload: feedback,
});

export const createNewFeedbackFailure = (error) => ({
    type: CREATE_NEW_FEEDBACK_FAILURE,
    payload: error,
});

export const resetFormData = () => ({
    type: FORM_RESET,
});
export const getFeedbackTypeSuccess = (feedbackType) => ({
    type: GET_FEEDBACK_TYPE_SUCCESS,
    payload: feedbackType,
  });
