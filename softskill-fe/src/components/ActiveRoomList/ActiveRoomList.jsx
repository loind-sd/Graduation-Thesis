import React from "react";
import "./ActiveRoomList.scss";
import Header from "../Header/Header";
import ChatBar from "../ChatBar/ChatBar";
import ActiveRoom from "../Room/ActiveRoom";

const HomePageTheme = () => {
  return (
    <div className="home d-flex">
      <div className="content row">
        <div className="body-content col-9 ">
          <div className="row banner-container p-2">
            <div className="banner"></div>
          </div>
          <ActiveRoom
            softSkillId={null}
            textSearch={""}
            roomSizeFrom={null}
            roomSizeTo={null}
            toTime={null}
            fromTime={null}
          />
        </div>
        <div className="col-3 p-0"> {localStorage.getItem("token") ? <ChatBar /> : ""}</div>
      </div>
    </div>
  );
};

export default HomePageTheme;
