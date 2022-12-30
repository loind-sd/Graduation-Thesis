import {
    ADD_SOFTSKILLS_FAILURE,
    ADD_SOFTSKILLS_SUCCESS,
    DELETE_SOFTSKILLS_FAILURE,
    DELETE_SOFTSKILLS_SUCCESS,
    GET_SOFTSKILLS_FAILURE,
    GET_SOFTSKILLS_SUCCESS,
    UPDATE_SOFTSKILLS_FAILURE,
    UPDATE_SOFTSKILLS_SUCCESS,
} from '../../action-types/admin/softskill-action-type';

export const getSoftSkillSuccess = (softskills) => ({
    type: GET_SOFTSKILLS_SUCCESS,
    payload: softskills,
});

export const getSoftSkillFailure = (error) => ({
    type: GET_SOFTSKILLS_FAILURE,
    payload: error,
});

export const addSoftSkillSuccess = (softskills) => ({
    type: ADD_SOFTSKILLS_SUCCESS,
    payload: softskills,
});

export const addSoftSkillFailure = (error) => ({
    type: ADD_SOFTSKILLS_FAILURE,
    payload: error,
});

export const updateSoftSkillSuccess = (softskills) => ({
    type: UPDATE_SOFTSKILLS_SUCCESS,
    payload: softskills,
});

export const updateSoftSkillFailure = (error) => ({
    type: UPDATE_SOFTSKILLS_FAILURE,
    payload: error,
});

export const deleteSoftSkillSuccess = (softskills) => ({
    type: DELETE_SOFTSKILLS_SUCCESS,
    payload: softskills,
});

export const deleteSoftSkillFailure = (error) => ({
    type: DELETE_SOFTSKILLS_FAILURE,
    payload: error,
});
