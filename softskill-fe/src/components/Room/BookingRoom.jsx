import React, { useEffect, useState } from 'react';
import './ActiveRoom.scss';
import * as Unicons from '@iconscout/react-unicons';
import { Button } from 'primereact/button';
import { ConfirmDialog, confirmDialog, InputText, Slider, TabPanel, TabView, Toast } from 'primereact';
import { useDispatch, useSelector } from 'react-redux';
import {
    getRoomBookingCreateBy,
    getRoomJoined,
    getRooms,
    registerBookingRoom,
} from '../../redux/thunks/room-booking-thunks';
import _ from 'lodash';
import moment from 'moment';
import { useRef } from 'react';
import { deleteRoom } from '../../redux/thunks/room-active-thunks';
import Countdown from 'react-countdown';
import { resetFormData } from '../../redux/actions/booking-room-action';
import Spinner from '../Common/Spinner/Spinner';
function onClickSeeAll(e) { }

const Room = (room) => {
    const [category, setCategory] = useState(0);
    const [sliderSize, setSliderSize] = useState([0, 100]);
    const [showFilter, setShowFilter] = useState(false);
    const [valueFilter, setValueFilter] = useState([0, 0]);
    let dataDelete = {};
    const [isDelete, setIsDelete] = useState(false);
    const toast = useRef(null);
    let idRoomRegisted = null;
    let dataRegister = {};
    let countUserRegisterId = null;
    //get room data api

    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getRooms(room));
        dispatch(getRoomJoined(room));
        dispatch(getRoomBookingCreateBy(room));
    }, [dispatch]);
    const rooms = useSelector((state) => state.bookingRoomData);
    const [filteredList, setFilteredList] = new useState([]);

    useEffect(() => {
        if (document.getElementById('search').value === '') {
            if (category === 0) {
                setFilteredList(rooms.rooms);
            } else if (category === 2) {
                setFilteredList(rooms.created);
            } else if (category === 1) {
                setFilteredList(rooms.joined);
            }
        }
    }, [category]);
    useEffect(() => {
        setFilteredList(rooms.rooms);
    }, [rooms]);
    useEffect(() => {
        if (isDelete == true) {

            setFilteredList(rooms.joined);
        }
    }, [isDelete]);

    //filter by group soft skill
    const groups = _.groupBy(filteredList, 'softSkillId');

    //search onChange
    const filterBySearch = () => {
        const query = document.getElementById('search').value;
        var updatedList = [];
        if (category === 0) {
            updatedList = [...rooms.rooms];
        } else if (category === 2) {
            updatedList = [...rooms.created];
        } else if (category === 1) {
            updatedList = [...rooms.joined];
        }

        updatedList = updatedList.filter(({ roomName, roomSize }) => {
            return (
                roomName.toString().toLowerCase().includes(query.toLowerCase()) &&
                roomSize <= sliderSize[1] &&
                roomSize >= sliderSize[0]
            );
        });
        setFilteredList(updatedList);
    };

    const accept = () => {
        dispatch(resetFormData());

        dispatch(deleteRoom(dataDelete));
        toast.current.show({
            severity: 'info',
            summary: 'Thành công',
            detail: 'Xoá thành công',
            life: 3000,
        });
        setIsDelete(true);
    };

    const reject = () => {
        toast.current.show({
            severity: 'warn',
            summary: 'Rejected',
            detail: 'Từ chối xoá',
            life: 3000,
        });
    };
    const confirm = (roomId, taskId, roomName) => {
        console.table(roomId, taskId);
        dataDelete = { roomId: roomId, taskId: parseInt(taskId) };
        confirmDialog({
            header: 'Xác nhận xoá',
            message: 'Bạn có muốn xoá phòng: ' + roomName + '?',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Xác nhận',
            rejectLabel: 'Hủy',
            accept,
            reject,
        });
    };

    const accept1 = () => {
        toast.current.show({ severity: 'info', summary: 'Xác nhận', detail: 'Bạn đã đăng ký thành công', life: 3000 });
        document.getElementById(idRoomRegisted).className = 'btn btn-join col-12 mt-2 disabled ';
        document.getElementById(idRoomRegisted).style =
            'pointer-events: none !important; cursor: context-menu !important';
        document.getElementById(idRoomRegisted).innerText = 'Đã tham gia';
        document.getElementById('count-user-' + countUserRegisterId).innerText =
            parseInt(document.getElementById('count-user-' + countUserRegisterId).textContent) + 1;
        dispatch(registerBookingRoom(dataRegister));
        setInterval(() => {
            window.location.reload();
        }, 3000);
    };

    const onRegisterRoom = (e, roomId, taskId) => {
        confirmDialog({
            message: 'Bạn có muốn đăng ký phòng này?',
            header: 'Xác nhận',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Xác nhận',
            rejectLabel: 'Hủy',
            accept: accept1,
        });
        idRoomRegisted = e.target.id;
        dataRegister = { roomId: roomId, taskId: parseInt(taskId) };
        countUserRegisterId = e.target.id.toString().slice(14, 16);

        // document.getElementById(e.target.id).style = 'background-color: grey !important';
        // document.getElementById(e.target.id).className = 'btn btn-join col-12 mt-2 bg-dark';
    };
    const removeRoomTimeout = (index) => {
        document.getElementById('room-index-' + index).remove();

    };
    //loop soft skill
    let listSoftSkill = [];
    let temp = 0;
    for (let index = 0; index < Object.keys(groups).length; index++) {
        let btnAll =
            Object.values(groups)[index].length > 3 ? (
                <Button onClick={onClickSeeAll} className="">
                    {' '}
                    Tất cả{' '}
                </Button>
            ) : null;
        listSoftSkill.push(
            <div className={'room-container row softskill-' + index} key={index}>
                <div className="col-12 d-flex ">
                    <div className="room-title col-6 font-weight-bold">
                        Kỹ Năng {Object.values(groups)[index][0].softSkillName}
                    </div>
                    <div className="see-all col-6 d-flex justify-content-end">{btnAll}</div>
                </div>

                {(() => {
                    let roomList = [];
                    let roomRow = [];
                    for (let i = 1; i <= Object.values(groups)[index].length; i++) {
                        const singleRoom = Array.from(Object.values(groups)[index])[i - 1];
                        if (singleRoom.countUserOnline === null) {
                            singleRoom.countUserOnline = 0;
                        }
                        roomList.push(
                            <div
                                className="room-container col-xl-4 col-lg-4 col-md-6 col-xs-12"
                                id={'room-index-' + singleRoom.roomId}
                                key={i}
                            >
                                <div className="upper-part row ">
                                    <div className="member-container col-6 text-danger">
                                        <Countdown
                                            date={Date.parse(singleRoom.startTime)}
                                            onComplete={() => removeRoomTimeout(singleRoom.roomId)}
                                        />
                                    </div>
                                    <div className="room-name text-dark font-weight-bold col-12 w-100 mt-3 mb-3">
                                        {singleRoom.roomName === '' ? '&nbsp' : singleRoom.roomName}
                                    </div>
                                    {category == 2 ? (
                                        <Button
                                            onClick={() =>
                                                confirm(singleRoom.roomId, singleRoom.taskId, singleRoom.roomName)
                                            }
                                            className="delete-room text-danger"
                                        >
                                            <Unicons.UilInfoCircle />
                                        </Button>
                                    ) : null}
                                </div>
                                <div className="avatar-room"></div>
                                <Button className="three-dot border-0 p-2" tooltip={singleRoom.taskName}>
                                    Nhiệm vụ
                                </Button>
                                <div className="lower-part position-relative">
                                    <div className="description">
                                        {singleRoom.description === '' ? '\u00A0' : singleRoom.description}
                                    </div>
                                    <div className="lower-static row align-items-end">
                                        <div className="room-info p-0 col-12 d-flex">
                                            <Unicons.UilClock className="p-2 mr-1 text-dark" />
                                            <span> {moment(singleRoom.startTime).format('DD-MM-yyyy HH:mm')} </span>
                                        </div>
                                        <div className="user-count p-0 col-12 d-flex">
                                            <Unicons.UilUsersAlt className="p-2 text-dark" />
                                            <span className={'p-1 '}>
                                                <span id={'count-user-' + temp}>{singleRoom.countUserRegister}</span>{' '}
                                                người đã tham gia
                                            </span>
                                        </div>
                                        {category !== 0 ? (
                                            <button className="btn btn-join col-12 mt-2 disabled">Đã tham gia</button>
                                        ) : (
                                            <button
                                                className="btn btn-join col-12 mt-2"
                                                id={'roomToRegiser-' + temp}
                                                onClick={(e) => {
                                                    onRegisterRoom(e, singleRoom.roomId, singleRoom.taskId);
                                                }}
                                            >
                                                Tham gia
                                            </button>
                                        )}
                                    </div>
                                </div>
                            </div>,
                        );
                        if (roomList.length === 3 || i === Object.values(groups)[index].length) {
                            roomRow.push(
                                <div className="row w-100" key={i}>
                                    {roomList}
                                </div>,
                            );
                            roomList = [];
                        }
                        temp++;
                    }
                    return roomRow;
                })()}
            </div>,
        );
    }
    return (
        <div>
            {rooms.isLoaded == true ? (
                <div className="spinner-container">
                    <Spinner />
                </div>
            ) : (
                ''
            )}
            <ConfirmDialog />
            <Toast ref={toast} />
            <div className="input-group mt-5 search-container">
                <div className="input-group-prepend">
                    <span className="input-group-text" id="search-addon">
                        <Unicons.UilSearchAlt />
                    </span>
                </div>
                <input
                    type={'text'}
                    className="form-control search"
                    placeholder="Tìm kiếm kỹ năng mềm, phòng học, ..."
                    onChange={filterBySearch}
                    id="search"
                />
                <div className="input-group-append filter-container">
                    <button className="input-group-text" id="filter-addon" onClick={() => setShowFilter(!showFilter)}>
                        <Unicons.UilFilter />
                    </button>
                </div>
                {showFilter ? (
                    <div className="slider">
                        <p>
                            Room size: {sliderSize[0]} - {sliderSize[1]}
                        </p>
                        <Slider
                            range={true}
                            min={0}
                            max={100}
                            value={sliderSize}
                            onChange={(e) => setSliderSize(e.value)}
                            orientation="horizontal"
                        />
                        <Button
                            label="Áp dụng"
                            className="p-button-raised mt-5"
                            onClick={() => setValueFilter(sliderSize)}
                        />
                    </div>
                ) : (
                    ''
                )}
            </div>
            <div className="tab-view-container mt-5">
                <TabView
                    className="col-12 row tabView"
                    activeIndex={category}
                    onTabChange={(e) => setCategory(e.index)}
                >
                    <TabPanel
                        header="Phòng cộng đồng"
                        className="col-4 py-3 d-flex justify-content-center font-weight-bold"
                    ></TabPanel>
                    <TabPanel
                        header="Phòng đã đặt"
                        className="col-4 py-3 d-flex justify-content-center  font-weight-bold"
                    ></TabPanel>
                    <TabPanel
                        header="Phòng của bạn"
                        className="col-4 py-3 d-flex justify-content-center font-weight-bold"
                    ></TabPanel>
                </TabView>
            </div>
            {listSoftSkill}
        </div>
    );
};

export default Room;
