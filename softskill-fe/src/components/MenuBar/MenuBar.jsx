import React, { useState } from 'react';
import './MenuBar.scss';
import { Link, useHistory } from 'react-router-dom';
import { logout } from '../../redux/thunks/auth-thunks';
import { useDispatch } from 'react-redux';
import { Collapse, ListGroup } from 'react-bootstrap';
import * as Unicons from '@iconscout/react-unicons';
import { useEffect } from 'react';
import { Cookies } from 'react-cookie';
const MenuBar = () => {
  const dispatch = useDispatch();
  const cookies = new Cookies();
  const history = useHistory();
  const [showListTask, setShowListTask] = useState(false);

  const [token, setToken] = useState(localStorage.getItem('token'));
  const onClickLogout = () => {
    setToken(null);
    dispatch(logout(history));
  };
  const [open, setOpen] = useState(true);
  const [openParent, setOpenParent] = useState(cookies.get('active-parent') == undefined || cookies.get('active-parent') == null ? 0 : cookies.get('active-parent'))
  const [openTask, setOpenTask] = useState(false);
  const [active, setActive] = useState(cookies.get('active') == undefined || localStorage.getItem('token') == null ? 0 : cookies.get('active'));
  function onClickActive(e) {
    if (localStorage.getItem('token') == null) {
      history.push('/login');
    }
    else {
      setActive(e.value);
      history.push(e.url);
      cookies.set('active', e.value)
    }
  }

  const onActiveParent = (e) => {
    if (localStorage.getItem('token') == null) {
      history.push('/login');
    }
    else {
      setOpenParent(e.value);
      cookies.set('active-parent', e.value)
    }
  }
  return (
    <div className="menu-bar">
      <div className="logo-container separate"></div>
      <div className="menu-container mt-4">
        <ListGroup>
          <ListGroup.Item onClick={() => onActiveParent({ value: 0 })} className={(active == 0 || active == 1) ? 'pl-0 border-0 d-block active' : 'pl-0 border-0 d-block'} >
            <Unicons.UilEstate className="ml-5" />
            <span className={'ml-2'}>Phòng họp mặt</span>
            {openParent == 0 ? <Unicons.UilAngleDown /> : <Unicons.UilAngleUp />}
          </ListGroup.Item>
          <Collapse in={openParent == 0}>
            <div className="row">
              <ListGroup className="">
                <ListGroup.Item onClick={() => onClickActive({ value: 0, url: 'active-room' })} className={active == 0 ? 'border-0 pl-5 d-flex active' : ' pl-5 border-0 d-flex'} >
                  <Link to={'/active-room'} className="link pl-5 ml-3">
                    Phòng hoạt động
                  </Link>
                </ListGroup.Item>
                <ListGroup.Item onClick={() => onClickActive({ value: 1, url: 'booking-room' })} className={active == 1 ? 'border-0 pl-5 d-flex active' : ' pl-5 border-0 d-flex'}>
                  <Link to={'/booking-room'} className="link pl-5 ml-3">
                    Phòng đặt lịch
                  </Link>
                </ListGroup.Item>
              </ListGroup>
            </div>
          </Collapse>
          <ListGroup.Item onClick={() => onActiveParent({ value: 1 })} className={active == 4 || active == 5 || active == 6 || active == 7 ? "pl-0 border-0 d-block active" : "pl-0 border-0 d-block"}>
            <Unicons.UilCrosshair className='ml-5' size={29} />
            <span className={'ml-2'}>Nhiệm vụ</span>
            {openParent == 1 ? <Unicons.UilAngleDown /> : <Unicons.UilAngleUp />}
          </ListGroup.Item>
          <Collapse in={openParent == 1}>
            <div className="row">
              <ListGroup className="">
                <ListGroup.Item onClick={() => onClickActive({ value: 4, url: 'tasksForGroup' })} className={active == 4 ? 'border-0 pl-5 d-flex active' : 'border-0 pl-5 d-flex'}>
                  <Link onClick={() => setActive(4)} to={"/tasksForGroup"} className="link pl-5 ml-3">
                    Nhiệm vụ nhóm
                  </Link>
                </ListGroup.Item>
                <ListGroup.Item onClick={() => onClickActive({ value: 5, url: 'tasksForPersonal' })} className={active == 5 ? 'border-0 pl-5 d-flex active' : 'border-0 pl-5 d-flex'}>
                  <Link to={"/tasksForPersonal"} className="link pl-5 ml-3">
                    Nhiệm vụ cá nhân
                  </Link>
                </ListGroup.Item>
                <ListGroup.Item onClick={() => onClickActive({ value: 6, url: 'tasksForYou' })} className={active == 6 ? 'border-0 pl-5 d-flex active' : 'border-0 pl-5 d-flex'}>
                  <Link to={"/tasksForYou"} className="link pl-5 ml-3">
                    Dành cho bạn
                  </Link>
                </ListGroup.Item>
                <ListGroup.Item onClick={() => onClickActive({ value: 7, url: 'tasksProposed' })} className={active == 7 ? 'border-0 pl-5 d-flex active' : 'border-0 pl-5 d-flex'}>
                  <Link to={"/tasksProposed"} className="link pl-5 ml-3">
                    Nhiệm vụ đề xuất
                  </Link>
                </ListGroup.Item>
              </ListGroup>
            </div>
          </Collapse>

          <ListGroup.Item onClick={() => onClickActive({ value: 8, url: 'chat' })} className={active == 8 ? 'border-0 pl-5 d-flex active' : 'border-0 pl-5 d-flex'}>
            <Unicons.UilComment />
            <Link to={'chat'} className="link ml-2">
              Tin nhắn
            </Link>
          </ListGroup.Item>
          <ListGroup.Item onClick={() => onClickActive({ value: 9, url: 'dashboard' })} className={active == 9 ? 'border-0 pl-5 d-flex active' : 'border-0 pl-5 d-flex'}>
            <Unicons.UilSignalAlt3 />
            <Link to={'dashboard'} className="link ml-2">
              Thống kê
            </Link>
          </ListGroup.Item>
        </ListGroup>
      </div>
      <div className="card-container ">
        <div className="card">
          <div className="card-body mb">
            <p className="card-text"></p>
          </div>
        </div>
      </div>
      {token == null ? (
        <></>
      ) : (
        <div className="logout-container d-flex justify-content-center">
          <Unicons.UilSignout className="mr-2" />
          <Link to={'#'} onClick={onClickLogout} className="link">
            Đăng xuất
          </Link>
        </div>
      )}
    </div>
  );
};

export default MenuBar;
