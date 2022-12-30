import { GET_ROOMS_FOR_CHAT_SUCCESS } from "../action-types/room-action-noti-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
    created: {},
    joined: {},
    room: {},
    isLoaded: false,
    success: "",
    error: {},
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return { ...state, isLoaded: true };

        case FORM_RESET:
            return {
                ...state,
                loading: false,
                success: "",
                error: {},
            };
        case GET_ROOMS_FOR_CHAT_SUCCESS:
            return { ...state, room: action.payload }
        default:
            return state;
    }
}

export default reducer;
