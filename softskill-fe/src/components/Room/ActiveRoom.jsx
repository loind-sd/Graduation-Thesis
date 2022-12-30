import React, { useEffect, useRef, useState } from 'react';
import './ActiveRoom.scss';
import * as Unicons from '@iconscout/react-unicons';
import { Button } from 'primereact/button';
import { InputText, Slider, TabPanel, TabView, ConfirmDialog, Toast } from 'primereact';
import { useDispatch, useSelector } from 'react-redux';
import { getRoomCreateBy, getRoomJoined, getRooms } from '../../redux/thunks/room-active-thunks';
import _ from 'lodash';
import moment from 'moment';
import { Link } from 'react-router-dom';
import CountUp from 'react-countup';

import Timer from 'react-timer-wrapper';
import Timecode from 'react-timecode';
import Spinner from '../Common/Spinner/Spinner';
import { socket } from './../../pages/App/App'
const Room = (room) => {
    const [category, setCategory] = useState(0);
    const [sliderSize, setSliderSize] = useState([0, 100]);
    const [showFilter, setShowFilter] = useState(false);
    // eslint-disable-next-line no-unused-vars
    const [valueFilter, setValueFilter] = useState([0, 0]);
    const [arrActive, setActiveArr] = useState([]);
    //get room data api
    const token = localStorage.getItem('token');
    const dispatch = useDispatch();
    const isBack = localStorage.getItem('isBack');
    function onClickSeeAll(e) {
        const roomType = e.target.getAttribute('room-type');
        const isShow = e.target.getAttribute('is-show');
        if (isShow) {
            arrActive[roomType - 1] = true;
            setActiveArr(arrActive);

            // document.getElementsByClassName('room-type' + roomType).style.display = 'block';
        } else {
            arrActive[roomType - 1] = false;
        }
    }

    useEffect(() => {
        dispatch(getRooms(room));
        dispatch(getRoomJoined(room));
        dispatch(getRoomCreateBy(room));
    }, [dispatch]);

    useEffect(() => {
        if (isBack == 'true') {
            dispatch(getRooms(room));
            dispatch(getRoomJoined(room));
            dispatch(getRoomCreateBy(room));
            localStorage.setItem('isBack', false);
        }
    }, [isBack]);

    const rooms = useSelector((state) => state.roomData);
    // setInterval(handleDisplayData, 1000);
    const [filteredList, setFilteredList] = useState([]);
    useEffect(() => {
        if (document.getElementById('search').value === '') {
            if (category == 0) {
                setFilteredList(rooms.rooms);
            } else if (category == 2) {
                setFilteredList(rooms.created);
            } else if (category == 1) {
                setFilteredList(rooms.joined);
            }
        }
    }, [category]);
    useEffect(() => {
        setFilteredList(rooms.rooms);
    }, [rooms]);

    function convertMS(ms) {
        var d, h, m, s;
        s = Math.floor(ms / 1000);
        m = Math.floor(s / 60);
        s = s % 60;
        h = Math.floor(m / 60);
        m = m % 60;
        d = Math.floor(h / 24);
        h = h % 24;
        h += d * 24;
        return h + ':' + m + ':' + s;
    }
    //filter by group soft skill
    const groups = _.groupBy(filteredList, 'softSkillId');
    const toast = useRef(null);

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
    useEffect(() => {
        if (socket) {
            socket.on('update-lock-room-receive', (data) => {
                if (filteredList) {
                    let index = -1;
                    for (let i = 0; i < filteredList.length; i++) {
                        const element = filteredList[i];
                        if (element.roomId === data.roomId) {
                            index = i;
                            break;
                        }
                    }

                    if (index !== -1) {
                        let _fiterList = [...filteredList];
                        let _room = _fiterList[index];
                        _room.isLock = data.changeLock;
                        _fiterList[index] = _room;
                        setFilteredList(_fiterList);
                    }
                }
            })
        }
    }, [filteredList])

    //loop soft skill
    let listSoftSkill = [];
    let temp = 0;
    for (let index = 0; index < Object.keys(groups).length; index++) {
        let btnAll =
            Object.values(groups)[index].length > 3 ? (
                <button
                    type="button"
                    room-type={index + 1}
                    is-show={'' + arrActive[index]}
                    onClick={(e) => onClickSeeAll(e)}
                    className="btn-all"
                >
                    {' '}
                    Tất cả{' '}
                </button>
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
                            <div className="room-container col-xl-4 col-lg-4 col-md-6 col-xs-12" key={i}>
                                <div className="upper-part row ">
                                    <div className="member-container col-xl-3 col-lg-4 col-md-5">
                                        <Timer
                                            time={Math.floor(
                                                moment(new Date()).valueOf() - moment(singleRoom.startTime),
                                            )}
                                            active={true}
                                            duration={10000000000000000}
                                        >
                                            <Timecode />
                                        </Timer>
                                    </div>
                                    <div className="room-name text-dark font-weight-bold col-12 w-100 mb-3">
                                        {singleRoom.roomName === '' ? '&nbsp' : singleRoom.roomName}
                                    </div>
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
                                            <Unicons.UilLock className="p-2 mr-1 text-dark" />
                                            <span> {singleRoom.isLock === false ? 'Phòng mở' : 'Phòng đóng'} </span>
                                        </div>
                                        <div className="user-count p-0 col-12 d-flex">
                                            <Unicons.UilUsersAlt className="p-2 text-dark" />
                                            <span className="p-1">
                                                {singleRoom.countUserOnline}/{singleRoom.roomSize}
                                            </span>
                                        </div>
                                        <Link
                                            onClick={(e) => {
                                                if (singleRoom.countUserOnline >= singleRoom.roomSize) {
                                                    toast.current.show({
                                                        severity: 'error',
                                                        summary: 'Thông báo',
                                                        detail: 'Phòng đã đầy',
                                                        life: 3000,
                                                    });
                                                    e.preventDefault();
                                                }
                                                if (singleRoom.isLock && category !== 2) {
                                                    toast.current.show({
                                                        severity: 'error',
                                                        summary: 'Thông báo',
                                                        detail: 'Phòng đã bị khóa, tạm thời không thể vào được',
                                                        life: 3000,
                                                    });
                                                    e.preventDefault();
                                                }

                                            }}
                                            to={{
                                                pathname: localStorage.getItem('token') === null ? '/login' : '/meet',
                                                state: { singleRoom },
                                            }}
                                            disabled={token === null ? true : false}
                                            className="btn btn-join col-12 mt-2"
                                        >
                                            Vào phòng
                                        </Link>
                                    </div>
                                </div>
                            </div>,
                        );
                        if (roomList.length === 3 || i === Object.values(groups)[index].length) {
                            if (roomRow.length >= 1) {
                                roomRow.push(
                                    <div className={'row room-row w-100 room-type-' + (index + 1)} key={i}>
                                        {roomList}
                                    </div>,
                                );
                            } else {
                                roomRow.push(
                                    <div className={'row room-row w-100 room-type-' + (index + 1)} key={i}>
                                        {roomList}
                                    </div>,
                                );
                            }
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
                <div className="input-group-append text-dark filter-container">
                    <button
                        className="input-group-text text-dark"
                        id="filter-addon"
                        type="button"
                        onClick={() => setShowFilter(!showFilter)}
                    >
                        <Unicons.UilFilter />
                    </button>
                </div>
                {showFilter ? (
                    <div className="slider">
                        <p className="text-dark text-center">Số người trong phòng </p>
                        <div className="row justify-content-between mb-4">
                            <input
                                type="text"
                                className="col-5 disabled border rounded text-center"
                                name=""
                                id=""
                                value={sliderSize[0]}
                            />
                            <input
                                type="text"
                                name=""
                                className="col-5 disabled border rounded text-center"
                                id=""
                                value={sliderSize[1]}
                            />
                        </div>
                        <Slider
                            range={true}
                            min={0}
                            max={100}
                            value={sliderSize}
                            onChange={(e) => {
                                setSliderSize(e.value);
                            }}
                            orientation="horizontal"
                        />
                        <div className="row justify-content-center">
                            <input
                                type={'button'}
                                value="Áp dụng"
                                className=" mt-3 btn-filter"
                                onClick={() => {
                                    setValueFilter(sliderSize);
                                    filterBySearch();
                                }}
                            />
                        </div>
                    </div>
                ) : (
                    ''
                )}
            </div>
            <div className="tab-view-container mt-5">
                <TabView
                    className="col-12 row tabView"
                    activeIndex={category}
                    onTabChange={(e) => {
                        setCategory(e.index);
                    }}
                >
                    <TabPanel
                        header="Phòng cộng đồng"
                        className="col-4 py-3 d-flex justify-content-center font-weight-bold"
                    ></TabPanel>
                    <TabPanel
                        header="Phòng đã tham gia"
                        className="col-4 py-3 d-flex justify-content-center  font-weight-bold"
                    ></TabPanel>
                    <TabPanel
                        header="Phòng của bạn"
                        className="col-4 py-3 d-flex justify-content-center font-weight-bold"
                    ></TabPanel>
                </TabView>
            </div>
            {listSoftSkill}
            <ConfirmDialog />
        </div>
    );
};

export default Room;
