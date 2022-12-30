import {
  FORM_RESET,
  GET_RANK_INFO_SUCCESS,
} from "../action-types/statistic-action-types";

const initialState = {
  rankInfo: {},
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case FORM_RESET:
      return { ...state };
    case GET_RANK_INFO_SUCCESS:
      return { ...state, rankInfo: action.payload };
    default:
      return state;
  }
};

export default reducer;
