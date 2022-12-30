import {
  FORM_RESET,
  GET_COMMUNITY_SUCCESS,
  GET_PERSONAL_SUCCESS,
  GET_RANK_INFO_SUCCESS,
  SHOW_LOADER,
} from "../action-types/statistic-action-types";

export const showLoader = () => ({
  type: SHOW_LOADER,
});

export const reset = () => ({
  type: FORM_RESET,
});

export const getComunitySuccess = (community) => ({
  type: GET_COMMUNITY_SUCCESS,
  payload: community,
});

export const getPersonalSuccess = (personal) => ({
  type: GET_PERSONAL_SUCCESS,
  payload: personal,
});

export const getRankInfoSuccess = (rankInfo) => ({
    type: GET_RANK_INFO_SUCCESS,
    payload: rankInfo,
  });
