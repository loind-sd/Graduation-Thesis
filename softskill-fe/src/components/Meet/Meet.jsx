import { JitsiMeeting } from '@jitsi/react-sdk';
import './Meet.scss';
import React, { useEffect, useRef, useState } from 'react';
import * as Unicon from '@iconscout/react-unicons';
import { useHistory, useLocation } from 'react-router';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { getFriends } from '../../redux/thunks/friend-thunks';
import { socket } from './../../pages/App/App';
import {
    acceptRoomTask,
    cancelTask,
    completeTask,
    getInformationJoinRoom,
    getListTaskToChoose,
    getMember,
    getRoomInformation,
    getTaskChooseById,
    outMeeting,
    resetUserRoomTaskInfo,
    updateLockRoom,
} from "../../redux/thunks/meeting-thunks";
import TaskForVoting from "./TaskForVoting/TaskForVoting";
import TaskOption from "./TaskOption/TaskOption";
import InviteFriend from "./InviteFriend/InviteFriend";
import { ConfirmDialog, Toast } from "primereact";
import { Button } from "react-bootstrap";
import { getUserInfo } from "../../redux/thunks/auth-thunks";
import { taskStatus } from "../../utils/constant/CommonConstant";

const Meet = () => {
    const users = useSelector((state) => state.authData.user);
    const [name, setName] = useState(users?.userDetails?.name);
    const dispatch = useDispatch();
    const location = useLocation();
    // none - vote - doing
    const [actionEvent, setActionEvent] = useState('none');
    // useState
    const [modalShow, setModalShow] = useState(false);
    const [active, setActive] = useState(true);
    const [lock, setLock] = useState(false);
    const [friends, setFriends] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [minutes, setMinutes] = useState(0);
    const [seconds, setSeconds] = useState(0);
    const [voteStatus, setVoteStatus] = useState('doing');
    const [isOldMember, setIsOldMember] = useState('none');
    // useSelector
    const user = useSelector((state) => state.authData.user);
    const frSelector = useSelector((state) => state.friends.friends);
    const informationRoomJoin = useSelector((state) => state.meetingData.information);
    const roomInfo = useSelector((state) => state.meetingData.room);
    const tasksToChoose = useSelector((state) => state.meetingData.tasks);
    const currentTask = useSelector((state) => state.meetingData?.currentTask);
    const members = useSelector((state) => state.meetingData.members);

    // data location
    const room = location.state?.singleRoom;
    const [visible, setVisible] = useState(false);
    const toast = useRef(null);
    const [userRequired, setUserRequired] = useState(null);
    const [dataAcceptRoomTask, setDataAcceptRoomTask] = useState({});
    const [taskId, setTaskId] = useState(null);
    const [isCancelComplete, setIsCancelComplete] = useState(false);
    const [completePercent, setCompletePercent] = useState(0);
    const [cancelPercent, setCancelPercent] = useState(0);
    // useEffect
    useEffect(() => {
        if (localStorage.getItem('token')) {
            dispatch(getUserInfo());
        }
        dispatch(getFriends());
        dispatch(getInformationJoinRoom(room.roomId));
        dispatch(getRoomInformation(room.roomId));
        if (room.taskId != null) {
            dispatch(getMember({ roomId: room.roomId, tasksId: parseInt(room.taskId) }));
        }
    }, [room.roomId]);

    useEffect(() => {
        let taskCurrent = informationRoomJoin?.meetingData?.taskDetails?.taskLatestInRoomMapping?.taskId;
        setTaskId(taskCurrent !== undefined ? taskCurrent : room.taskId);
        for (let i = 0; i < informationRoomJoin?.item?.roomMeeting?.usersRoom?.length; i++) {
            if (user?.userDetails?.id === informationRoomJoin?.item?.roomMeeting?.usersRoom[i]?.userId) {
                setIsOldMember(informationRoomJoin?.item?.roomMeeting?.usersRoom[i]?.isOldMember);
                break;
            }
        }
        if (informationRoomJoin?.item?.roomMeeting?.isRoomMaster) {
            setIsOldMember(true);
        }
        if (informationRoomJoin?.item?.roomMeeting?.taskDetails === null) {
            setIsOldMember(true);
        }
    }, [informationRoomJoin]);
    useEffect(() => {
        let complete = 0,
            cancel = 0;
        members?.usersRoomTask?.forEach((member) => {
            if (
                member.userId === user.userDetails.id &&
                (member.isClickCompleted === true || member.isClickCancel === true)
            ) {
                setIsCancelComplete(true);
            }
            if (member.isClickCompleted === true) {
                complete++;
            }
            if (member.isClickCancel === true) {
                cancel++;
            }
        });
        setCancelPercent((cancel / members?.usersRoomTask?.length) * 100);
        setCompletePercent((complete / members?.usersRoomTask?.length) * 100);
    }, [members]);
    useEffect(() => {
        if (
            (actionEvent === 'none' || actionEvent === 'vote') &&
            room?.roomCode !== undefined &&
            informationRoomJoin?.item?.roomMeeting?.isRoomMaster !== undefined
        ) {
            socket.emit('join-room-meeting', {
                room: room?.roomCode,
                isMaster: informationRoomJoin?.item?.roomMeeting?.isRoomMaster,
                members: members.usersRoomTask,
            });
        }
        if (informationRoomJoin?.item?.roomMeeting?.taskDetails?.taskLatestInRoomMapping?.taskId !== undefined) {
            socket.emit('update-task-choose', {
                taskChooseId: informationRoomJoin?.item?.roomMeeting?.taskDetails?.taskLatestInRoomMapping?.taskId
            });
        }
    }, [informationRoomJoin]);

    useEffect(() => {
        setFriends(frSelector);
    }, [frSelector]);

    useEffect(() => {
        setLock(roomInfo?.item?.roomDetail?.isLock);
    }, [roomInfo]);

    useEffect(() => {
        if ((active === true && actionEvent !== "none") || (informationRoomJoin?.item?.roomMeeting?.taskDetails !== undefined && informationRoomJoin?.item?.roomMeeting?.taskDetails !== null)) {
            document.getElementById("sidebar").style.display = "block";
            document.getElementById("content").style.width = "100%";
            document.getElementById("content").style.marginLeft = "350px";
            document.getElementById("sidebar").style.width = "350px";
            document.getElementById("sidebar").style.opacity = "1";
            if (isOldMember == true) {
                document.getElementById("end-call").style.right = "calc((100% - 850px) / 2)";
            }
            document.getElementById('invite-friend').style.right = 'calc((100% - 635px) / 2)';
            document.getElementById('lock-room').style.right = 'calc((100% - 715px) / 2)';
        }
        else {
            document.getElementById('content').style.width = '100%';
            document.getElementById('content').style.marginLeft = '0';
            document.getElementById('sidebar').style.opacity = '0';
            document.getElementById('sidebar').style.width = '0px';
            if (informationRoomJoin?.item?.roomMeeting?.taskDetails == null || isOldMember == true) {
                document.getElementById("end-call").style.right = "calc((100% - 510px) / 2)";
            }
            document.getElementById('invite-friend').style.right = 'calc((100% - 280px) / 2)';
            document.getElementById('lock-room').style.right = 'calc((100% - 368px) / 2)';
        }
        if (active == false) {
            document.getElementById('content').style.width = '100%';
            document.getElementById('content').style.marginLeft = '0';
            document.getElementById('sidebar').style.opacity = '0';
            document.getElementById('sidebar').style.width = '0px';
            if (informationRoomJoin?.item?.roomMeeting?.taskDetails == null || isOldMember == true) {
                document.getElementById("end-call").style.right = "calc((100% - 510px) / 2)";
            }
            document.getElementById('invite-friend').style.right = 'calc((100% - 280px) / 2)';
            document.getElementById('lock-room').style.right = 'calc((100% - 368px) / 2)';
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [lock, actionEvent, active, informationRoomJoin?.item?.roomMeeting?.isRoomMaster]);

    //  master
    useEffect(() => {
        if (
            actionEvent === 'vote' &&
            informationRoomJoin?.item?.roomMeeting?.isRoomMaster === true &&
            tasksToChoose?.item !== undefined
        ) {
            let tasks = tasksToChoose?.item.map((item) => ({
                ...item,
                vote: 0,
            }));
            setTasks(tasks);
            socket.emit('action-event-room', {
                roomCode: room?.roomCode,
                actionEvent: 'vote',
                tasksToChoose: tasks,
            });
        }
    }, [actionEvent, tasksToChoose]);

    useEffect(() => {
        socket.on('mission-rq-receive', (data) => {
            setName(data.name);
            setVisible(true);
            setUserRequired(data.socketId);
            setDataAcceptRoomTask({
                taskId: data.taskId,
                roomId: data.roomId,
                softSkillId: data.softSkillId,
                userId: data.userId,
            });
        });
    }, [socket]);

    useEffect(() => {
        async function result() {
            socket.on('receive-rq-result', (data) => {
                if (data.result == 'confirm') {
                    document.getElementById('noti-accept').click();
                } else {
                    document.getElementById('noti-reject').click();
                }
            });
        }
        result();
    }, [socket]);
    // socket on ng dang trong phong
    useEffect(() => {
        if (socket) {
            socket.on('action-event-room-change', (eventAction) => {
                setActionEvent(eventAction?.actionEvent === undefined ? 'none' : eventAction?.actionEvent);
                setTasks(eventAction?.tasksToChoose);
            });

            socket.on('counter', (eventAction) => {
                setVoteStatus(eventAction.voteStatus);
                setMinutes(Math.floor(eventAction.counterTime / 60));
                setSeconds(eventAction.counterTime % 60);
            });

            socket.on('join-after', (data) => {
                setTasks(data.task);
                setActionEvent(data?.actionVote === undefined ? "none" : data?.actionVote);
            });

            socket.on('update-action-event', (data) => {
                setActionEvent(data.actionEvent);

                if (data?.taskIdChoose !== undefined && data?.taskIdChoose !== null) {
                    dispatch(getTaskChooseById(data?.taskIdChoose));
                    dispatch(getInformationJoinRoom(room.roomId));
                    setTaskId(data?.taskIdChoose)
                    dispatch(getMember({ roomId: room.roomId, tasksId: data?.taskIdChoose }));
                }
            });

            socket.on('update-lock-room', (data) => {
                setLock(data.changeLock);
            });

            socket.on("count-complete-init", (data) => {
                setCompletePercent(data.countComplete);
                setCancelPercent(data.countCancel);
                if (data.countComplete === 100 || data.countCancel === 100) {
                    setCompletePercent(0);
                    setCancelPercent(0);
                    dispatch(getListTaskToChoose({
                        softSkillId: room?.softSkillId,
                        statusId: taskStatus[0].id
                    }));
                    dispatch(resetUserRoomTaskInfo(data.taskIdChoose));

                    dispatch(getRoomInformation(room?.roomId));

                    setTaskId(null)
                    socket.emit("change-action-event", {
                        actionEvent: "vote",
                    });
                    socket.emit("reset-count-complete-init", {
                    });
                }
            });
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [socket]);

    useEffect(() => {
        if (room.taskId != null) {
            dispatch(getTaskChooseById(room.taskId));
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [dispatch]);

    useEffect(() => {
        const handleTabClose = async () => {
            await dispatch(
                outMeeting(
                    room.roomId,
                    room.taskId == null || room.taskId == undefined
                        ? informationRoomJoin?.meetingData?.taskDetails?.taskLatestInRoomMapping?.taskId
                        : room.taskId,
                ),
            );
            return null;
        };
        window.addEventListener('beforeunload', handleTabClose, false);
    }, []);
    useEffect(() => {
        const handleBack = async () => {
            await dispatch(
                outMeeting(
                    room.roomId,
                    room.taskId == null || room.taskId == undefined
                        ? informationRoomJoin?.meetingData?.taskDetails?.taskLatestInRoomMapping?.taskId
                        : room.taskId,
                ),
            );
            localStorage.setItem('isBack', true);
        }
        window.addEventListener('popstate', handleBack, false);
    }, []);

    const cancelHandle = async () => {
        let taskId = informationRoomJoin?.item?.roomMeeting?.taskDetails?.taskLatestInRoomMapping?.taskId;
        await dispatch(cancelTask({
            tasksId: parseInt(taskId !== undefined ? taskId : room.taskId),
            userId: users?.userDetails?.id,
            roomId: room.roomId, softSkillId: room.softSkillId
        }));
        await dispatch(getMember({ roomId: room.roomId, tasksId: parseInt(taskId !== undefined ? taskId : room.taskId) }));
        await socket.emit('members-in-room', members.usersRoomTask);
    };

    const completeHandle = async () => {
        let taskId = informationRoomJoin?.item?.roomMeeting?.taskDetails?.taskLatestInRoomMapping?.taskId;
        await dispatch(completeTask({
            tasksId: parseInt(taskId !== undefined ? taskId : room.taskId),
            userId: users?.userDetails?.id,
            roomId: room.roomId,
            softSkillId: room.softSkillId
        }));
        await dispatch(getMember({ roomId: room.roomId, tasksId: parseInt(taskId !== undefined ? taskId : room.taskId) }));
    };

    useEffect(() => {
        socket.emit('members-in-room', members.usersRoomTask);
    }, [members])

    const guidelineTask = (...guideline) => {
        let guidelines = [];
        for (let i = 0; i < guideline[0]?.length; i++) {
            guidelines.push(
                <div key={i} className="px-5">
                    <span>
                        Bước {i + 1}: {guideline[0][i].guidelineTitle}
                    </span>
                    <br />
                    <p className="pl-0">{guideline[0][i].guidelineContent}</p>
                </div>,
            );
        }
        return guidelines;
    };

    const clickRq = () => {
        socket.emit('sent-rq-mission', {
            roomId: room.roomId,
            taskId: room.taskId,
            softSkillId: room.softSkillId,
            userId: user?.userDetails?.id,
            roomAdmin: room.createById,
            name: user?.userDetails?.name,
        });
        return () => {
            socket.removeListener('sent-rq-mission');
        };
    };

    const accept = () => {
        toast.current.show({ severity: 'info', summary: 'Xác nhận', detail: 'Đồng ý tham gia', life: 3000 });
        // dispatch(acceptRoomTask(dataAcceptRoomTask));
        socket
            .emit('result-sent-rq', {
                result: 'confirm',
                socketId: userRequired,
            })
            .off('result-sent-rq');
    };
    const reject = () => {
        toast.current.show({ severity: 'warn', summary: 'Từ chối', detail: 'Từ chối tham gia', life: 3000 });
        socket
            .emit('result-sent-rq', {
                result: 'reject',
                socketId: userRequired,
            })
            .off('result-sent-rq');
    };

    const showAccept = async () => {
        toast.current.show({ severity: 'info', summary: 'Xác nhận', detail: 'Bạn đã được tham gia', life: 3000 });
        let taskChooseId = informationRoomJoin?.item?.roomMeeting?.taskDetails?.taskLatestInRoomMapping?.taskId;
        await dispatch(acceptRoomTask({ roomId: room.roomId, tasksId: parseInt(taskChooseId !== undefined ? taskChooseId : room.taskId), userId: user?.userDetails?.id, softSkillId: room.softSkillId }));
        await dispatch(getInformationJoinRoom(room.roomId));
        // setInterval(() => {
        //     window.location.href = 'http://localhost:3000/meet';
        // }, 3000);
        setIsOldMember(true);

    };
    const showReject = () => {
        toast.current.show({ severity: 'warn', summary: 'Từ chối', detail: 'Bạn bị từ chối tham gia', life: 3000 });
    };

    // socket on ng vua trong phong

    // handle action

    const handleLockRoom = () => {
        dispatch(updateLockRoom(room.roomId));
        socket.emit('change-lock-room', {
            changeLock: !lock,
        });
        socket.emit('change-lock-room-in-active-room', {
            changeLock: !lock,
            roomId: room.roomId,
        });

        setLock(!lock);
    };

    const handleActionEvent = (action) => {
        setActionEvent(action);
    };
    const endCallHandle = async () => {
        // window.location.replace('http://localhost:3000');
        await dispatch(
            outMeeting(
                room.roomId,
                room.taskId == null || room.taskId == undefined
                    ? informationRoomJoin?.meetingData?.taskDetails?.taskLatestInRoomMapping?.taskId
                    : room.taskId,
            ),
        );
        window.location.replace('http://localhost:3000');
    };
    // layout
    const layoutLockRoom =
        <button
            type="button"
            id="invite-friend"
            onClick={() => handleLockRoom()}
            className={"btn btn-info btn-lock position-absolute " + (informationRoomJoin?.item?.roomMeeting?.isRoomMaster === true ? '' : 'display-none')}
        >
            {lock === false ? <Unicon.UilUnlockAlt /> : <Unicon.UilLockAlt />}
        </button>;
    return (
        <div className="wrapper-meeting">
            <Toast ref={toast} />
            <nav id="sidebar">
                <div className="sidebar-header">
                    <h3>
                        Nhiệm vụ cho{' '}
                        {roomInfo?.item?.roomDetail?.softSkillName === undefined
                            ? 'Kỹ năng chung'
                            : roomInfo?.item?.roomDetail?.softSkillName}
                    </h3>
                </div>

                {actionEvent === 'vote' ? (
                    <TaskForVoting
                        isMaster={informationRoomJoin?.item?.roomMeeting?.isRoomMaster}
                        voteStatus={voteStatus}
                        tasks={tasks}
                        room={room}
                        time={
                            minutes === 0 && seconds === 0 ? (
                                0
                            ) : (
                                <h2>
                                    {' '}
                                    {minutes}:{seconds < 10 ? `0${seconds}` : seconds}
                                </h2>
                            )
                        }
                    />
                ) : actionEvent === 'doing' || room.taskId != null ? (
                    <ul className="list-unstyled components mb-5 task-newbie task-doing">
                        <li>
                            <div className="">
                                <div className="mr-5 ml-5 mt-3 pt-3 font-weight-bold">
                                    Nhiệm vụ: {currentTask?.taskDetailsInRoomMapping?.taskName}
                                </div>
                                <p className="ml-5 mr-5 mt-3 font-italic task-content">{currentTask?.taskDetailsInRoomMapping?.taskContent}</p>
                                <img
                                    className="image-mission"
                                    src={
                                        currentTask?.taskDetailsInRoomMapping?.imageTask == null
                                            ? '/image/our-mission.png'
                                            : `data:image/jpeg;base64,${currentTask?.taskDetailsInRoomMapping?.imageTask}`
                                    }
                                    alt=""
                                />
                                <div className='guiline-list pt-5'>
                                    <div className='mx-5 font-weight-bold pb-4'>
                                        Hướng dẫn làm nhiệm vụ
                                    </div>
                                    {currentTask?.guidelineTasks?.map((value, index) => {
                                        return <p className="ml-5 mr-5 font-italic task-step">
                                            {value.guidelineTitle}: {value.guidelineContent}
                                        </p>;
                                    })}
                                </div>
                                {isOldMember == true ? (
                                    <div>
                                        <div className="d-flex justify-content-center complete-cancel px-3 mx-1">
                                            <div className="text-danger mr-5 font-weight-bold">
                                                Đã huỷ{' '}
                                                {cancelPercent?.toLocaleString(navigator.language, {
                                                    maximumFractionDigits: 0,
                                                })}{' '}
                                                %
                                            </div>
                                            <div className="text-success ml-5 font-weight-bold">
                                                Đã hoàn thành{' '}
                                                {completePercent?.toLocaleString(navigator.language, {
                                                    maximumFractionDigits: 0,
                                                })}{' '}
                                                %
                                            </div>
                                        </div>
                                        <div className="d-flex justify-content-center button-mission">
                                            {/* <Button className='btn btn-lg' >Gửi yêu cầu làm nhiệm vụ</Button> */}
                                            <Button
                                                className="border-0 btn-lg text-danger mr-5 bg-white font-weight-bold mr-5 btn-cancel"
                                                onClick={cancelHandle}
                                            >
                                                Huỷ bỏ
                                            </Button>
                                            <Button
                                                className="border-0 btn-lg text-white bg-success font-weight-bold btn-complete"
                                                onClick={completeHandle}
                                            >
                                                Hoàn thành
                                            </Button>
                                        </div>
                                    </div>
                                ) : (
                                    <div className="button-require">
                                        <button
                                            type="button"
                                            id="rq"
                                            onClick={() => clickRq()}
                                            className="btn btn-info ml-5 send-request position-absolute"
                                        >
                                            Gửi yêu cầu tham gia nhiệm vụ
                                        </button>
                                    </div>
                                )}
                                {currentTask?.taskDetailsInRoomMapping?.subTaskName !== null &&
                                    currentTask?.taskDetailsInRoomMapping?.subTaskName !== undefined ? (
                                    <div className="text-danger ml-5 mr-5 remind">
                                        Nhắc nhở: Bạn cần hoàn thành nhiệm vụ cá nhân{' '}
                                        {currentTask?.taskDetailsInRoomMapping?.subTaskName} để làm nhiệm vụ nhóm này.
                                    </div>
                                ) : null}
                            </div>
                        </li >
                    </ul >
                ) : null}
            </nav >

            <div id="content">
                <ConfirmDialog />
                {actionEvent !== 'none' || room.taskId != null ? (
                    <button
                        type="button"
                        id="sidebarCollapse"
                        onClick={() => setActive(!active)}
                        className="btn btn-info position-absolute"
                    >
                        {/* <UilBars /> */} Nhiệm Vụ{' '}
                        {active === true ? <Unicon.UilAngleLeft /> : <Unicon.UilAngleRight />}
                    </button>
                ) : (
                    <TaskOption informationJoinRoom={informationRoomJoin} handleAction={handleActionEvent} />
                )}
                {layoutLockRoom}
                <button
                    type="button"
                    id="lock-room"
                    onClick={() => setModalShow(true)}
                    className={"btn btn-info btn-invite-friend position-absolute " + (lock === false ? '' : 'display-none')}
                >
                    {<Unicon.UilUserPlus />}
                </button>

                <InviteFriend
                    show={modalShow}
                    onHide={() => setModalShow(false)}
                    friends={friends}
                    lock={lock}
                    user={user}
                    room={room}
                />
                {isOldMember ? (
                    <div className="end-call-container position-absolute" id='end-call'>
                        <Button className="btn-end-call" onClick={() => endCallHandle()}>
                            <Unicon.UilPhoneTimes />
                        </Button>
                    </div>
                ) : (
                    <div className="end-call-container-newbie position-absolute">
                        <Button className="btn-end-call" onClick={() => endCallHandle()}>
                            <Unicon.UilPhoneTimes />
                        </Button>
                    </div>
                )}

                {isOldMember != 'none' ? (
                    <JitsiMeeting key={isOldMember}
                        roomName={room.roomName}
                        jwt={''}
                        userInfo={{ displayName: user?.userDetails?.name }}
                        // jwt={token}
                        interfaceConfigOverwrite={{
                            VIDEO_QUALITY_LABEL_DISABLED: true,
                            DEFAULT_LOGO_URL: '',
                            SHOW_PROMOTIONAL_CLOSE_PAGE: true,
                        }}
                        configOverwrite={{
                            remoteVideoMenu: {
                                // Whether the remote video context menu to be rendered or not.
                                disabled: true,
                                // If set to true the 'Kick out' button will be disabled.
                                disableKick: true,
                                // If set to true the 'Grant moderator' button will be disabled.
                                disableGrantModerator: true,
                                // If set to true the 'Send private message' button will be disabled.
                                disablePrivateChat: true,
                            },
                            logging: {
                                defaultLogLevel: 'debug',
                                disableLogCollector: true,
                            },
                            toolbarConfig: { alwaysVisible: true },
                            startWithAudioMuted: true,
                            startWithVideoMuted: true,
                            prejoinPageEnabled: false,
                            enableClosePage: false,
                            toolbarButtons:
                                isOldMember == true
                                    ? [
                                        'camera',
                                        'microphone',
                                        'chat',
                                        'desktop',
                                        'raisehand',
                                        'recording',
                                        'select-background',
                                    ]
                                    : [],
                            videoquality: false,
                        }}
                        getIFrameRef={(node) => (node.style.height = '100%', node.style.overflow = 'hidden')}
                    />
                ) : null}
            </div>

            <ConfirmDialog
                visible={visible}
                onHide={() => setVisible(false)}
                message={name + ' yêu cầu được làm nhiệm vụ cùng!'}
                header="Xác nhận"
                icon="pi pi-exclamation-triangle"
                style={{ fontFamily: 'Helvetica', fontSize: '1.2rem', paddingTop: '1.5rem' }}
                acceptLabel="Xác nhận"
                rejectLabel="Từ chối"
                accept={accept}
                reject={reject}
            />
            <Button label="Success" id="noti-accept" className="p-button-success d-none" onClick={() => showAccept()} />
            <Button label="Info" id="noti-reject" className="p-button-info d-none" onClick={() => showReject()} />
        </div >
    );
};

export default Meet;
