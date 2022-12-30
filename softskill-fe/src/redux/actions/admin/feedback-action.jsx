import {
    CONFIRM_FEEDBACK_FAILURE,
    CONFIRM_FEEDBACK_SUCCESS,
    DELETE_FEEDBACK_FAILURE,
    DELETE_FEEDBACK_SUCCESS,
    GET_LIST_FEEDBACK_FAILURE,
    GET_LIST_FEEDBACK_SUCCESS
} from "../../action-types/admin/feedback-action-type";
import { FORM_RESET } from "../../action-types/_common-action-types";

export const getFeedbackSuccess = (feedbacks) => ({
    type: GET_LIST_FEEDBACK_SUCCESS,
    payload: feedbacks
});

export const getFeedbackFailure = (error) => ({
    type: GET_LIST_FEEDBACK_FAILURE,
    payload: error
});

export const deleteFeedbackSuccess = (feedbacks) => ({
    type: DELETE_FEEDBACK_SUCCESS,
    payload: feedbacks
});

export const deleteFeedbackFailure = (error) => ({
    type: DELETE_FEEDBACK_FAILURE,
    payload: error
});

export const confirmFeedbackSuccess = (feedbacks) => ({
    type: CONFIRM_FEEDBACK_SUCCESS,
    payload: feedbacks
});

export const confirmFeedbackFailure = (error) => ({
    type: CONFIRM_FEEDBACK_FAILURE,
    payload: error
});
export const resetFormData = () => ({
    type: FORM_RESET,
});
