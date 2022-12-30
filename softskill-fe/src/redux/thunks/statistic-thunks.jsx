import requestService from "../../utils/request-service";
import {
  getComunitySuccess,
  getPersonalSuccess,
  getRankInfoSuccess,
  showLoader,
} from "../actions/statistic-action";

export const getCommunity = () => async (dispatch) => {
  dispatch(showLoader());
  const response = await requestService.get("/user/statistic/softSkill", true);
  dispatch(getComunitySuccess(response.data.item.list));
};
export const getPersonal = (time) => async (dispatch) => {
  dispatch(showLoader());
  const body = {
    userId: null,
    time: time,
  };
  const response = await requestService.post(
    "/user/statistic/task",
    body,
    true
  );
  dispatch(getPersonalSuccess(response.data.item.list));
};

export const getRankInfo = (time) => async (dispatch) => {
  dispatch(showLoader());
  const body = {
    userId: null,
    time: time,
  };
  const response = await requestService.post("/user/statistic/rank", body, true);
  dispatch(getRankInfoSuccess(response.data.item));
};
