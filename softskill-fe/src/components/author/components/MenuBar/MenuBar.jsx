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
                            to.to === '/author-manage/group-task' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilObjectGroup />
                        <Link to={'/author-manage/group-task'} className="link ml-2">
                            Quản lý nhiệm vụ nhóm
                        </Link>
                    </li>
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/author-manage/individual-task' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilUsersAlt />
                        <Link to={'/author-manage/individual-task'} className="link ml-2">
                            Quản lý nhiệm vụ cá nhân
                        </Link>
                    </li>
                    <li
                        className={`list-group-item border-0 pl-5 d-flex ${
                            to.to === '/author-manage/recommend-task' ? 'active' : ''
                        }`}
                    >
                        <Unicons.UilLightbulbAlt />
                        <Link to={'/author-manage/recommend-task'} className="link ml-2">
                            Quản lý nhiệm vụ đề xuất
                        </Link>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default MenuBar;
