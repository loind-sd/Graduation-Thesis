import {
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  LOGOUT_SUCCESS,
  LOGOUT_FAILURE,
  GET_USER_SUCCESS,
  GET_USER_FAILURE,
} from "../action-types/auth-action-types";
import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

const initialState = {
  isLoaded: false,
  user: {},
  isLoggedIn: false,
  success: "",
  error: {},
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case SHOW_LOADER:
      return { ...state, isLoaded: true, errors: {} };

    case FORM_RESET:
      return {
        ...state, isLoaded: false, success: "", error: {}
      };

    case LOGIN_SUCCESS:
      return { ...state, user: action.payload, success: "success" };

    case LOGIN_FAILURE:
      return { ...state, error: action.payload };

    case LOGOUT_SUCCESS:
      return { ...state };

    case LOGOUT_FAILURE:
      return { ...state, error: action.payload };

    case GET_USER_SUCCESS:
      return { ...state, user: action.payload, isLoggedIn: true, isLoaded: false }

    case GET_USER_FAILURE:
      return { ...state, error: action.payload, isLoaded: false }

    default:
      return state;
  }
};

export default reducer;
