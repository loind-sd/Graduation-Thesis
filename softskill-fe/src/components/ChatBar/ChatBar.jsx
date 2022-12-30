import React, { useEffect, useState } from 'react';
import './ChatBar.scss';
import { deleteFriend, getFriendList, getFriendsRecent } from '../../redux/thunks/friend-thunks';
import { useDispatch, useSelector } from 'react-redux';
import * as Unicons from '@iconscout/react-unicons';
import AddFriend from './AddFriend/AddFriend';
import imgDefault from '../../assets/image/user.png';
import { UilEllipsisV, UilUserTimes } from '@iconscout/react-unicons';

const ChatBar = () => {
    const dispatch = useDispatch();
    const friends = useSelector((state) => state.friends.friends);
    const friendsRecent = useSelector((state) => state.friends.friendsRecent);

    // useState
    const [friendsRes, setFriendsRes] = useState([]);
    const [friendsRecentRes, setFriendsRecentRes] = useState([]);

    const [friendsFilter, setFriendsFilter] = useState([]);
    const [modalShow, setModalShow] = useState(false);

    useEffect(() => {
        dispatch(getFriendList());
        dispatch(getFriendsRecent());
    }, [dispatch]);

    useEffect(() => {
        setFriendsRes(friends);
        setFriendsFilter(friends);
    }, [friends]);

    useEffect(() => {
        setFriendsRecentRes(friendsRecent);
    }, [friendsRecent]);

    const handleSearch = (value) => {
        setFriendsFilter(friendsRes.filter((e) => e.name.toLowerCase().includes(value)));
    };

    const [idSelected, setIdSelected] = useState(0);

    const handleOpen = (type, id) => {
        if (idSelected === id) {
            setIdSelected(0);
        } else {
            setIdSelected(id);
        }
    };

    const handleActionFriend = (type, id) => {
        if (type === 'deleteFriend') {
            dispatch(deleteFriend(id));
        }
        dispatch(getFriendList());
    };

    const friendArr = friendsFilter.map((data, index) => (
        <div className="info-user" key={index}>
            <ul className="info-user-list">
                <li className="content-item">
                    <div className="wrap-content">
                        <div className="item-image">
                            <img
                                src={data.picture === null ? imgDefault : data.picture}
                                onError={(e) =>
                                    (e.target.src =
                                        'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                }
                                alt="img"
                            />
                            <span className="online"></span>
                        </div>
                        <div className="item-content">
                            <span className="item-name">{data.name}</span>
                        </div>
                    </div>
                    <div className="option-user">
                        <span onClick={() => handleOpen('deleteFriend', data?.id)}>
                            {' '}
                            <UilEllipsisV />
                        </span>
                        {idSelected === data.id ? (
                            <div>
                                <div className="edit-content">
                                    <div
                                        className="item-action my-0 btn-delete"
                                        onClick={() => handleActionFriend('deleteFriend', data.id)}
                                    >
                                        <span>
                                            <UilUserTimes size={18} />
                                        </span>
                                        <span style={{ marginLeft: '5px' }}> Huỷ kết bạn</span>
                                    </div>
                                </div>
                            </div>
                        ) : null}
                    </div>
                </li>
            </ul>
        </div>
    ));
    return (
        <div className="chat-bar col-lg-0 col-md-0 ">
            <header className="header-chat">
                <div className="search-user">
                    <div className="search-user">
                        <div className="input-group mt-5 container-input">
                            <input
                                type={'text'}
                                maxLength={50}
                                onChange={(e) => handleSearch(e.target.value)}
                                className="form-control search-input"
                                placeholder={'Tìm kiếm bạn bè'}
                            />
                            <span className="input-icon">
                                <Unicons.UilSearchAlt />
                            </span>
                            <button
                                className="btn btn-light btn-custom input-group-append"
                                onClick={() => setModalShow(true)}
                            >
                                <Unicons.UilUserPlus className="icon-user" />
                            </button>
                        </div>
                        <AddFriend show={modalShow} onHide={() => setModalShow(false)} friends={friendsRecentRes} />
                    </div>
                </div>
                <h3 className="title separate">Người liên hệ</h3>
            </header>
            {friendArr}
        </div>
    );
};

export default ChatBar;
