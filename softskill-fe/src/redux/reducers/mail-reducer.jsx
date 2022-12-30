import {
    SEND_RESET_PASSWORD_FAILURE,
    SEND_RESET_PASSWORD_SUCCESS,
    SEND_INVITE_FAILURE,
    SEND_INVITE_SUCCESS,
    SEND_THANK_FAILURE,
    SEND_THANK_SUCCESS,
} from '../action-types/mail-action-types';

const initialState = {
    mails: {},
    loading: false,
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SEND_INVITE_SUCCESS:
            return { ...state, mails: action.payload, loading: false };

        case SEND_INVITE_FAILURE:
            return { ...state, error: action.payload, loading: false };

        case SEND_THANK_SUCCESS: {
            return { ...state, mails: action.payload, loading: false };
        }

        case SEND_THANK_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }

        case SEND_RESET_PASSWORD_SUCCESS:
            return { ...state, mails: action.payload, loading: false };

        case SEND_RESET_PASSWORD_FAILURE:
            return { ...state, error: action.payload, loading: false };

        default: {
            return state;
        }
    }
};

export default reducer;
