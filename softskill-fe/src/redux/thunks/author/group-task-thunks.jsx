import RequestService from '../../../utils/request-service';
import {
    addGroupTasksFailure,
    addGroupTasksSuccess,
    deleteGroupTasksFailure,
    deleteGroupTasksSuccess,
    getGroupTasksFailure,
    getGroupTasksSuccess,
    updateGroupTasksFailure,
    updateGroupTasksSuccess,
} from '../../actions/author/group-task-action';
import { showLoader } from '../../actions/_common-action';

export const getGroupTasks = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.get('/manager/tasks/group-task', true);
        dispatch(getGroupTasksSuccess(response.data.item));
    } catch (error) {
        dispatch(getGroupTasksFailure(error.response));
    }
};

export const addGroupTasks =
    (file, taskName, taskContent, requiredTask, softSkillId, startDate, endDate, status) => async (dispatch) => {
        try {
            const response = await RequestService.post(
                `/manager/tasks/group-task?softSkillId=${softSkillId}&&taskName=${taskName}&&taskContent=${taskContent}&&requiredTask=${requiredTask}&&startDate=${startDate}&&endDate=${endDate}&&status=${status}`,
                file,
                true,
            );
            dispatch(addGroupTasksSuccess(response.data.item.groupTask));
        } catch (error) {
            dispatch(addGroupTasksFailure(error.response));
        }
    };

export const updateGroupTasks =
    (file, taskName, taskContent, requiredTask, softSkillId, startDate, endDate, status, id, guideLineChange, no) =>
    async (dispatch) => {
        try {
            const response = await RequestService.put(
                `/manager/tasks/group-task?id=${id}&&guideLineChange=${guideLineChange}&&softSkillId=${softSkillId}&&taskName=${taskName}&&taskContent=${taskContent}&&requiredTask=${requiredTask}&&startDate=${startDate}&&endDate=${endDate}&&status=${status}&&no=${no}`,
                file,
                true,
            );
            dispatch(updateGroupTasksSuccess(response.data.item));
        } catch (error) {
            dispatch(updateGroupTasksFailure(error.response));
        }
    };

export const deleteGroupTaskss = (ids) => async () => {
    try {
        const response = await RequestService.post('/manager/tasks/group-task/delete', ids, true);
        deleteGroupTasksSuccess(response.data.item);
    } catch (error) {
        deleteGroupTasksFailure(error.response);
    }
};
