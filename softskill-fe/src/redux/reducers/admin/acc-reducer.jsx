import {
    ADD_ACCOUNTS_FAILURE,
    ADD_ACCOUNTS_SUCCESS,
    DELETE_ACCOUNTS_FAILURE,
    DELETE_ACCOUNTS_SUCCESS,
    GET_ACCOUNTS_FAILURE,
    GET_ACCOUNTS_SUCCESS,
    UPDATE_ACCOUNTS_FAILURE,
    UPDATE_ACCOUNTS_SUCCESS,
} from '../../action-types/admin/acc-action-type';

const initialState = {
    accounts: [],
    loading: false,
    accType: '',
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_ACCOUNTS_SUCCESS:
            return { ...state, accounts: action.payload, loading: false, accType: 'load' };

        case GET_ACCOUNTS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case ADD_ACCOUNTS_SUCCESS:
            return { ...state, loading: false, accType: 'add', success: action.payload };

        case ADD_ACCOUNTS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case UPDATE_ACCOUNTS_SUCCESS: {
            return { ...state, accounts: action.payload, loading: false };
        }

        case UPDATE_ACCOUNTS_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case DELETE_ACCOUNTS_SUCCESS:
            return { ...state, accounts: action.payload, loading: false };

        case DELETE_ACCOUNTS_FAILURE:
            return { ...state, error: action.payload, loading: false };

        default: {
            return state;
        }
    }
};

export default reducer;
