import React from "react";
import "./TaskOption.scss";
import { useDispatch } from 'react-redux';
import { getListTaskToChoose, getRoomInformation } from "../../../redux/thunks/meeting-thunks";
import { taskStatus } from "../../../utils/constant/CommonConstant";
import { socket } from "../../../pages/App/App";

const TaskOption = ({ informationJoinRoom, handleAction }) => {
    const dispatch = useDispatch();

    const handleChooseTask = () => {
        handleAction("vote")
        socket.emit("change-action-event", {
            actionEvent: "vote",
        });
        dispatch(getListTaskToChoose({
            softSkillId: informationJoinRoom?.item?.roomMeeting?.softSkillId,
            statusId: taskStatus[0].id
        }));
        dispatch(getRoomInformation(informationJoinRoom?.item?.roomMeeting?.roomId));
    }

    return (
        <React.Fragment>
            {informationJoinRoom?.item?.roomMeeting?.isRoomMaster === true
                ?
                <button type="button" onClick={() => handleChooseTask()} className="btn btn-info btn-choice">
                    Chọn nhiệm Vụ
                </button> : ""}
        </React.Fragment>
    );
};

export default TaskOption;