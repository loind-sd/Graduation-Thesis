import { GET_MEMBERS_FAILURE, GET_MEMBERS_SUCCESS } from '../action-types/meeting-action-types';
import {
    DELETE_CHAT_FAILURE,
    DELETE_CHAT_SUCCESS,
    GET_CHAT_FAILURE,
    GET_CHAT_SUCCESS,
    SEND_CHAT_FAILURE,
    SEND_CHAT_SUCCESS,
    GET_CONTACT_SUCCESS,
    ADD_CONTACT_SUCCESS,
    UPDATE_CHAT_SUCCESS,
    UPDATE_CHAT_FAILURE,
    GET_FRIEND_SUCCESS,
    GET_FRIEND_FAILURE,
    OUT_ROOM_SUCCESS,
    OUT_ROOM_FAILURE,
    ADD_MEMBER_SUCCESS,
    ADD_MEMBER_FAILURE,
    KICK_MEMBER_SUCCESS,
    KICK_MEMBER_FAILURE,
} from './../action-types/chat-action-type';

export const getChatSuccess = (chats) => ({
    type: GET_CHAT_SUCCESS,
    payload: chats.data,
    msgType: chats.loadType,
});

export const getChatFailure = (error) => ({
    type: GET_CHAT_FAILURE,
    payload: error,
});

export const sendChatSuccess = (chats) => ({
    type: SEND_CHAT_SUCCESS,
    payload: chats,
});

export const sendChatFailure = (error) => ({
    type: SEND_CHAT_FAILURE,
    payload: error,
});

export const deleteChatSuccess = (chats) => ({
    type: DELETE_CHAT_SUCCESS,
    payload: chats,
});

export const deleteChatFailure = (error) => ({
    type: DELETE_CHAT_FAILURE,
    payload: error,
});

export const getContactSuccess = (contacts) => ({
    type: GET_CONTACT_SUCCESS,
    payload: contacts,
});

export const addContactSuccess = (contacts) => ({
    type: ADD_CONTACT_SUCCESS,
    payload: contacts,
});

export const updateChatSuccess = (chats) => ({
    type: UPDATE_CHAT_SUCCESS,
    payload: chats,
});

export const updateChatFailure = (error) => ({
    type: UPDATE_CHAT_FAILURE,
    payload: error,
});

export const outRoomSuccess = (contacts) => ({
    type: OUT_ROOM_SUCCESS,
    payload: contacts,
});

export const outRoomFailure = (error) => ({
    type: OUT_ROOM_FAILURE,
    payload: error,
});

export const getFriendSuccess = (frined) => ({
    type: GET_FRIEND_SUCCESS,
    payload: frined,
});

export const getFriendFailure = (error) => ({
    type: GET_FRIEND_FAILURE,
    payload: error,
});

export const addMemberSuccess = (member) => ({
    type: ADD_MEMBER_SUCCESS,
    payload: member,
});

export const addMemberFailure = (error) => ({
    type: ADD_MEMBER_FAILURE,
    payload: error,
});

export const getMemberSuccess = (member) => ({
    type: GET_MEMBERS_SUCCESS,
    payload: member,
});

export const getMemberFailure = (error) => ({
    type: GET_MEMBERS_FAILURE,
    payload: error,
});

export const kickMemberSuccess = (member) => ({
    type: KICK_MEMBER_SUCCESS,
    payload: member,
});

export const kickMemberFailure = (error) => ({
    type: KICK_MEMBER_FAILURE,
    payload: error,
});
