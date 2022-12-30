import React from 'react'
import ChatBar from '../ChatBar/ChatBar';
import Header from '../Header/Header';
import BookingRoom from '../Room/BookingRoom';

const BookingRoomList = () => {
    return (
        <div className="home d-flex">
            <Header action={"home"} />
            <div className="content row">
                <div className="body-content col-9 ">
                    <div className="row banner-container p-2">
                        <div className="banner"></div>
                    </div>
                    <BookingRoom
                        softSkillId={null}
                        textSearch={""}
                        roomSizeFrom={null}
                        roomSizeTo={null}
                        toTime={null}
                        fromTime={null}
                    />
                </div>
                <div className="col-3"> {localStorage.getItem("token") ? <ChatBar /> : ""}</div>
            </div>
        </div>
    );
}

export default BookingRoomList