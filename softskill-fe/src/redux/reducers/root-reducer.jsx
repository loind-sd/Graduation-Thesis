import { combineReducers } from 'redux';
import deliveredNotifs from './deliveredNotifs';
import authReducer from './auth-reducer';
import userReducer from './user-reducer';
import notificationReducer from './notification-reducer';
import softSkillReducer from './soft-skill-reducer';
import roomReducer from './room-reducer';
import roomReducerNoti from './room-reducer-noti';
import taskReducer from './task-reducer';
import dropdownReducer from './_dropdown-reducer';
import friendReducer from './friend-reducer';
import chatReducer from './chat-reducer';
import softskillReducer from './../reducers/admin/softskill-reducer';
import accReducer from './../reducers/admin/acc-reducer';
import statisticReducer from "./statistic-reducer";
import rankReducer from "./rank-reducer";
import guidelineReducer from "./guideline-reducer";
import taskForYouReducer from "./task-for-you-reducer";
import taskProposedReducer from "./task-proposed-reducer";
import statisticAdminReducer from './../reducers/admin/statistic-reducer';
import feedbackReducer from "./feedback-reducer";
import bookingRoomReducer from './booking-room-reducer';
import groupTaskReducer from './../reducers/author/group-task-reducer';
import recommendTaskReducer from './../reducers/author/recommend-task-reducer';
import individualTaskReducer from './author/individual-task-reducer';
import meetingReducer from './meeting-reducer';
import adminRoomReducer from './../reducers/admin/room-reducer';
import adminFeedbackReducer from './../reducers/admin/feedback-reducer';

const rootReducer = combineReducers({
    authData: authReducer,
    userData: userReducer,
    deliveredNotificationData: deliveredNotifs,
    notificationData: notificationReducer,
    softSkillData: softSkillReducer,
    roomData: roomReducer,
    roomDataNoti: roomReducerNoti,
    taskData: taskReducer,
    dropdownData: dropdownReducer,
    friends: friendReducer,
    chatsData: chatReducer,
    softskillData: softskillReducer,
    accData: accReducer,
    groupTaskData: groupTaskReducer,
    recommendTaskData: recommendTaskReducer,
    statistic: statisticReducer,
    rank: rankReducer,
    feedback: feedbackReducer,
    guidelineData: guidelineReducer,
    taskForYouData: taskForYouReducer,
    taskProposedData: taskProposedReducer,
    bookingRoomData: bookingRoomReducer,
    individualTaskData: individualTaskReducer,
    adminRoomData: adminRoomReducer,
    adminFeedbackData: adminFeedbackReducer,
    statisticAdmin: statisticAdminReducer,
    meetingData: meetingReducer,
});

export default rootReducer;
