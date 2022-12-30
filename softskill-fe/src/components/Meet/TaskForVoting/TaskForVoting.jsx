import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import './TaskForVoting.scss';
import { socket } from './../../../pages/App/App';
import { useDispatch } from 'react-redux';
import { addTaskChooseForUser } from '../../../redux/thunks/meeting-thunks';

const TaskForVoting = ({ tasks, room, time, voteStatus }) => {
  const dispatch = useDispatch();

  const [tasksMap, setTasksMap] = useState([]);
  const [selectTask, setSelectTask] = useState(0);
  const [disabled, setDisabled] = useState(false);
  const [total, setTotal] = useState(0);
  const [actionSubmit, setActionSubmit] = useState(false);
  const [arrTaskCheck, setArrTaskCheck] = useState([]);
  const [seconds, setSeconds] = useState(5);
  useEffect(() => {
    if (voteStatus === "done" && arrTaskCheck.length > 1) {
      let myInterval = setInterval(() => {
        if (voteStatus === "done") {
          setSeconds(seconds - 1);
        } else {
          clearInterval(myInterval)
          setSeconds(5)
        }
      }, 1000);
      return () => {
        clearInterval(myInterval);
      };
    }
  }, [arrTaskCheck.length, seconds, voteStatus]);

  useEffect(() => {
    setTasksMap(tasks);
  }, [tasks]);

  useEffect(() => {
    if (socket) {
      socket.on('update-vote', (eventAction) => {
        let count = 0;
        eventAction.tasksUpdate.forEach((e) => {
          count += e.vote;
        });
        setTotal(count);
        setTasksMap(eventAction.tasksUpdate);
      });
    }
  }, [socket]);

  useEffect(() => {
    socket.emit('update-total-user', {
      total: total,
    });
  }, [total]);

  useEffect(() => {
    if (voteStatus === 'done' && tasksMap.length !== 0) {
      let voteMax = tasksMap.reduce((max, value) => (max.vote > value.vote ? max : value)).vote;
      let arr = [];
      tasksMap.map((e) => (e.vote === voteMax ? arr.push(e) : null));
      setArrTaskCheck(arr);
    }
  }, [voteStatus]);

  useEffect(() => {
    if (voteStatus === 'done' && arrTaskCheck.length > 1) {
      setTimeout(function () {
        setArrTaskCheck([]);
        let tasks = tasksMap?.map((item) => ({
          ...item,
          vote: 0,
        }));
        socket.emit('action-event-room', {
          roomCode: room?.roomCode,
          actionEvent: 'vote',
          tasksToChoose: tasks,
        });
        setDisabled(false);
        setActionSubmit(false);
        setSeconds(5)
      }, 5000);
    } else if (voteStatus === "done" && arrTaskCheck.length === 1) {
      setDisabled(true);
      socket.emit("update-total-user", {
        total: total,
      });
    }
  }, [voteStatus, arrTaskCheck])

  //Handle
  const handleSubmit = () => {
    if (actionSubmit || selectTask === 0) {
      return;
    }
    setDisabled(true);
    setActionSubmit(true);
    tasksMap.find((e) => e.taskId === selectTask).vote += 1;
    let count = 0;
    tasksMap.forEach((e) => {
      count += e.vote;
    });
    socket.emit("vote-task", {
      tasksUpdate: tasksMap,
    });
    setTotal(count);
  };

  const handleAddTask = async () => {
    let dataRq = {
      tasksId: arrTaskCheck[0].taskId,
      roomId: room?.roomId,
      softSkillId: room?.softSkillId
    }
    await dispatch(addTaskChooseForUser(dataRq))
    await socket.emit("change-action-event", {
      taskIdChoose: arrTaskCheck[0].taskId,
      actionEvent: "doing",
    });
  }

  return (
    <React.Fragment>
      <ul className="list-unstyled task-voting">
        {tasksMap?.map((item, index) => (
          <li key={index} className="item-task">
            <div className="wrap-select">
              <input
                type={"radio"}
                name="misson"
                disabled={disabled}
                onClick={() => setSelectTask(item?.taskId)}
                className="task-vote"
              />
              {item?.taskName}
              <span style={{ color: "red", marginLeft: "10px" }}>
                {(item?.vote * 100) / (total === 0 ? 1 : total) === 0
                  ? null
                  : (item?.vote * 100) / (total === 0 ? 1 : total) + "%"}
              </span>
            </div>
            <p className="task-content">{item?.taskContent}</p>
            <div className="d-flex justify-content-center">
              <img
                className="task-image"
                src={
                  item?.imageData === undefined || item?.imageData === null
                    ? require('../../../assets/image/softskill.jpg')
                    : `data:image/jpeg;base64,${item?.imageData}`
                }
                alt="ảnh nhiệm vụ"
                about="Ảnh nhiệm vụ"
              />
            </div>
          </li>
        ))}
      </ul>
      <div className="footer-container">
        <div className="footer-content ">
          {voteStatus === "doing" && actionSubmit === false ? (
            <div className="d-flex justify-content-center">
              <Button
                className="btn-lg btn-choose"
                onClick={() => handleSubmit()}>
                Xác nhận
              </Button>
              <div className="time-count rounded-circle">{time}</div>
            </div>
          ) : voteStatus === "done" && arrTaskCheck.length > 1 ? (
            <React.Fragment>
              <div className="time-count rounded-circle" style={{ padding: "13px 17px 7px" }}><h3>{seconds}</h3></div>
              <div style={{ marginLeft: "7px" }}>Hãy tìm ra nhiệm vụ có lượt bình chọn cao nhất!</div>
            </React.Fragment>
          ) : voteStatus === "doing" && arrTaskCheck.length === 0 ? (
            <React.Fragment>
              <div className="time-count rounded-circle">{time}</div>
              <div>Chờ mọi người trong phòng chọn</div>
            </React.Fragment>
          ) : voteStatus === "done" && arrTaskCheck.length === 1 ? (
            <React.Fragment>
              <Button
                className="btn-lg btn-choose"
                style={{ padding: "8px 10px" }}
                onClick={() => handleAddTask()}>
                Tiếp tục
              </Button>
            </React.Fragment>
          ) : null}
        </div>

      </div>
    </React.Fragment>
  );
};

export default TaskForVoting;
