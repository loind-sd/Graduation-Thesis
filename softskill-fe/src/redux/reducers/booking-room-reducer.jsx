import { FORM_RESET, GET_CREATED_ROOM_SUCCESS, GET_JOINED_ROOM_SUCCESS, GET_ROOMS_SUCCESS, REGISTER_BOOKING_ROOM_FAILURE, REGISTER_BOOKING_ROOM_SUCCESS, SHOW_LOADER } from "../action-types/booking-room-action-types";

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
        case GET_ROOMS_SUCCESS:
            return { ...state, rooms: action.payload, isLoaded: false }

        case GET_CREATED_ROOM_SUCCESS:
            return { ...state, created: action.payload, isLoaded: false }

        case GET_JOINED_ROOM_SUCCESS:
            return { ...state, joined: action.payload, isLoaded: false }

        case REGISTER_BOOKING_ROOM_SUCCESS:
            return { ...state, success: action.payload, isLoaded: false }

        case REGISTER_BOOKING_ROOM_FAILURE:
            return { ...state, error: action.payload, isLoaded: false }

        default:
            return state;
    }
}

export default reducer;
