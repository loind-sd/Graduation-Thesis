import React, { useEffect } from "react";
import MenuBar from "../../../../components/MenuBar/MenuBar";
import BookingRoomList from '../../../../components/BookingRoomList/BookingRoomList';
import { useDispatch } from 'react-redux';
import { getUserInfo } from "../../../../redux/thunks/auth-thunks";
import Feedback from "../../../../components/Feedback/Feedback";

const BookingRoom = () => {
    const dispatch = useDispatch();
    // useEffect(() => {
    //     if (localStorage.getItem('token') != null) {
    //         dispatch(getUserInfo());
    //     }
    // }, [dispatch]);

    return (
        <React.Fragment>
            <MenuBar activeMenu='booking-room' />
            <BookingRoomList />
            {localStorage.getItem('token') != null ? <Feedback /> : null}
        </React.Fragment>
    );
};

export default BookingRoom;
