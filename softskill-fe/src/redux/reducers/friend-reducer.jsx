import {
    ACCEPT_FRIEND_REQUEST_FAILURE,
    ACCEPT_FRIEND_REQUEST_SUCCESS,
    ADD_FRIENDS_FAILURE,
    ADD_FRIENDS_SUCCESS,
    DELETE_FRIEND_FAILURE,
    DELETE_FRIEND_SUCCESS,
    DENY_FRIEND_REQUEST_FAILURE,
    DENY_FRIEND_REQUEST_SUCCESS,
    GET_FRIENDS_BY_TASK_FAILURE,
    GET_FRIENDS_BY_TASK_SUCCESS,
    GET_FRIENDS_EVEN_REQUEST_SUCCESS,
    GET_FRIENDS_RECENT,
    GET_FRIENDS_SUCCESS,
    SEARCH_FRIENDS_FAILURE,
    SEARCH_FRIENDS_SUCCESS,
} from "../action-types/friend-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    isLoaded: false,
    friends: [],
    friend: {},
    friendTask: [],
    friendsSearch: [],
    success: "",
    error: {},
    friendsRecent: [],
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return { ...state, isLoaded: false, success: "", error: {} };

        case GET_FRIENDS_SUCCESS:
            return { ...state, friends: action.payload };

        case GET_FRIENDS_EVEN_REQUEST_SUCCESS:
            return { ...state, friend: action.payload };

        case ADD_FRIENDS_SUCCESS:
            return { ...state, success: action.payload };

        case ADD_FRIENDS_FAILURE:
            return { ...state, error: action.payload };

        case GET_FRIENDS_BY_TASK_SUCCESS:
            return { ...state, friendTask: action.payload };

        case GET_FRIENDS_BY_TASK_FAILURE:
            return { ...state, error: action.payload };
        case GET_FRIENDS_RECENT:
            return { ...state, friendsRecent: action.payload };

        case ACCEPT_FRIEND_REQUEST_SUCCESS:
            return { ...state };

        case DENY_FRIEND_REQUEST_FAILURE:
            return { ...state, error: action.payload };

        case DENY_FRIEND_REQUEST_SUCCESS:
            return { ...state };

        case ACCEPT_FRIEND_REQUEST_FAILURE:
            return { ...state, error: action.payload };

        case SEARCH_FRIENDS_SUCCESS:
            return { ...state, friendsSearch: action.payload, isLoaded: false };

        case SEARCH_FRIENDS_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        case DELETE_FRIEND_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false };

        case DELETE_FRIEND_FAILURE:
            return { ...state, error: action.payload, isLoaded: false };

        default:
            return state;
    }
};
export default reducer;
