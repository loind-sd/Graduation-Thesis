import RequestService from '../../utils/request-service';
import { getUserFailure, getUserSuccess } from '../actions/auth-action';
import {
    deleteChatFailure,
    deleteChatSuccess,
    getChatFailure,
    getChatSuccess,
    sendChatFailure,
    sendChatSuccess,
    getContactSuccess,
    addContactSuccess,
    getFriendSuccess,
    getFriendFailure,
    outRoomSuccess,
    outRoomFailure,
    addMemberSuccess,
    addMemberFailure,
    updateChatSuccess,
    updateChatFailure,
    getMemberSuccess,
    getMemberFailure,
    kickMemberSuccess,
    kickMemberFailure,
} from '../actions/chat-action';
import { showLoader } from '../actions/_common-action';

export const getWorldChat = (body) => async (dispatch) => {
    try {
        dispatch(showLoader());
        const response = await RequestService.post('/world-chat/load', body, true);
        dispatch(getChatSuccess(response.data.item.item));
    } catch (error) {
        dispatch(getChatFailure(error.response));
    }
};

export const sendWorldChat = (chats) => async (dispatch) => {
    try {
        const response = await RequestService.post('/world-chat', chats, true);
        dispatch(sendChatSuccess(response.data.item.item.chat));
    } catch (error) {
        dispatch(sendChatFailure(error.response));
    }
};

export const deleteWorldChat = (chats) => async (dispatch) => {
    try {
        const response = await RequestService.delete(`/world-chat/${chats}`, true);
        dispatch(deleteChatSuccess(response.data.item.id));
    } catch (error) {
        dispatch(deleteChatFailure(error.response));
    }
};

export const getAllContacts = () => async (dispatch) => {
    try {
        const response = await RequestService.get('/world-chat/contact', null, true);
        dispatch(getContactSuccess(response.data.item));
    } catch (error) {}
};

export const addNewContacts = (contact) => async (dispatch) => {
    try {
        const response = await RequestService.post(`/world-chat/contact`, contact, true);
        dispatch(addContactSuccess(response.data.item));
    } catch (error) {}
};

export const getFriend = (friend) => async (dispatch) => {
    try {
        const response = await RequestService.post(`/world-chat/friend`, friend, true);
        dispatch(getFriendSuccess(response.data.item));
    } catch (error) {
        dispatch(getFriendFailure(error.response));
    }
};

export const outRoom = (contact) => async (dispatch) => {
    try {
        const response = await RequestService.delete(`/world-chat/contact`, contact, true);
        dispatch(outRoomSuccess(response.data.item));
    } catch (error) {
        dispatch(outRoomFailure(error.response));
    }
};

export const addMembers = (friends) => async (dispatch) => {
    try {
        const response = await RequestService.put(`/world-chat/contact`, friends, true);
        dispatch(addMemberSuccess(response.data.item));
    } catch (error) {
        dispatch(addMemberFailure(error.response));
    }
};

export const updateChat = (chats) => async (dispatch) => {
    try {
        const response = await RequestService.put(`/world-chat`, chats, true);
        dispatch(updateChatSuccess(response.data.item));
    } catch (error) {
        dispatch(updateChatFailure(error.response));
    }
};

export const getOtherUserInfo = (id) => async (dispatch) => {
    try {
        // dispatch(showLoader());
        const response = await RequestService.get(`/user/info/${id}`, null, true);
        dispatch(getUserSuccess(response.data.item));
    } catch (error) {
        dispatch(getUserFailure(error.response.data));
    }
};

export const getMember = (data) => async (dispatch) => {
    try {
        const response = await RequestService.post(`/world-chat/member`, data, true);
        dispatch(getMemberSuccess(response.data.item));
    } catch (error) {
        dispatch(getMemberFailure(error.response));
    }
};

export const kickMember = (body) => async (dispatch) => {
    try {
        const response = await RequestService.delete(`/world-chat/member`, body, true);
        dispatch(kickMemberSuccess(response.data.item));
    } catch (error) {
        dispatch(kickMemberFailure(error.response));
    }
};
