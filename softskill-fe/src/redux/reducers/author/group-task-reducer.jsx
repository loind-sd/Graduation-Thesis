import {
    ADD_GROUP_TASKS_FAILURE,
    ADD_GROUP_TASKS_SUCCESS,
    DELETE_GROUP_TASKS_FAILURE,
    DELETE_GROUP_TASKS_SUCCESS,
    GET_GROUP_TASKS_FAILURE,
    GET_GROUP_TASKS_SUCCESS,
    UPDATE_GROUP_TASKS_FAILURE,
    UPDATE_GROUP_TASKS_SUCCESS,
} from '../../action-types/author/group-task-action-type';

const initialState = {
    groupTasks: [],
    loading: false,
    gtType: '',
    personalTask: [],
    softSkill: [],
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_GROUP_TASKS_SUCCESS:
            return {
                ...state,
                groupTasks: action.payload.data,
                personalTask: action.payload.personalTask,
                softSkill: action.payload.softSkill,
                loading: false,
                gtType: 'load',
            };

        case GET_GROUP_TASKS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case ADD_GROUP_TASKS_SUCCESS: {
            return { ...state, groupTasks: action.payload, loading: false, gtType: 'add' };
        }

        case ADD_GROUP_TASKS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case UPDATE_GROUP_TASKS_SUCCESS: {
            return { ...state, groupTasks: action.payload, loading: false, gtType: 'update' };
        }

        case UPDATE_GROUP_TASKS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case DELETE_GROUP_TASKS_SUCCESS:
            return { ...state, groupTasks: action.payload, loading: false };

        case DELETE_GROUP_TASKS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        default: {
            return state;
        }
    }
};

export default reducer;
