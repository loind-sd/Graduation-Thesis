import React, { useEffect } from 'react';
import { Route, Switch } from 'react-router-dom';
import AccountManager from '../admin/AccountManager/AccountManager';
import SoftSkillManager from '../admin/SoftSkillManager/SoftSkillManager';
import DashboardManager from '../admin/DashBoard/Dashboard';
import { BrowserRouter } from 'react-router-dom/cjs/react-router-dom.min';
import GroupTaskManager from '../author/GroupTaskManager/GroupTaskManager';
import IndividualTaskManager from '../author/IndividualTask/IndividualTask';
import RecommendTaskManager from '../author/RecommendTask/RecommendTask';
import ChatPage from '../ChatPage/ChatPage';
import RoomActive from '../User/Room/RoomActive/RoomActive';
import Login from './../Auth/Login/Login';
import OAuth2RedirectHandler from './../Auth/oauth2/OAuth2RedirectHandler';
import Dashboard from '../User/Dashboard/Dashboard';
import Task from '../User/Task/Task';
import MeetingRoom from '../User/MeetingRoom/MeetingRoom';
import BookingRoom from '../User/Room/RoomBooking/BookingRoom';
import RoomManagement from '../admin/RoomManager/RoomManagement';
import FeedbackManagement from '../admin/FeedbackManager/FeedbackManagement';
import { io } from 'socket.io-client';
import PrivateRoute from './PrivateRoute';

export const socket = io('http://localhost:5000');

const App = () => {

    useEffect(() => {
        window.addEventListener('popstate', async function (event) {
            if (this.localStorage.getItem('token') == null || this.localStorage.getItem('token') == undefined) {
                this.window.location.href = 'http://localhost:3000';
            }
        });
    }, []);

    return (
        <div className="App">
            <BrowserRouter>
                <Switch>
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/active-room" component={RoomActive} />
                    <PrivateRoute exact path="/booking-room" component={BookingRoom} />
                    <PrivateRoute path="/tasksForGroup" component={() => <Task taskName={"tasksForGroup"} />} />
                    <PrivateRoute path="/tasksForYou" component={() => <Task taskName={"tasksForYou"} />} />
                    <PrivateRoute path="/tasksProposed" component={() => <Task taskName={"tasksProposed"} />} />
                    <PrivateRoute
                        exact
                        path="/tasksForPersonal"
                        component={() => <Task taskName={"tasksForPersonal"} />}
                    />
                    <Route path="/oauth2/redirect" component={OAuth2RedirectHandler} />
                    <PrivateRoute path="/chat" component={ChatPage} />
                    <PrivateRoute path="/dashboard" component={Dashboard} />
                    <PrivateRoute path="/meet" component={MeetingRoom} />

                    {/* admin */}
                    <PrivateRoute path="/admin-manage/acc" component={AccountManager} />
                    <PrivateRoute path="/admin-manage/softskills" component={SoftSkillManager} />
                    <PrivateRoute path="/admin-manage/dashboard" component={DashboardManager} />
                    <PrivateRoute path="/admin-manage/room" component={RoomManagement} />
                    <PrivateRoute path="/admin-manage/feedback" component={FeedbackManagement} />


                    {/* author */}
                    <PrivateRoute path="/author-manage/group-task" component={GroupTaskManager} />
                    <PrivateRoute path="/author-manage/recommend-task" component={RecommendTaskManager} />
                    <PrivateRoute path="/author-manage/individual-task" component={IndividualTaskManager} />

                    <Route path="/*" component={RoomActive} />
                </Switch>
            </BrowserRouter>
        </div>
    );
};

export default App;
