import RequestService from '../../../utils/request-service';
import {
    addSoftSkillFailure,
    addSoftSkillSuccess,
    deleteSoftSkillFailure,
    deleteSoftSkillSuccess,
    getSoftSkillFailure,
    getSoftSkillSuccess,
    updateSoftSkillFailure,
    updateSoftSkillSuccess,
} from '../../actions/admin/softskill-action';
import { showLoader } from '../../actions/_common-action';

export const getSoftSkill = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.get('/admin/softSkill', true);
        dispatch(getSoftSkillSuccess(response.data.item));
    } catch (error) {
        dispatch(getSoftSkillFailure(error.response));
    }
};

export const addSoftSkill = (softskill) => async (dispatch) => {
    try {
        const response = await RequestService.post('/admin/softSkill', softskill, true);
        dispatch(addSoftSkillSuccess(response.data.item.softSkill));
    } catch (error) {
        dispatch(addSoftSkillFailure(error.response));
    }
};

export const updateSoftSkill = (softskill) => async () => {
    try {
        const response = await RequestService.put('/admin/softSkill', softskill, true);
        updateSoftSkillSuccess(response.data.item);
    } catch (error) {
        updateSoftSkillFailure(error.response);
    }
};

export const deleteSoftSkills = (ids) => async () => {
    try {
        const response = await RequestService.post('/admin/softSkill/delete', ids, true);
        deleteSoftSkillSuccess(response.data.item);
    } catch (error) {
        deleteSoftSkillFailure(error.response);
    }
};
