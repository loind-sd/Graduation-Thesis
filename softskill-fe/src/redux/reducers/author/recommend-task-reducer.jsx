import {
    DELETE_RECOMMEND_TASKS_FAILURE,
    DELETE_RECOMMEND_TASKS_SUCCESS,
    GET_RECOMMEND_TASKS_FAILURE,
    GET_RECOMMEND_TASKS_SUCCESS,
    UPDATE_RECOMMEND_TASKS_FAILURE,
    UPDATE_RECOMMEND_TASKS_SUCCESS,
} from '../../action-types/author/recommend-task-action-type';

const initialState = {
    recommendTasks: [],
    loading: false,
    rtType: '',
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_RECOMMEND_TASKS_SUCCESS:
            return {
                ...state,
                recommendTasks: action.payload,
                loading: false,
                rtType: 'load',
            };

        case GET_RECOMMEND_TASKS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case UPDATE_RECOMMEND_TASKS_SUCCESS: {
            return { ...state, recommendTasks: action.payload, loading: false };
        }

        case UPDATE_RECOMMEND_TASKS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case DELETE_RECOMMEND_TASKS_SUCCESS:
            return { ...state, recommendTasks: action.payload, loading: false };

        case DELETE_RECOMMEND_TASKS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        default: {
            return state;
        }
    }
};

export default reducer;
