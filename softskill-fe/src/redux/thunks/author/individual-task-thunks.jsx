import RequestService from '../../../utils/request-service';
import {
    addIndividualTasksFailure,
    addIndividualTasksSuccess,
    deleteIndividualTasksFailure,
    deleteIndividualTasksSuccess,
    getIndividualTasksFailure,
    getIndividualTasksSuccess,
    updateIndividualTasksFailure,
    updateIndividualTasksSuccess,
} from '../../actions/author/individual-task-action';
import { showLoader } from '../../actions/_common-action';

export const getIndividualTasks = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.get('/manager/tasks/individual-task', true);
        dispatch(getIndividualTasksSuccess(response.data.item));
    } catch (error) {
        dispatch(getIndividualTasksFailure(error.response));
    }
};

export const addIndividualTasks = (individualTask) => async (dispatch) => {
    try {
        const response = await RequestService.post('/manager/tasks/individual-task', individualTask, true);
        dispatch(addIndividualTasksSuccess(response.data.item.individualTask));
    } catch (error) {
        dispatch(addIndividualTasksFailure(error.response));
    }
};

export const updateIndividualTasks = (individualTask) => async () => {
    try {
        const response = await RequestService.put('/manager/tasks/individual-task', individualTask, true);
        updateIndividualTasksSuccess(response.data.item);
    } catch (error) {
        updateIndividualTasksFailure(error.response);
    }
};

export const deleteIndividualTaskss = (ids) => async () => {
    try {
        const response = await RequestService.post('/manager/tasks/individual-task/delete', ids, true);
        deleteIndividualTasksSuccess(response.data.item);
    } catch (error) {
        deleteIndividualTasksFailure(error.response);
    }
};
