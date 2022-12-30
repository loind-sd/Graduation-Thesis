import React, { useEffect, useState } from 'react';
import './InviteFriend.scss';
import { Modal } from 'react-bootstrap';
import { Avatar, InputText } from 'primereact';
import * as Unicon from '@iconscout/react-unicons';
import { useDispatch, useSelector } from 'react-redux';
import { formReset, sendInvite } from '../../../redux/thunks/notification-thunks';
import { useRef } from 'react';
import { Toast } from 'primereact/toast';
import { sendWorldChat } from '../../../redux/thunks/chat-thunks';

const InviteFriend = (props) => {
    const dispatch = useDispatch();

    // useState
    const [friends, setFriends] = useState([]);
    const [friendsFilter, setFriendsFilter] = useState([]);

    // useSelector
    const success = useSelector((state) => state.notificationData.success);

    // toast
    const toast = useRef(null);

    useEffect(() => {
        setFriends(props?.friends);
        setFriendsFilter(props?.friends);
    }, [props?.friends]);

    const handleSearchFriend = (value) => {
        setFriendsFilter(friends.filter((e) => e.name.toLowerCase().includes(value)));
    };
    useEffect(() => {
        if (success === '200') {
            toast.current.show({
                severity: 'success',
                summary: 'Thành công',
                detail: 'Gửi lời mời thành công!',
                life: 3000,
            });
            dispatch(formReset());
        } else if (success === '400' || success === '404' || success === '500') {
            toast.current.show({
                severity: 'error',
                summary: 'Thất bại',
                detail: 'Gửi lời mời thất bại!',
                life: 3000,
            });
            dispatch(formReset());
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [success]);

    const handleInviteFriend = (id) => {
        if (props?.friends?.show === true) {
            return;
        }
        let dataReq = {
            senderId: props?.user?.userDetails?.id,
            typeId: 2,
            userId: id,
        };
        dispatch(formReset());
        dispatch(sendInvite(dataReq));
    };

    const handleInviteCommunity = () => {
        dispatch(sendWorldChat({ content: props?.room?.roomId, channelId: 2 }));
        toast.current.show({
            severity: 'success',
            summary: 'Thành công',
            detail: 'Gửi lời mời đến kênh thế giới thành công!',
            life: 3000,
        });
    };

    return (
        <React.Fragment>
            <Toast ref={toast} />
            <Modal
                {...props}
                size="md"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                className="invite-friend"
            >
                <Modal.Header closeButton="true">
                    <Modal.Title id="contained-modal-title-vcenter">Mời bạn bè vào phòng</Modal.Title>
                </Modal.Header>
                <Modal.Body className="p-0 mt-5">
                    <div className="col-12 pb-5 modal-invite-body">
                        <div className="p-inputgroup">
                            <InputText
                                maxLength={30}
                                placeholder="Tìm bạn bè"
                                onChange={(e) => handleSearchFriend(e.target.value)}
                                className="border-0 invite-search invite-search-input"
                            />
                            <span className="p-inputgroup-addon invite-search invite-search-icon border-0">
                                <Unicon.UilSearch />
                            </span>
                        </div>
                    </div>
                    <ul className="list-group list-friend">
                        {friendsFilter?.map((item, index) => (
                            <li className="list-group-item" key={index}>
                                <div className="row align-items-center justify-content-between">
                                    <div className="wrap-item d-flex">
                                        {/* <Avatar label="P" shape="circle" /> */}
                                        <div className="item-image">
                                            <img
                                                src={
                                                    item?.picture === null
                                                        ? require('./../../../assets/image/user.png')
                                                        : item?.picture
                                                }
                                                onError={(e) =>
                                                    (e.target.src =
                                                        'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                                }
                                                alt="Hình ảnh"
                                            />
                                        </div>
                                        <div className="wrap-name ml-3">
                                            <div className="font-size-18">{item?.name}</div>
                                            <i className="font-size-14">{item?.nickname}</i>
                                        </div>
                                    </div>
                                    <button
                                        className="my-0 btn-invite"
                                        type={'button'}
                                        onClick={() => handleInviteFriend(item?.id)}
                                    >
                                        Mời
                                    </button>
                                </div>
                            </li>
                        ))}
                    </ul>
                </Modal.Body>
                <Modal.Footer className="d-flex justify-content-center">
                    <button className="invite-community" type={'button'} onClick={() => handleInviteCommunity()}>
                        {' '}
                        Mời bạn bè từ cộng đồng
                    </button>
                </Modal.Footer>
            </Modal>
        </React.Fragment>
    );
};

export default InviteFriend;
