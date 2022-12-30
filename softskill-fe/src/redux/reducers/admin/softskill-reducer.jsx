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

const initialState = {
    softskills: [],
    loading: false,
    ssType: '',
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_SOFTSKILLS_SUCCESS:
            return { ...state, softskills: action.payload, loading: false, ssType: 'load' };

        case GET_SOFTSKILLS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case ADD_SOFTSKILLS_SUCCESS: {
            return { ...state, softskills: action.payload, loading: false, ssType: 'add' };
        }

        case ADD_SOFTSKILLS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case UPDATE_SOFTSKILLS_SUCCESS: {
            return { ...state, softskills: action.payload, loading: false };
        }

        case UPDATE_SOFTSKILLS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case DELETE_SOFTSKILLS_SUCCESS:
            return { ...state, softskills: action.payload, loading: false };

        case DELETE_SOFTSKILLS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        default: {
            return state;
        }
    }
};

export default reducer;
