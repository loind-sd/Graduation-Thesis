import {
    DELETE_ROOM_FAILURE,
    DELETE_ROOM_SUCCESS,
    GET_LIST_ROOM_FAILURE,
    GET_LIST_ROOM_SUCCESS
} from "../../action-types/admin/room-action-type"


const initialState = {
    rooms: [],
    isLoaded: false,
    success: "",
    error: {},
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_LIST_ROOM_SUCCESS:
            return { ...state, rooms: action.payload, loading: false };
        case GET_LIST_ROOM_FAILURE:
            return { ...state, error: action.payload, loading: false };
        case DELETE_ROOM_SUCCESS:
            return { ...state, rooms: action.payload, loading: false };
        case DELETE_ROOM_FAILURE:
            return { ...state, error: action.payload, loading: false };
        default: {
            return state;
        }
    }
};

export default reducer;