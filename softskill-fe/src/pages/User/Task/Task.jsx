import "./Task.scss"
import React from "react";
import TasksForPersonal from "./TasksForPersonal/TasksForPersonal";
import TasksForYou from "./TasksForYou/TasksForYou";
import TasksForGroup from "./TasksForGroup/TasksForGroup";
import TasksProposed from "./TasksProposed/TasksProposed";
import MenuBar from "../../../components/MenuBar/MenuBar";
import Header from "../../../components/Header/Header";
import ChatBar from "../../../components/ChatBar/ChatBar";
import Feedback from "../../../components/Feedback/Feedback";

const Task = ({ taskName }) => {
    return (
        <React.Fragment>
            <MenuBar />
            <div className="task">
                <Header action={taskName} />
                <div className="content row p-0">
                    <div className="col-9  p-0 body-content">
                        {taskName === "tasksProposed"
                            ? <TasksProposed />
                            : taskName === "tasksForGroup"
                                ? <TasksForGroup />
                                : taskName === "tasksForPersonal"
                                    ? <TasksForPersonal />
                                    : taskName === "tasksForYou"
                                        ? <TasksForYou />
                                        : ""}
                    </div>
                    <div className="col-3 p-0 friend-bar"><ChatBar /></div>
                </div>
            </div>
            <Feedback />
        </React.Fragment>
    );
};

export default Task;
