import requestService from '../../../utils/request-service';
import {
    confirmFeedbackFailure,
    confirmFeedbackSuccess,
    deleteFeedbackFailure,
    deleteFeedbackSuccess,
    getFeedbackFailure,
    getFeedbackSuccess,
} from '../../actions/admin/feedback-action';
import { showLoader } from '../../actions/_common-action';

export const getFeedbacks = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get('/admin/feedback', null, true);
        dispatch(getFeedbackSuccess(response.data.item));
    } catch (error) {
        dispatch(getFeedbackFailure(error.response));
    }
};

export const deleteFeedback = (feebackId) => async (dispatch) => {
    try {
        const response = await requestService.delete('/admin/feedback/' + feebackId, null, true);
        dispatch(deleteFeedbackSuccess(response.data.item));
    } catch (error) {
        dispatch(deleteFeedbackFailure(error.response));
    }
};

export const confirmFeedback = (FeedbackRequest) => async (dispatch) => {
    try {
        const response = await requestService.put('/admin/feedback/', FeedbackRequest, true);
        dispatch(confirmFeedbackSuccess(response.data.item));
    } catch (error) {
        dispatch(confirmFeedbackFailure(error.response));
    }
};
