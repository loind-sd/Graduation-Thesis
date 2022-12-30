import requestService from '../../utils/request-service';
import {
    createNewRoomFailure,
    createNewRoomSuccess,
    deleteRoomFailure,
    deleteRoomSuccess,
    getRoomCreatedSuccess,
    getRoomJoinedSuccess,
    getRoomsSuccess,
    resetFormData,
} from '../actions/room-action';
import { getRoomsForChatSuccess } from '../actions/room-noti-action';
import { showLoader } from '../actions/_common-action';
import { getRoomBookingCreateBy } from './room-booking-thunks';

export const createNewRoom = (data, history) => async (dispatch) => {
    try {
        const response = await requestService.post('/rooms', data, true);
        dispatch(createNewRoomSuccess(response.data.status));
        if (response?.data?.item?.taskId === null) {
            history.push({
                pathname: "/meet",
                state: { singleRoom: response.data.item.room },
            })
        }
    } catch (error) {
        dispatch(createNewRoomFailure(error.response.data.status));
    }
};

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
    const response = await requestService.post('/rooms/active/softSkill', body, true);
    dispatch(getRoomsSuccess(response.data.item.rooms));
};

export const getRoomsForChat = (id) => async (dispatch) => {
    dispatch(showLoader());
    const body = {
        roomId: id,
    };
    const response = await requestService.post('/rooms/active/chat', body, true);
    dispatch(getRoomsForChatSuccess(response.data.item.rooms));
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
    const response = await requestService.post('/rooms/active/isJoined', body, true);
    dispatch(getRoomJoinedSuccess(response.data.item.rooms));
};

export const getRoomCreateBy = (room) => async (dispatch) => {
    dispatch(showLoader());
    const body = {
        softSkillId: room.softSkillId,
        textSearch: room.textSearch,
        roomSizeFrom: room.roomSizeFrom,
        roomSizeTo: room.roomSizeTo,
        fromTime: room.fromTime,
        toTime: room.toTime,
    };
    const response = await requestService.post('/rooms/active/createBy', body, true);
    dispatch(getRoomCreatedSuccess(response.data.item.rooms));
};

export const deleteRoom = (data) => async (dispatch) => {
    // dispatch(showLoader());
    try {
        const response = await requestService.delete('/rooms', data, true);
        dispatch(deleteRoomSuccess(response.data));
        let room = {
            softSkillId: null,
            textSearch: '',
            roomSizeFrom: null,
            roomSizeTo: null,
            toTime: null,
            fromTime: null,
        };
        dispatch(getRoomBookingCreateBy(room));
    } catch (error) {
        dispatch(deleteRoomFailure(error.response.data));
    }
};
