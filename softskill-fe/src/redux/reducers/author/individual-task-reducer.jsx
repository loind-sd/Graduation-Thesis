import {
    ADD_INDIVIDUAL_TASKS_FAILURE,
    ADD_INDIVIDUAL_TASKS_SUCCESS,
    DELETE_INDIVIDUAL_TASKS_FAILURE,
    DELETE_INDIVIDUAL_TASKS_SUCCESS,
    GET_INDIVIDUAL_TASKS_FAILURE,
    GET_INDIVIDUAL_TASKS_SUCCESS,
    UPDATE_INDIVIDUAL_TASKS_FAILURE,
    UPDATE_INDIVIDUAL_TASKS_SUCCESS,
} from '../../action-types/author/individual-task-action-type';

const initialState = {
    individualTasks: [],
    loading: false,
    gtType: '',
    softSkill: [],
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_INDIVIDUAL_TASKS_SUCCESS:
            return {
                ...state,
                individualTasks: action.payload.data,
                softSkill: action.payload.softSkill,
                loading: false,
                itType: 'load',
            };

        case GET_INDIVIDUAL_TASKS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case ADD_INDIVIDUAL_TASKS_SUCCESS: {
            return { ...state, individualTasks: action.payload, loading: false, itType: 'add' };
        }

        case ADD_INDIVIDUAL_TASKS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case UPDATE_INDIVIDUAL_TASKS_SUCCESS: {
            return { ...state, individualTasks: action.payload, loading: false };
        }

        case UPDATE_INDIVIDUAL_TASKS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case DELETE_INDIVIDUAL_TASKS_SUCCESS:
            return { ...state, individualTasks: action.payload, loading: false };

        case DELETE_INDIVIDUAL_TASKS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        default: {
            return state;
        }
    }
};

export default reducer;
