import React from 'react';
import './MenuBar.scss';
import * as Unicons from '@iconscout/react-unicons';
import { Link } from 'react-router-dom';

const MenuBar = (to) => {
    return (
        <div className="menu-bar">
            <div className="logo-container separate"></div>
            <div className="menu-container mt-4">
                <ul className="list-group">
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/admin-manage/dashboard' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilSignalAlt3 />
                        <Link to={'/admin-manage/dashboard'} className="link ml-2">
                            Thống kê
                        </Link>
                    </li>
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/admin-manage/acc' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilUserCheck />
                        <Link to={'/admin-manage/acc'} className="link ml-2">
                            Quản lý tài khoản
                        </Link>
                    </li>
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/admin-manage/softskills' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilApps />
                        <Link to={'/admin-manage/softskills'} className="link ml-2">
                            Quản lý kỹ năng mềm
                        </Link>
                    </li>
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/admin-manage/room' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilArchway />
                        <Link to={'/admin-manage/room'} className="link ml-2">
                            Quản lý phòng
                        </Link>
                    </li>
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/admin-manage/feedback' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilShare />
                        <Link to={'/admin-manage/feedback'} className="link ml-2">
                            Quản lý Feedback
                        </Link>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default MenuBar;
