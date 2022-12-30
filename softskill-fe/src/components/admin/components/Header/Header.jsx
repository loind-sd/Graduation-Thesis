import React, { useState } from 'react';
import './Header.scss';
import * as Unicons from '@iconscout/react-unicons';
import { Link, useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { checkEmpty } from '../../../../utils/input-validators';
import { logout } from '../../../../redux/thunks/auth-thunks';
import { useDispatch } from 'react-redux';

const Header = ({ text }) => {
    const dispatch = useDispatch();
    const history = useHistory();
    const [token, setToken] = useState(localStorage.getItem('token'));

    const onClickLogout = () => {
        setToken(null);
        dispatch(logout(history));
    };

    return (
        <React.Fragment>
            <header className="admin-header separate">
                <div className="header__info">
                    <span>{text}</span>
                </div>
                <div className="header__content">
                    <div className="logout-container-admin d-flex justify-content-center">
                        <Unicons.UilSignout className="mr-2" />
                        <Link to={'#'} onClick={onClickLogout} className="link">
                            Đăng xuất
                        </Link>
                    </div>
                </div>
            </header>
        </React.Fragment>
    );
};

export default Header;
