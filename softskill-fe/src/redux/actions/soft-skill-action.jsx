import {
    GET_LIST_SOFT_SKILL_FAILURE,
    GET_LIST_SOFT_SKILL_SUCCESS,
} from "../action-types/soft-skill-action-types";

export const getListSoftSkillSuccess = (user) => ({
    type: GET_LIST_SOFT_SKILL_SUCCESS,
    payload: user,
});

export const getListSoftSkillFailure = (error) => ({
    type: GET_LIST_SOFT_SKILL_FAILURE,
    payload: error,
});
