import requestService from '../../../utils/request-service';
import {
    getNumberJoinSuccess,
    getNumberRoomSuccess,
    getNumberUserSuccess,
    getRankInfoSuccess,
    getSoftSkillSuccess,
    getTotalRoomSuccess,
} from './../../actions/admin/statistic-action';
import { showLoader } from './../../actions/_common-action';

export const getNumberUser = () => async (dispatch) => {
    dispatch(showLoader());
    const response = await requestService.get('/admin/statistic/numberUsers', true);
    dispatch(getNumberUserSuccess(response.data.item));
};

export const getTotalRooms = () => async (dispatch) => {
    dispatch(showLoader());
    const response = await requestService.get('/admin/statistic/totalRooms', true);
    dispatch(getTotalRoomSuccess(response.data.item));
};

export const getNumberRoom = (body) => async (dispatch) => {
    dispatch(showLoader());
    const response = await requestService.post('/admin/statistic/numberRoom', body, true);
    dispatch(getNumberRoomSuccess(response.data.item));
};

export const getSoftSkill = () => async (dispatch) => {
    dispatch(showLoader());
    const response = await requestService.get('/admin/statistic/softSkill', true);
    dispatch(getSoftSkillSuccess(response.data.item));
};

export const getNumberJoin = (body) => async (dispatch) => {
    dispatch(showLoader());
    const response = await requestService.post('/admin/statistic/numberJoin', body, true);
    dispatch(getNumberJoinSuccess(response.data.item));
};

export const getRankInfo = () => async (dispatch) => {
    dispatch(showLoader());
    const response = await requestService.get('/admin/statistic/rank', true);
    dispatch(getRankInfoSuccess(response.data.item));
};
