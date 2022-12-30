import requestService from '../../utils/request-service';
import {
    getRoomCreatedSuccess,
    getRoomJoinedSuccess,
    getRoomsSuccess,
  
    registerBookingRoomFailure,
  
    registerBookingRoomSuccess,
  
    resetFormData,
    showLoader,
} from '../actions/booking-room-action';

export const resetFormRoom = () => async (dispatch) => {
    dispatch(resetFormData());
};

export const getRooms = (room) => async (dispatch) => {
    dispatch(showLoader());
    const body = {
        softSkillId: room.softSkillId,
        textSearch: room.textSearch,
        roomSizeFrom: room.roomSizeFrom,
        roomSizeTo: room.roomSizeTo,
        fromTime: room.fromTime,
        toTime: room.toTime,
    };
    const response = await requestService.post('/rooms/booking/softSkill', body, true);
    dispatch(getRoomsSuccess(response.data.item.rooms));
};

export const getRoomJoined = (room) => async (dispatch) => {
    dispatch(showLoader());
    const body = {
        softSkillId: room.softSkillId,
        textSearch: room.textSearch,
        roomSizeFrom: room.roomSizeFrom,
        roomSizeTo: room.roomSizeTo,
        fromTime: room.fromTime,
        toTime: room.toTime,
    };
    const response = await requestService.post('/rooms/booking/isJoined', body, true);
    dispatch(getRoomJoinedSuccess(response.data.item.rooms));
};

export const getRoomBookingCreateBy = (room) => async (dispatch) => {
    dispatch(showLoader());
    const body = {
        softSkillId: room.softSkillId,
        textSearch: room.textSearch,
        roomSizeFrom: room.roomSizeFrom,
        roomSizeTo: room.roomSizeTo,
        fromTime: room.fromTime,
        toTime: room.toTime,
    };
    const response = await requestService.post('/rooms/booking/createBy', body, true);
    dispatch(getRoomCreatedSuccess(response.data.item.rooms));
};

export const registerBookingRoom = (data) => async (dispatch) => {
    dispatch(showLoader());
    try {
        const response = await requestService.post('/roomTasks/registerRoomTask', data, true);
        dispatch(registerBookingRoomSuccess(response.data));
    } catch (error) {
        dispatch(registerBookingRoomFailure(error));
    }
};
