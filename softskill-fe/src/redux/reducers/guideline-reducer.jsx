import { GET_GUIDELINE_BY_TASK_FAILURE, GET_GUIDELINE_BY_TASK_SUCCESS } from "../action-types/guideline-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    isLoaded: false,
    guidelines: [],
    guideline: {},
    success: {},
    error: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return { ...state, isLoaded: false, success: {}, error: {} };

        case GET_GUIDELINE_BY_TASK_SUCCESS:
            return { ...state, guideline: action.payload };

        case GET_GUIDELINE_BY_TASK_FAILURE:
            return { ...state, error: action.payload };

        default:
            return state;
    }
};
export default reducer;
