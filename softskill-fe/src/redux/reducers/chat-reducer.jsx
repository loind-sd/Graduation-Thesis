import {
    DELETE_CHAT_FAILURE,
    DELETE_CHAT_SUCCESS,
    GET_CHAT_FAILURE,
    GET_CHAT_SUCCESS,
    SEND_CHAT_FAILURE,
    SEND_CHAT_SUCCESS,
    GET_CONTACT_SUCCESS,
    ADD_CONTACT_SUCCESS,
    UPDATE_CHAT_SUCCESS,
    UPDATE_CHAT_FAILURE,
    GET_FRIEND_SUCCESS,
    GET_FRIEND_FAILURE,
    OUT_ROOM_SUCCESS,
    OUT_ROOM_FAILURE,
    ADD_MEMBER_SUCCESS,
    ADD_MEMBER_FAILURE,
    GET_MEMBER_SUCCESS,
    GET_MEMBER_FAILURE,
    KICK_MEMBER_SUCCESS,
    KICK_MEMBER_FAILURE,
} from './../action-types/chat-action-type';

const initialState = {
    chats: [],
    contacts: [],
    friend: [],
    member: [],
    loading: false,
    msgType: '',
    contactType: '',
    success: '',
    error: '',
    errors: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_CHAT_SUCCESS:
            return { ...state, chats: action.payload, loading: false, msgType: action.msgType };
        case GET_CHAT_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case SEND_CHAT_SUCCESS: {
            return { ...state, chats: action.payload, loading: false, msgType: 'send' };
        }
        case SEND_CHAT_FAILURE: {
            return { ...state, error: action.payload, loading: false };
        }
        case DELETE_CHAT_SUCCESS:
            return { ...state, chats: action.payload, loading: false, msgType: 'delete' };
        case DELETE_CHAT_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case GET_CONTACT_SUCCESS:
            return { ...state, contacts: action.payload, loading: true, contactType: 'load' };
        case ADD_CONTACT_SUCCESS:
            return { ...state, contacts: action.payload, loading: true, contactType: 'add' };
        case ADD_MEMBER_SUCCESS:
            return { ...state, loading: false };
        case ADD_MEMBER_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case GET_MEMBER_SUCCESS:
            return { ...state, member: action.payload, loading: false };
        case GET_MEMBER_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case KICK_MEMBER_SUCCESS:
            return { ...state, loading: false };
        case KICK_MEMBER_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case GET_FRIEND_SUCCESS:
            return { ...state, friend: action.payload, loading: false };
        case GET_FRIEND_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case UPDATE_CHAT_SUCCESS:
            return { ...state, chats: action.payload, loading: false, msgType: 'update' };
        case UPDATE_CHAT_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case OUT_ROOM_SUCCESS:
            return { ...state, loading: false };
        case OUT_ROOM_FAILURE:
            return { ...state, error: action.payload, loading: false };
        default: {
            return state;
        }
    }
};

export default reducer;
