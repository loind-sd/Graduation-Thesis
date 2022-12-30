import requestService from '../../../utils/request-service';
import {
    deleteRoomFailure,
    deleteRoomSuccess,
    getRoomsFailure,
    getRoomsSuccess,
} from '../../actions/admin/room-action';
import { showLoader } from '../../actions/_common-action';

export const getRooms = () => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await requestService.get('/admin/rooms', null, true);
        dispatch(getRoomsSuccess(response.data.item));
    } catch (error) {
        dispatch(getRoomsFailure(error.response));
    }
};

export const deleteRoom = (roomDeleteRequest) => async (dispatch) => {
    try {
        const response = await requestService.delete('/admin/rooms/', roomDeleteRequest, true);
        dispatch(deleteRoomSuccess(response.data.item));
    } catch (error) {
        dispatch(deleteRoomFailure(error.response));
    }
};
