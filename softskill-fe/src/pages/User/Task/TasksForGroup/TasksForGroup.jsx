import React, { useEffect, useState } from 'react';
import './TasksForGroup.scss';
import {
    UilCrosshair,
    UilExclamationCircle,
    UilUsersAlt,
    UilHeart,
    UilSearchAlt,
    UilUserPlus,
    UilSignOutAlt,
} from '@iconscout/react-unicons';
import { Dialog, InputText, TabPanel, TabView, Button } from 'primereact';
import { useDispatch, useSelector } from 'react-redux';
import {
    getListTaskDoingGroup,
    getListTaskDoneGroup,
    getListTaskFavoriteGroup,
    getListTaskNotStartGroup,
    updateFavoriteForTask,
} from '../../../../redux/thunks/task-thunks';
import {
    addFriend,
    formReset,
    getFriendsByTask,
    getFriendsByTaskDoingOrDone,
    getFriendsByTaskFavorite,
} from '../../../../redux/thunks/friend-thunks';
import { getGuidelineByTask } from '../../../../redux/thunks/guideline-thunks';
import { colorListSoftSkill } from '../../../../utils/constant/CommonConstant';
import { Link } from 'react-router-dom';

const TasksForGroup = () => {
    const dispatch = useDispatch();
    const colorList = colorListSoftSkill;
    const tasksData = useSelector((state) => state.taskData.tasksGroupNotStart);
    const tasksDataDoing = useSelector((state) => state.taskData.tasksGroupDoing);
    const tasksDataDone = useSelector((state) => state.taskData.tasksGroupDone);
    const tasksDataFavorite = useSelector((state) => state.taskData.tasksGroupFavorite);


    const peopleData = useSelector((state) => state.friends.friendTask);
    const guideline = useSelector((state) => state.guidelineData.guideline);
    const [displayLayoutGuideline, setDisplayLayoutGuideline] = useState(false);
    const [dataGuideline, setDataGuideline] = useState([]);

    const [initDataFilterTask, setInitDataFilterTask] = useState([]);
    const [dataFilterTask, setDataFilterTask] = useState([]);
    const [search, setSearch] = useState('');
    const [changeTabViewTask, setChangeTabViewTask] = useState(0);

    const [displayLayoutPeople, setDisplayLayoutPeople] = useState(false);
    const [changeTabPeople, setChangeTabPeople] = useState(0);
    const [dataUserFriend, setDataUserFriend] = useState([]);
    const [dataUserNotFriend, setDataUserNotFriend] = useState([]);
    const [viewAllFriend, setViewAllFriend] = useState(false);
    const [viewAllNotFriend, setViewAllNotFriend] = useState(false);
    const [info, setInfo] = useState(false);
    const [errMsg, setErrMsg] = useState({});

    const dialogFuncMap = {
        displayLayoutGuideline: setDisplayLayoutGuideline,
        displayLayoutPeople: setDisplayLayoutPeople,
    };

    // useEffect
    useEffect(() => {
        dispatch(getListTaskNotStartGroup());
    }, [dispatch]);

    useEffect(() => {
        setDataInit(tasksData);
    }, [tasksData]);

    useEffect(() => {
        setDataInit(tasksDataDoing);
    }, [tasksDataDoing]);

    useEffect(() => {
        setDataInit(tasksDataDone);
    }, [tasksDataDone]);

    useEffect(() => {
        setDataInit(tasksDataFavorite);
    }, [tasksDataFavorite]);

    useEffect(() => {
        // let a = initDataFilterTask?.map((e) => {
        //     e?.data?.filter(item => item?.tasksMapping?.taskId === guideline?.item?.task?.id);
        // })
        setDataGuideline(guideline.item === null || guideline.item === undefined ? [] : guideline.item);
    }, [guideline]);

    useEffect(() => {
        setDataUserFriend(
            peopleData.item === null || peopleData.item === undefined ? [] : peopleData.item.allFriendsTask.slice(0, 5),
        );
        setDataUserNotFriend(
            peopleData.item === null || peopleData.item === undefined ? [] : peopleData.item.allUsersTask.slice(0, 5),
        );
    }, [peopleData]);

    const setDataInit = (data) => {
        if (data.item !== undefined && data.item !== null) {
            let dataMapping = data.item;
            let dataRes = dataMapping.map((item) => ({
                ...item,
                viewAll: false,
                data: item.data.filter((e) => e.tasksMapping.taskName.toLowerCase().includes('')),
            }));
            setInitDataFilterTask(dataRes);
            setDataFilterTask(dataRes);
        } else {
            setDataFilterTask([]);
        }
    };

    // show hide dialog
    const onHide = (name) => {
        dialogFuncMap[`${name}`](false);
        setChangeTabPeople(0);
    };

    const onClick = (name, taskId, sofSkillId) => {
        dialogFuncMap[`${name}`](true);
        if (name === 'displayLayoutPeople') {
            let dataReq = {
                taskId: taskId,
                softSkillId: sofSkillId,
                statusTask: changeTabViewTask + 1,
            };
            if (changeTabViewTask === 0) {
                dispatch(getFriendsByTask(dataReq));
            } else if (changeTabViewTask === 1 || changeTabViewTask === 2) {
                dispatch(getFriendsByTaskDoingOrDone(dataReq));
            } else if (changeTabViewTask === 3) {
                dispatch(getFriendsByTaskFavorite(dataReq));
            }
            setDataUserFriend([]);
            setDataUserNotFriend([]);
            setViewAllNotFriend(false);
            setViewAllFriend(false);
        } else if (name === 'displayLayoutGuideline') {
            dispatch(getGuidelineByTask(taskId));
            setDataGuideline([]);
        }
    };

    // change Tab
    const handleChangeTabPeople = (data) => {
        setChangeTabPeople(data.index);
    };

    const handleChangeTabViewTask = (data) => {
        setDataFilterTask([]);
        setChangeTabViewTask(data.index);
        if (data.index === 0) {
            setDataInit(tasksData);
            dispatch(getListTaskNotStartGroup());
        } else if (data.index === 1) {
            setDataInit(tasksDataDoing);
            dispatch(getListTaskDoingGroup(2));
        } else if (data.index === 2) {
            setDataInit(tasksDataDone);
            dispatch(getListTaskDoneGroup(3));
        } else if (data.index === 3) {
            setDataInit(tasksDataFavorite);
            dispatch(getListTaskFavoriteGroup());
        }
    };

    // action
    const searchTasks = (value) => {
        const dataMapping = initDataFilterTask;
        let dataSearch = dataMapping.map((item) => ({
            ...item,
            viewAll: false,
            data: item.data.filter((e) => e.tasksMapping.taskName.toLowerCase().includes(value)),
        }));
        setDataFilterTask(dataSearch);
        setSearch(value);
    };
    const handleViewAll = (sofSkillId, viewAll) => {
        let dataSearch = dataFilterTask.map((item) =>
            item.softSkill.id === sofSkillId
                ? {
                    ...item,
                    viewAll: viewAll,
                }
                : {
                    ...item,
                },
        );
        setDataFilterTask(dataSearch);
    };

    const handleFavorite = (taskId, isFavorite) => {
        let dataReq = {
            taskId: taskId,
            isFavorite: !isFavorite,
            status: changeTabViewTask,
        };
        // if (condition) {

        // }
        let _dataFilterTask = [...dataFilterTask];

        for (let index = 0; index < _dataFilterTask.length; index++) {
            const item = _dataFilterTask[index].data;
            for (let i = 0; i < item.length; i++) {
                const task = item[i];
                if (task.tasksMapping.taskId === taskId) {
                    if (changeTabViewTask === 3) {
                        _dataFilterTask[index].data = _dataFilterTask[index].data.filter(
                            (val) => val.tasksMapping.taskId !== taskId,
                        );
                        setDataFilterTask(_dataFilterTask);
                        break;
                    }

                    let _task = _dataFilterTask[index].data[i];
                    _task.tasksMapping.isFavorite = !isFavorite;
                    _dataFilterTask[index].data[i] = _task;
                    setDataFilterTask(_dataFilterTask);
                    break;
                }
            }
        }
        dispatch(updateFavoriteForTask(dataReq));
    };

    const hideInfoDialog = () => {
        setInfo(false);
    };

    const openInfoDialog = () => {
        setInfo(true);
    };

    const showNoti = (singleRoom) => {
        if (!singleRoom || singleRoom === undefined || singleRoom === null) {
            setErrMsg('Phòng này đã không còn tồn tại');
            openInfoDialog();
        } else if (singleRoom.countUserOnline === singleRoom.roomSize) {
            setErrMsg('Phòng này đã đủ người');
            openInfoDialog();
        } else if (singleRoom.isLock) {
            setErrMsg('Phòng này đã bị khóa, tạm thời không thể vào được');
            openInfoDialog();
        } else {
            //redirect
            // <Redirect to={{ pathname: localStorage.getItem('token') === null ? '/login' : '/meet', state: { room } }} />
        }
    };

    const infoDialogFooter = (
        <React.Fragment>
            <Button label="OK" icon="pi pi-check" className="p-button-text" onClick={hideInfoDialog} />
        </React.Fragment>
    );

    const addFriendTask = (id) => {
        dispatch(formReset());
        dispatch(addFriend({ id: id }));
    };

    const viewAllUserFriend = () => {
        if (!viewAllFriend) {
            setDataUserFriend(peopleData.item.allFriendsTask);
        } else {
            setDataUserFriend(peopleData.item.allFriendsTask.slice(0, 5));
        }
        setViewAllFriend(!viewAllFriend);
    };

    const viewAllNotUserFriend = () => {
        if (!viewAllNotFriend) {
            setDataUserNotFriend(peopleData.item.allUsersTask);
        } else {
            setDataUserNotFriend(peopleData.item.allUsersTask.slice(0, 5));
        }
        setViewAllNotFriend(!viewAllNotFriend);
    };

    const tabViewPeople = (
        <TabView onTabChange={(e) => handleChangeTabPeople(e)} activeIndex={changeTabPeople}>
            <TabPanel header="Bạn bè" className="w-50 p-0"></TabPanel>
            <TabPanel header="Thành viên khác" className="w-50 p-0"></TabPanel>
        </TabView>
    );

    const tabViewTask = (
        <TabView className="m-4" onTabChange={(e) => handleChangeTabViewTask(e)} activeIndex={changeTabViewTask}>
            <TabPanel header="Nhiệm vụ chưa làm" className="col-3 p-0"></TabPanel>
            <TabPanel header="Nhiệm vụ đang làm" className="col-3 p-0"></TabPanel>
            <TabPanel header="Nhiệm vụ hoàn thành" className="col-3 p-0"></TabPanel>
            <TabPanel header="Nhiệm vụ yêu thích" className="col-3 p-0"></TabPanel>
        </TabView>
    );

    const layoutPeople = (
        <Dialog
            className="layout-people"
            draggable={false}
            dismissableMask={true}
            closeOnEscape={true}
            visible={displayLayoutPeople}
            footer={''}
            style={{ width: '450px', height: '420px' }}
            onHide={() => onHide('displayLayoutPeople')}
        >
            <div className="position-absolute w-75" style={{ top: '10px' }}>
                {tabViewPeople}
            </div>
            <div className="content-people mt-4">
                {changeTabPeople === 0 ? (
                    <React.Fragment>
                        {dataUserFriend.map((data, index) => (
                            <div className="friend d-flex align-items-center w-100 font-size-18 mb-3" key={index}>
                                <div className="img rounded-circle">
                                    {!data?.userRoomTaskOtherMapping.picture && (
                                        <img
                                            className="img rounded-circle"
                                            style={{ width: '50px', height: '50px' }}
                                            src={require('../../../../assets/image/user.png')}
                                            onError={(e) =>
                                            (e.target.src =
                                                'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                            }
                                            alt="img"
                                        />
                                    )}
                                    {data?.userRoomTaskOtherMapping.picture && (
                                        <img
                                            className="img rounded-circle"
                                            style={{ width: '50px', height: '50px' }}
                                            src={data?.userRoomTaskOtherMapping.picture}
                                            onError={(e) =>
                                            (e.target.src =
                                                'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                            }
                                            alt="img"
                                        />
                                    )}
                                </div>
                                <div className="user-information ml-3">
                                    <div className="user-content">
                                        <span className="user-name">{data?.userRoomTaskOtherMapping.nameUser}</span>
                                        <span className="user-nickname font-size-14 pl-3">
                                            {data?.userRoomTaskOtherMapping.nickname}
                                        </span>
                                    </div>
                                    <div className="time-join font-size-14">{data?.joinedTimeAgo}</div>
                                </div>
                            </div>
                        ))}
                        <footer className="footer d-flex justify-content-center" style={{ margin: '10px 0 -20px' }}>
                            {peopleData.item?.allFriendsTask.length > 5 ? (
                                <button
                                    className="view-more btn btn-primary font-size-14"
                                    onClick={() => {
                                        viewAllUserFriend();
                                    }}
                                >
                                    {viewAllFriend ? 'Thu gọn' : 'Xem tất cả'}
                                </button>
                            ) : (
                                ''
                            )}
                        </footer>
                    </React.Fragment>
                ) : changeTabPeople === 1 ? (
                    <React.Fragment>
                        {dataUserNotFriend.map((data, index) => (
                            <div
                                className="no-friend d-flex align-items-center justify-content-between w-100 font-size-18 mb-3"
                                key={index}
                            >
                                <div className="use-content d-flex align-items-center">
                                    <div className="img rounded-circle">
                                        {!data?.userRoomTaskOtherMapping.picture && (
                                            <img
                                                className="img rounded-circle"
                                                style={{ width: '50px', height: '50px' }}
                                                src={require('../../../../assets/image/user.png')}
                                                onError={(e) =>
                                                (e.target.src =
                                                    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                                }
                                                alt="img"
                                            />
                                        )}
                                        {data?.userRoomTaskOtherMapping.picture && (
                                            <img
                                                className="img rounded-circle"
                                                style={{ width: '50px', height: '50px' }}
                                                src={data?.userRoomTaskOtherMapping.picture}
                                                onError={(e) =>
                                                (e.target.src =
                                                    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                                }
                                                alt="img"
                                            />
                                        )}
                                    </div>
                                    <div className="user-information ml-3">
                                        <div className="user-content">
                                            <span className="user-name">{data?.userRoomTaskOtherMapping.nameUser}</span>
                                            <span className="user-nickname font-size-14 pl-3">
                                                {data?.userRoomTaskOtherMapping.nickname}
                                            </span>
                                        </div>
                                        <div className="time-join font-size-14">{data?.joinedTimeAgo}</div>
                                    </div>
                                </div>
                                <div
                                    className="add-friend rounded-circle pl-1 pb-1 pr-1 pointer"
                                    onClick={() => addFriendTask(data?.userRoomTaskOtherMapping.userId)}
                                >
                                    <UilUserPlus />
                                </div>
                            </div>
                        ))}
                        <footer className="footer d-flex justify-content-center" style={{ margin: '10px 0 -20px' }}>
                            {peopleData.item?.allUsersTask.length > 5 ? (
                                <button
                                    className="view-more btn btn-primary font-size-14"
                                    onClick={() => viewAllNotUserFriend()}
                                >
                                    {viewAllNotFriend ? 'Thu gọn' : 'Xem tất cả'}
                                </button>
                            ) : (
                                ''
                            )}
                        </footer>
                    </React.Fragment>
                ) : (
                    ''
                )}
            </div>
        </Dialog>
    );

    const layoutGuideline = (
        <Dialog
            className="layout-guideline"
            draggable={false}
            dismissableMask={true}
            closeOnEscape={true}
            visible={displayLayoutGuideline}
            style={{ width: '500px' }}
            onHide={() => onHide('displayLayoutGuideline')}
        >
            <div className="guideline-content">
                {dataGuideline?.map((item, index) => (
                    <div key={index}>
                        <div className="d-flex justify-content-center align-content-center">
                            <span className="align-items-center mx-auto font-size-18 font-weight-bold">
                                {item?.task?.name}
                            </span>
                        </div>
                        <div className="content-guideline font-size-16 mt-2 mb-3">{item?.task?.content}</div>
                        <div className="img-guideline d-flex justify-content-center align-content-center">
                            <img
                                style={{ height: '300px', width: '400px', borderRadius: '6px' }}
                                src={
                                    item?.task?.imageData
                                        ? `data:image/jpeg;base64,${item?.task?.imageData}`
                                        : require('../../../../assets/image/softskill.jpg')
                                }
                                alt=""
                                onError={(e) => (e.target.src = require('../../../../assets/image/softskill.jpg'))}
                            />
                        </div>
                        {item?.guidelineTask?.length > 0 ? (
                            <div className="tutorial-guideline mt-3">
                                <p className="title font-size-14">Hướng dẫn</p>
                                {item?.guidelineTask.map((e, index) => (
                                    <div className="content-tutorial pl-5 mb-3" key={index}>
                                        <span style={{ fontStyle: 'italic', fontWeight: '400' }}>
                                            {e?.guidelineTitle}
                                            {e?.guidelineTitle ? ':' : ''}
                                        </span>
                                        <span className="ml-3 font-size-16">{e?.guidelineContent}</span>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="tutorial-guideline mt-3">
                                <p className="title font-size-14">Hướng dẫn</p>
                                <div className="content-tutorial pl-5 mb-3" key={index}>
                                    <span className="ml-3 font-size-16">Không có hướng dẫn cho nhiệm vụ này</span>
                                </div>
                            </div>
                        )}

                        {item?.rooms.length > 0 ? (
                            <div className="room-recommend separate-top mt-3">
                                <div className="title font-size-16 font-weight-bold">
                                    Một số phòng đang làm nhiệm vụ này
                                </div>
                                <div className="row room-content justify-content-between  mt-3">
                                    {item?.rooms.map((e, index) => (
                                        <div className="room-item d-flex align-items-center col-sm-3-5">
                                            <div className="wrap-content">
                                                <div className="room-name wrap-1-line">{e.roomName}</div>
                                                <div className="room-count">
                                                    {e.countUserOnline}/{e.roomSize} người
                                                </div>
                                            </div>
                                            {e.isLock === true || e.countUserOnline >= e.roomSize ? (
                                                <div className="join" onClick={() => showNoti(e)}>
                                                    <UilSignOutAlt size="20" />
                                                </div>
                                            ) : (
                                                <div className="join">
                                                    <Link to={{ pathname: '/meet', state: { singleRoom: e } }}>
                                                        <UilSignOutAlt size="20" />
                                                    </Link>
                                                </div>
                                            )}
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ) : (
                            <div className="title font-size-16 font-weight-bold">
                                Không có phòng nào đang làm nhiệm vụ này
                            </div>
                        )}
                    </div>
                ))}
            </div>
        </Dialog>
    );

    const layoutSearch = (
        <span className="d-flex align-items-center search-input">
            <span className="icon-search">
                <UilSearchAlt size="20" />
            </span>
            <InputText
                minLength={4}
                maxLength={256}
                className="animated-search-filter"
                value={search}
                onChange={(e) => searchTasks(e.target.value)}
                placeholder="Tìm kiếm nhiệm vụ"
            />
        </span>
    );

    return (
        <React.Fragment>
            <div className="task-container">
                {layoutGuideline}
                {layoutPeople}
                {tabViewTask}
                {layoutSearch}

                {dataFilterTask.map((item, indexItem) => (
                    <div key={indexItem}>
                        <div className="task-open d-flex justify-content-between p-1">
                            {item.data.length > 0 ? (
                                <>
                                    <div className="soft-skill font-weight-bold">{item?.softSkill.name}</div>
                                    <div
                                        className="view-all link-view"
                                        onClick={() => handleViewAll(item?.softSkill?.id, !item?.viewAll)}
                                    >
                                        {' '}
                                        {item?.viewAll ? 'Thu gọn' : 'Xem tất cả'}
                                    </div>
                                </>
                            ) : (
                                ''
                            )}
                        </div>
                        <div className="task-list animated-search-filter">
                            {item.data.map((task, index) =>
                                item.viewAll ? (
                                    <div
                                        className="task-item p-3"
                                        style={{
                                            backgroundColor: colorList.filter((i) => i.id === item?.softSkill?.id)[0]
                                                ?.color,
                                        }}
                                        key={index}
                                    >
                                        <header className="d-flex align-items-center justify-content-between task-header">
                                            <div className="d-flex align-items-center task-description">
                                                <UilCrosshair
                                                    className="crosshair"
                                                    style={{ color: '#5594E0' }}
                                                    size="28"
                                                />
                                                <span className="task-name">{task?.tasksMapping.taskName}</span>
                                            </div>
                                            <span
                                                className="guideline"
                                                onClick={() =>
                                                    onClick('displayLayoutGuideline', task?.tasksMapping.taskId)
                                                }
                                            >
                                                <UilExclamationCircle size="20" />
                                            </span>
                                        </header>
                                        <div className="task-content">
                                            <div className="mb-2 content-name wrap-2-line">
                                                {task?.tasksMapping.taskContent}
                                                {/* Đây là nhiệm vụ không dành cho trẻ con chỉ dành cho người lớnĐây là nhiệm vụ không dành cho trẻ con chỉ dành cho người lớnĐây là nhiệm vụ không dành cho trẻ con chỉ dành cho người lớn */}
                                            </div>
                                            <div>
                                                <img
                                                    src={
                                                        task?.tasksMapping.imageData
                                                            ? `data:image/jpeg;base64,${task?.tasksMapping.imageData}`
                                                            : require('../../../../assets/image/softskill.jpg')
                                                    }
                                                    alt=""
                                                    onError={(e) =>
                                                        (e.target.src = require('../../../../assets/image/softskill.jpg'))
                                                    }
                                                    style={{
                                                        width: '100%',
                                                        height: '200px',
                                                        borderRadius: '5px',
                                                    }}
                                                />
                                            </div>
                                        </div>
                                        <footer className="d-flex align-items-center justify-content-between separate-top mt-3">
                                            <div className="people-take d-flex align-items-center mt-2">
                                                <UilUsersAlt size="20" />
                                                <small
                                                    className="ml-2 link-a"
                                                    onClick={() =>
                                                        onClick(
                                                            'displayLayoutPeople',
                                                            task?.tasksMapping.taskId,
                                                            item?.softSkill.id,
                                                        )
                                                    }
                                                >
                                                    {task?.countUsers} người khác{' '}
                                                    {changeTabViewTask === 0
                                                        ? 'chưa làm'
                                                        : changeTabViewTask === 1
                                                            ? 'đang làm'
                                                            : changeTabViewTask === 2
                                                                ? 'hoàn thành'
                                                                : 'yêu thích'}
                                                </small>
                                            </div>
                                            <div
                                                className="favorite mt-2 pointer"
                                                onClick={() =>
                                                    handleFavorite(
                                                        task?.tasksMapping.taskId,
                                                        task?.tasksMapping.isFavorite,
                                                    )
                                                }
                                            >
                                                {task?.tasksMapping.isFavorite ? (
                                                    <i
                                                        style={{ fontSize: '18px', color: 'red', paddingTop: '5px' }}
                                                        className="pi pi-heart-fill"
                                                    />
                                                ) : (
                                                    <UilHeart size="20" />
                                                )}
                                            </div>
                                        </footer>
                                    </div>
                                ) : item.viewAll === false && index < 6 ? (
                                    <div
                                        className="task-item p-3"
                                        style={{
                                            backgroundColor: colorList.filter((i) => i.id === item?.softSkill?.id)[0]
                                                ?.color,
                                        }}
                                        key={index}
                                    >
                                        <header className="d-flex align-items-center justify-content-between task-header">
                                            <div className="d-flex align-items-center task-description">
                                                <UilCrosshair
                                                    className="crosshair"
                                                    style={{ color: '#5594E0' }}
                                                    size="28"
                                                />
                                                <span className="task-name">{task?.tasksMapping.taskName}</span>
                                            </div>
                                            <span
                                                className="guideline"
                                                onClick={() =>
                                                    onClick('displayLayoutGuideline', task?.tasksMapping.taskId)
                                                }
                                            >
                                                <UilExclamationCircle size="20" />
                                            </span>
                                        </header>
                                        <div className="task-content">
                                            <div className="mb-2 content-name wrap-2-line">
                                                {task?.tasksMapping.taskContent}
                                            </div>
                                            <div>
                                                <img
                                                    src={
                                                        task?.tasksMapping.imageData
                                                            ? `data:image/jpeg;base64,${task?.tasksMapping.imageData}`
                                                            : require('../../../../assets/image/softskill.jpg')
                                                    }
                                                    alt=""
                                                    onError={(e) =>
                                                        (e.target.src = require('../../../../assets/image/softskill.jpg'))
                                                    }
                                                    style={{
                                                        width: '100%',
                                                        height: '200px',
                                                        borderRadius: '5px',
                                                    }}
                                                />
                                            </div>
                                        </div>
                                        <footer className="d-flex align-items-center justify-content-between separate-top mt-3">
                                            <div className="people-take d-flex align-items-center mt-2">
                                                <UilUsersAlt size="20" />
                                                <small
                                                    className="ml-2 link-a"
                                                    onClick={() =>
                                                        onClick(
                                                            'displayLayoutPeople',
                                                            task?.tasksMapping.taskId,
                                                            item?.softSkill.id,
                                                        )
                                                    }
                                                >
                                                    {task?.countUsers} người khác{' '}
                                                    {changeTabViewTask === 0
                                                        ? 'chưa làm'
                                                        : changeTabViewTask === 1
                                                            ? 'đang làm'
                                                            : changeTabViewTask === 2
                                                                ? 'hoàn thành'
                                                                : 'yêu thích'}
                                                </small>
                                            </div>
                                            <div
                                                className="favorite mt-2 pointer"
                                                onClick={() =>
                                                    handleFavorite(
                                                        task?.tasksMapping.taskId,
                                                        task?.tasksMapping.isFavorite,
                                                    )
                                                }
                                            >
                                                {task?.tasksMapping.isFavorite ? (
                                                    <i
                                                        style={{ fontSize: '18px', color: 'red', paddingTop: '5px' }}
                                                        className="pi pi-heart-fill"
                                                    />
                                                ) : (
                                                    <UilHeart size="20" />
                                                )}
                                            </div>
                                        </footer>
                                    </div>
                                ) : (
                                    <></>
                                ),
                            )}
                        </div>
                    </div>
                ))}
            </div>

            <Dialog
                visible={info}
                style={{ width: '450px', borderRadius: '0' }}
                dismissableMask
                draggable={false}
                header="Thông báo"
                modal
                footer={infoDialogFooter}
                onHide={hideInfoDialog}
                contentStyle={{
                    fontSize: '1.2rem',
                    fontWeight: '500',
                }}
            >
                <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                    <span style={{ fontSize: '1.4rem', paddingTop: '1.5rem' }}>{errMsg}</span>
                </div>
            </Dialog>
        </React.Fragment>
    );
};

export default TasksForGroup;
