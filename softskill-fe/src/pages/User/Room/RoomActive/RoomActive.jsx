import React, { useEffect } from "react";
import MenuBar from "../../../../components/MenuBar/MenuBar";
import ActiveRoomList from "../../../../components/ActiveRoomList/ActiveRoomList";
import { useDispatch } from 'react-redux';
import { getUserInfo } from "../../../../redux/thunks/auth-thunks";
import Header from "../../../../components/Header/Header";
import Feedback from './../../../../components/Feedback/Feedback';

const RoomActive = () => {
    const dispatch = useDispatch();

    useEffect(() => {
        if (localStorage.getItem('token') != null) {
            dispatch(getUserInfo());
        }
    }, [dispatch]);
    return (
        <React.Fragment>
            <MenuBar activeMenu='active-room' />
            <Header action={"home"} />
            <ActiveRoomList />
            {localStorage.getItem('token') != null ? <Feedback /> : null}
        </React.Fragment>
    );
};

export default RoomActive;
