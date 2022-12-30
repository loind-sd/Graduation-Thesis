import { SHOW_LOADER } from "../action-types/_common-action-types";
import {
    GET_LIST_SOFT_SKILL_FAILURE,
    GET_LIST_SOFT_SKILL_SUCCESS,
} from "./../action-types/soft-skill-action-types";

const initialState = {
    softSkills: [],
    softSkill: {},
    isLoaded: false,
    error: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case GET_LIST_SOFT_SKILL_SUCCESS:
            return { ...state, softSkills: action.payload, isLoaded: false };

        case GET_LIST_SOFT_SKILL_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};

export default reducer;
