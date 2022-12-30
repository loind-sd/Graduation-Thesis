import RequestService from '../../../utils/request-service';
import {
    deleteRecommendTasksFailure,
    deleteRecommendTasksSuccess,
    getRecommendTasksFailure,
    getRecommendTasksSuccess,
    updateRecommendTasksFailure,
    updateRecommendTasksSuccess,
} from '../../actions/author/recommend-task-action';
import { showLoader } from '../../actions/_common-action';

export const getRecommendTasks = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.get('/manager/recommend-task', true);
        dispatch(getRecommendTasksSuccess(response.data.item));
    } catch (error) {
        dispatch(getRecommendTasksFailure(error.response));
    }
};

export const updateRecommendTasks = (recommendTask) => async () => {
    try {
        const response = await RequestService.put('/manager/recommend-task', recommendTask, true);
        updateRecommendTasksSuccess(response.data.item);
    } catch (error) {
        updateRecommendTasksFailure(error.response);
    }
};

export const deleteRecommendTaskss = (ids) => async () => {
    try {
        const response = await RequestService.post('/manager/recommend-task/delete', ids, true);
        deleteRecommendTasksSuccess(response.data.item);
    } catch (error) {
        deleteRecommendTasksFailure(error.response);
    }
};
