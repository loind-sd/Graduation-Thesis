import React from 'react';
import Header from '../../admin/components/Header/Header';
import MenuBar from '../components/MenuBar/MenuBar';
import './DefaultLayout.scss';

function DefaultLayout({ children, to, text }) {
    return (
        <React.Fragment>
            <MenuBar to={to} />
            <div className="content-base">
                <Header text={text} />
                <div className="content-children">{children}</div>
            </div>
        </React.Fragment>
    );
}

export default DefaultLayout;
