import React, { useEffect, useState } from 'react';
import './HeaderAction.scss';
import UilTimes from '@iconscout/react-unicons/icons/uil-times';
import UilCheck from '@iconscout/react-unicons/icons/uil-check';
import UilPen from '@iconscout/react-unicons/icons/uil-pen';
import dfPicture from '../../../assets/image/user.png';
import { Password } from 'primereact/password';
import { InputText } from 'primereact/inputtext';
import {
    changeName,
    changeNickName,
    changePassword,
    forgotPassword,
    resetFormUser,
} from '../../../redux/thunks/user-thunks';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import { deleteNotification, getListNotification } from '../../../redux/thunks/notification-thunks';
import { getTimeWithString } from '../../../utils/common-service';
import { clear } from '../../../redux/reducers/deliveredNotifs';
import { acceptFriendRequest, denyFriendRequest } from '../../../redux/thunks/friend-thunks';
import { Button, Dialog, Divider, Toast } from 'primereact';
import { useRef } from 'react';
import { HTTP_200 } from './../../../utils/constant/CommonConstant';
import { getRoomsForChatSuccess } from './../../../redux/actions/room-noti-action';

const objChangePassword = {
    oldPassword: '',
    newPassword: '',
    renewPassword: '',
};

const HeaderAction = () => {
    let notifications = [];
    let patternName =
        /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]$/;
    let patternNameRange =
        /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]{4,20}$/;
    let patternNicknameRange = /^[a-zA-Z0-9]{4,20}$/;
    let patternPasswordRange = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d`~!@#$^&*()_+}|:"<>?,.\/;'\\\][=-]{8,50}$/;

    const toast = useRef(null);
    const dispatch = useDispatch();
    const history = useHistory();

    // useSelector
    const successSelector = useSelector((state) => state.userData.success);
    const errorSelector = useSelector((state) => state.userData.error);
    const userAuth = useSelector((state) => state.authData.user);
    const getNotifications = useSelector((state) => state.notificationData.notifications);
    const allDeliveredNotifs = useSelector((state) => state.deliveredNotificationData.value.notifs);

    // useState
    // const [notifications, setNotifications] = useState([]);
    const [page, setPage] = useState(1);
    const [action, setAction] = useState({
        changeName: 'notChange',
        changeNickName: 'notChange',
        changePassWord: 'notChange',
    });
    const [nickName, setNickName] = useState('');
    const [info, setInfo] = useState(false);
    const [name, setName] = useState('');
    const [password, setPassword] = useState({
        oldPassword: '',
        newPassword: '',
        reNewPassword: '',
    });

    const [showNotify, setShowNotify] = useState(0);
    const [showUser, setShowUser] = useState(0);

    // useEffect
    useEffect(() => {
        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() => {
        if (successSelector === HTTP_200) {
            toast.current.show({
                severity: 'success',
                summary: 'Thành công',
                detail: 'Đổi tên thành công!',
                life: 3000,
            });
        }
        if (errorSelector !== HTTP_200 && errorSelector !== '') {
            toast.current.show({
                severity: 'error',
                summary: 'Thất bại',
                detail: 'Đổi tên thất bại!',
                life: 3000,
            });
        }
    }, [errorSelector, successSelector]);

    const openNotify = () => {
        dispatch(getListNotification(page));
        setShowNotify(1);
        dispatch(clear());
    };
    // open
    const openUser = () => {
        setShowUser(1);
    };
    // Click outside action
    const actionNotifyRef = useRef(null);
    const actionUserRef = useRef(null);

    useOutsideActionNotify(actionNotifyRef);
    useOutsideActionUser(actionUserRef);

    function useOutsideActionNotify(ref) {
        const handleClickOutside = (event) => {
            if (ref.current && !ref.current.contains(event.target)) {
                setShowNotify(0);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }

    function useOutsideActionUser(ref) {
        const handleClickOutside = (event) => {
            if (ref.current && !ref.current.contains(event.target)) {
                setShowUser(0);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }

    //  scroll get data notify
    const handleScroll = (event) => {
        let scrollTop = event.currentTarget.scrollTop;
        let calculator = Math.floor(scrollTop / 1000) + 1;
        if (calculator > page) {
            dispatch(getListNotification(calculator));
            setPage(calculator);
        }
    };

    // action edit
    const [submitEdit, setSubmitEdit] = useState(false);
    const [error, setError] = useState(false);
    const [errorSamePass, setErrorSamePass] = useState(false);
    const [errorNewPass, setErrorNewPass] = useState(false);
    const [errorReNewPass, setErrorReNewPass] = useState(false);
    const [errorEqualNewPass, setErrorEqualNewPass] = useState(false);
    const [messageRepass, setMessageRepass] = useState('');
    const [messageStrongPass, setMessageStrongPass] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmitName = () => {
        setSubmitEdit(true);
        if (error === true) {
            setMessage('Tên yêu cầu từ 4 - 20 ký tự');
            return;
        }
        dispatch(resetFormUser());
        dispatch(changeName({ name: name.trim() }));
        userAuth.userDetails.name = name.trim();
        clearFormName();
    };

    const keyEnterSubmitName = (e) => {
        if (e.which === 13 && !e.shiftKey) {
            handleSubmitName();
        }
    };

    const handleChange = (e, type, pattern) => {
        if (type === 'changeName') {
            setName(e);
        } else if (type === 'changeNickname') {
            setNickName(e);
        }
        let check = pattern.test(e.trim());
        if (check === true && error === true) {
            setError(false);
        } else if (check !== true && error === false) {
            setError(true);
        }
    };

    const handleChangePassword = (e, type, pattern) => {
        if (type === 'changeOldPassword') {
            setPassword({
                ...password,
                oldPassword: e,
            });
            if (errorSamePass === true) {
                setErrorSamePass(false);
            }
        } else if (type === 'changeNewPassword') {
            setPassword({
                ...password,
                newPassword: e,
            });
            if (e === password.reNewPassword) {
                setErrorEqualNewPass(false);
            } else {
                setErrorEqualNewPass(true);
            }
            let check = pattern.test(e.trim());
            if (check === true && errorNewPass === true) {
                setErrorNewPass(false);
            } else if (check !== true && errorNewPass === false) {
                setErrorNewPass(true);
            }
        } else if (type === 'changeReNewPassword') {
            setPassword({
                ...password,
                reNewPassword: e,
            });
            if (e === password.newPassword) {
                setErrorEqualNewPass(false);
            } else {
                setErrorEqualNewPass(true);
            }
            let check = pattern.test(e.trim());
            if (check === true && errorReNewPass === true) {
                setErrorReNewPass(false);
            } else if (check !== true && errorReNewPass === false) {
                setErrorReNewPass(true);
            }
        }
    };
    const handleChangeNickName = () => {
        setSubmitEdit(true);
        if (error === true) {
            setMessage('Nickname yêu cầu từ 4 - 20 ký tự');
            return;
        }
        dispatch(resetFormUser());
        dispatch(changeNickName({ nickName: '@' + nickName.trim() }));
        userAuth.userDetails.nickName = '@' + nickName.trim();
        clearFormNickName();
    };

    const handleChangePassWord = () => {
        setSubmitEdit(true);

        if (password.newPassword.trim() === '') {
            setErrorNewPass(true);
            setMessageStrongPass('Mật khẩu không đủ mạnh!');
        }

        if (password.reNewPassword.trim() === '') {
            setErrorReNewPass(true);
            setMessageStrongPass('Mật khẩu không đủ mạnh!');
        }

        if (userAuth?.passwordDecrypt === password.oldPassword) {
            setErrorSamePass(false);
        } else {
            setErrorSamePass(true);
            setMessage('Mật khẩu cũ của bạn không đúng!');
            return;
        }

        if (errorEqualNewPass === true) {
            setMessageRepass('Mật khẩu không khớp!');
        }

        if (errorNewPass === true || errorReNewPass === true) {
            setMessageStrongPass('Mật khẩu không đủ mạnh!');
        }

        if (
            errorEqualNewPass === true ||
            errorNewPass === true ||
            errorReNewPass === true ||
            password.reNewPassword.trim() === '' ||
            password.newPassword.trim() === ''
        ) {
            return;
        }
        objChangePassword.oldPassword = password.oldPassword;
        objChangePassword.newPassword = password.newPassword;
        objChangePassword.renewPassword = password.reNewPassword;
        dispatch(changePassword(objChangePassword));
        clearFormPassword();
    };

    const handleDeleteNotification = (data) => {
        notifications = getNotifications.filter((val) => val.id !== data);
        // setNotifications(_notifications);
        dispatch(deleteNotification(data));
        // dispatch(getListNotification(page));
    };

    const handleAcceptFriendRequest = (data, id) => {
        let index = -1;
        for (let i = 0; i < notifications.length; i++) {
            const element = notifications[i];
            if (element.id === id) {
                index = i;
                break;
            }
        }
        if (index !== -1) {
            let _notifications = [...notifications];
            let _noti = _notifications[index];
            let senderName = _noti.content.split('đã gửi cho bạn lời mời kết bạn')[0];

            _noti.content = 'Bạn và ' + senderName + ' đã trở thành bạn bè!';
            _noti.status = true;
            _notifications[index] = _noti;
            // setNotifications(_notifications);
        }
        dispatch(
            acceptFriendRequest({
                id: data,
                action: 'accept',
            }),
        );
        // dispatch(getListNotification(page));
    };

    const handleDenyFriendRequest = (data, id) => {
        // notifications = notifications.filter((val) => val.id !== id);
        let index = -1;
        for (let i = 0; i < notifications.length; i++) {
            const element = notifications[i];
            if (element.id === id) {
                index = i;
                break;
            }
        }
        if (index !== -1) {
            let _notifications = [...notifications];
            let _noti = _notifications[index];
            _noti.content = 'Đã xóa lời mời!';
            _noti.status = true;
            _notifications[index] = _noti;
            // setNotifications(_notifications);
        }
        dispatch(
            denyFriendRequest({
                id: data,
                action: 'deny',
            }),
        );
        // dispatch(getListNotification(page));
    };
    const onClickForgot = () => {
        setInfo(true);
        dispatch(forgotPassword({ email: userAuth.userDetails.mail }, history));
    };

    const cancelHandleChangeName = () => {
        clearFormName();
    };

    const cancelHandleChangeNickName = () => {
        clearFormNickName();
    };

    const cancelHandleChangePassWord = () => {
        clearFormPassword();
    };

    const clearFormPassword = () => {
        setAction({
            ...action,
            changePassWord: 'notChange',
            changeName: 'notChange',
            changeNickName: 'notChange',
        });
        password.newPassword = '';
        password.oldPassword = '';
        password.reNewPassword = '';
        setSubmitEdit(false);
        setError(false);
        setMessage('');
    };

    const clearFormNickName = () => {
        setAction({
            ...action,
            changePassWord: 'notChange',
            changeName: 'notChange',
            changeNickName: 'notChange',
        });
        setNickName('');
        setSubmitEdit(false);
        setError(false);
        setMessage('');
    };

    const clearFormName = () => {
        setAction({
            ...action,
            changePassWord: 'notChange',
            changeName: 'notChange',
            changeNickName: 'notChange',
        });
        setName('');
        setSubmitEdit(false);
        setError(false);
        setMessage('');
    };

    // notifications = getNotifications.item ? getNotifications.item : [];
    // useEffect(() => {
    //     if (getNotifications.item) {
    //         setNotifications(getNotifications.item);
    //     }
    // }, [getNotifications]);

    notifications = getNotifications.item ? getNotifications.item : [];
    // layout children
    const layoutChangeName = (
        <li className="user-item">
            <div className="link d-flex justify-content-between">
                <div className="custom-btn ml-1 bg-dark" onClick={() => cancelHandleChangeName()}>
                    <UilTimes />
                </div>

                <div className="custom-btn mr-3" onClick={() => handleSubmitName()}>
                    <UilCheck />
                </div>
            </div>

            <div className="change-nickName">
                <div className="form-group d-flex col-md-12 pl-3 pt-1 custom-prime p-float-label mt-3 mb-0">
                    <InputText
                        className={
                            submitEdit === true && error === true
                                ? 'form-control p-4 rounded-pill p-invalid block'
                                : 'form-control p-4 rounded-pill'
                        }
                        value={name}
                        type="text"
                        required
                        minLength={4}
                        maxLength={20}
                        autoFocus={true}
                        keyfilter={patternName}
                        onChange={(e) => handleChange(e.target.value, 'changeName', patternNameRange)}
                        onKeyPress={(e) => keyEnterSubmitName(e)}
                    />
                    <label htmlFor="inputtext" className="pl-3 small">
                        Nhập tên
                    </label>
                </div>
                {submitEdit === true && error === true ? <small className="p-error block pl-4">{message}</small> : null}
            </div>
        </li>
    );

    const layoutChangeNickname = (
        <li className="user-item">
            <div className="link d-flex justify-content-between">
                <div className="custom-btn ml-1 bg-dark" onClick={() => cancelHandleChangeNickName()}>
                    <UilTimes />
                </div>

                <div className="custom-btn mr-3" onClick={() => handleChangeNickName()}>
                    <UilCheck />
                </div>
            </div>

            <div className="change-nickName">
                <div className="form-group d-flex col-md-12 pl-3 pt-1 mb-0 custom-prime p-float-label mt-3">
                    <InputText
                        className={
                            submitEdit === true && error === true
                                ? 'form-control p-4 rounded-pill p-invalid block'
                                : 'form-control p-4 rounded-pill'
                        }
                        value={nickName}
                        type="text"
                        required
                        minLength={4}
                        maxLength={20}
                        keyfilter={/^[a-zA-Z0-9]$/}
                        onChange={(e) => handleChange(e.target.value, 'changeNickname', patternNicknameRange)}
                    />
                    <label htmlFor="inputtext" className="pl-3 small">
                        Nhập nickname
                    </label>
                </div>
                {submitEdit === true && error === true ? <small className="p-error block pl-4">{message}</small> : null}
            </div>
        </li>
    );
    const header = <h6>Mật khẩu của bạn</h6>;
    const footer = (
        <React.Fragment>
            <Divider />
            <p className="mt-2">Yêu cầu</p>
            <ul className="pl-2 ml-2 mt-0" style={{ lineHeight: '1.5' }}>
                <li>Ít nhất một ký tự thường</li>
                <li>Ít nhất một ký tự hoa</li>
                <li>Ít nhất một ký tự số</li>
                <li>Ít nhất 8 ký tự</li>
            </ul>
        </React.Fragment>
    );
    const layoutChangePassword = (
        <li className="user-item">
            <div className="link d-flex justify-content-between">
                <div className="custom-btn ml-1 bg-dark" onClick={() => cancelHandleChangePassWord()}>
                    <UilTimes />
                </div>
                <div className="custom-btn mr-3" onClick={() => handleChangePassWord()}>
                    <UilCheck />
                </div>
            </div>
            <div className="change-password">
                <div>
                    <div className="form-group d-flex col-md-12 pl-3 pt-1 custom-prime p-float-label">
                        <Password
                            className="form-control pb-5 rounded-pill"
                            type={'password'}
                            autoFocus
                            value={password.oldPassword}
                            minLength={1}
                            maxLength={50}
                            // keyfilter={patternPassword}
                            onChange={(e) => handleChangePassword(e.target.value, 'changeOldPassword', '')}
                            toggleMask
                            feedback={false}
                        />
                        <label htmlFor="inputtext" className="small" style={{ padding: '7px 25px' }}>
                            Nhập mật khẩu
                        </label>
                    </div>
                    {submitEdit === true && errorSamePass === true ? (
                        <small className="p-error block pl-5">{message}</small>
                    ) : null}
                </div>
                <div>
                    <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 custom-prime p-float-label mt-4">
                        <Password
                            className="form-control pb-5 rounded-pill"
                            type={'password'}
                            value={password.newPassword}
                            minLength={8}
                            maxLength={50}
                            // keyfilter={patternPassword}
                            onChange={(e) =>
                                handleChangePassword(e.target.value, 'changeNewPassword', patternPasswordRange)
                            }
                            toggleMask
                            header={header}
                            footer={footer}
                        />
                        <label htmlFor="inputtext" className="small" style={{ padding: '7px 25px' }}>
                            Nhập mật khẩu mới
                        </label>
                    </div>
                    {submitEdit === true && errorNewPass === true ? (
                        <small className="p-error block pl-5">{messageStrongPass}</small>
                    ) : null}
                </div>
                <div>
                    <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 custom-prime p-float-label mt-4">
                        <Password
                            className="form-control pb-5 rounded-pill"
                            type={'password'}
                            value={password.reNewPassword}
                            minLength={8}
                            maxLength={50}
                            // keyfilter={patternPassword}
                            onChange={(e) =>
                                handleChangePassword(e.target.value, 'changeReNewPassword', patternPasswordRange)
                            }
                            toggleMask
                            header={header}
                            footer={footer}
                        />
                        <label htmlFor="inputtext" className="small" style={{ padding: '7px 25px' }}>
                            Xác nhận mật khẩu mới
                        </label>
                    </div>
                    <div>
                        {submitEdit === true && errorReNewPass === true ? (
                            <div className="p-error block pl-5" style={{ fontSize: '0.875em' }}>
                                {messageStrongPass}
                            </div>
                        ) : null}
                        {submitEdit === true && errorEqualNewPass === true ? (
                            <div className="p-error block pl-5" style={{ fontSize: '0.875em' }}>
                                {messageRepass}
                            </div>
                        ) : null}
                    </div>
                </div>
                <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 mt-4">
                    <span className="pl-3">
                        Quên mật khẩu?{' '}
                        <span
                            style={{
                                color: 'blue',
                                textDecoration: 'underline',
                                cursor: 'pointer',
                            }}
                            onClick={() => onClickForgot()}
                        >
                            Lấy lại ngay!
                        </span>
                    </span>
                </div>
                {info === true && (
                    <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 mb-5">
                        <small className="p-error">Mật khẩu mới đã được gửi vào hòm thư của bạn</small>
                    </div>
                )}
            </div>
        </li>
    );

    const layoutAction = (
        <React.Fragment>
            <li className="user-item">
                <div className="link">
                    Tên: {userAuth?.userDetails?.name}
                    <span className="wrap-action">
                        <span
                            className="border-left pl-3 pb-2"
                            style={{ color: '#407fbe', cursor: 'pointer' }}
                            onClick={() =>
                                setAction({
                                    ...action,
                                    changeName: 'change',
                                })
                            }
                        >
                            <UilPen size={20} />
                        </span>
                    </span>
                </div>
            </li>
            <li className="user-item">
                <div className="link">
                    NickName: {userAuth?.userDetails?.nickName}
                    <span className="wrap-action">
                        <span
                            className="border-left pl-3 pb-2"
                            style={{ color: '#407fbe', cursor: 'pointer' }}
                            onClick={() =>
                                setAction({
                                    ...action,
                                    changeNickName: 'change',
                                })
                            }
                        >
                            <UilPen size={20} />
                        </span>
                    </span>
                </div>
            </li>

            <li className="user-item">
                <div className="link">
                    Mật Khẩu:
                    <span className="mt-2 ml-0">**************</span>
                    <span className="wrap-action">
                        <span
                            className="border-left pl-3 pb-2"
                            style={{ color: '#407fbe', cursor: 'pointer' }}
                            onClick={() =>
                                setAction({
                                    ...action,
                                    changePassWord: 'change',
                                })
                            }
                        >
                            <UilPen size={20} />
                        </span>
                    </span>
                </div>
            </li>
        </React.Fragment>
    );
    const layoutNotification = (
        <React.Fragment>
            {notifications.map((data, index) => (
                <li className="content-item" key={index}>
                    <div className="item-info">
                        <div className="item-image">
                            {data.typeId === 1 && (
                                <img
                                    src={data.picture ? data.picture : require('../../../assets/image/user.png')}
                                    alt=""
                                />
                            )}
                            {data.typeId === 2 && (
                                <img
                                    src={data.picture ? data.picture : require('../../../assets/image/user.png')}
                                    alt="Hình ảnh"
                                />
                            )}
                            {data.typeId === 3 && (
                                <img src={require('../../../assets/image/group.png')} alt="Hình ảnh" />
                            )}
                            {data.typeId === 4 && (
                                <img
                                    src={data.picture ? data.picture : require('../../../assets/image/user.png')}
                                    alt="Hình ảnh"
                                />
                            )}
                        </div>
                        <div className="item-info-detail">
                            {data.typeId === 2 && (
                                <span className="item-description">{data.content.split('|||')[0]}</span>
                            )}
                            {(data.typeId === 1 || data.typeId === 3) && (
                                <span className="item-description">{data.content}</span>
                            )}
                            {data.typeId === 4 && (
                                <span className="item-description">
                                    <Link to={'/chat'} style={{ color: 'black' }}>
                                        {data.content}
                                    </Link>
                                </span>
                            )}

                            <span className="item-time">
                                {getTimeWithString(new Date().getTime() - new Date(data.timeCreated))}
                            </span>
                        </div>
                    </div>
                    {data.typeId === 1 && !data.status && (
                        <div className="item-invite d-flex">
                            <button
                                className="btn btn-primary mr-2 btn-custom"
                                onClick={() => handleAcceptFriendRequest(data.senderId, data.id)}
                            >
                                Xác nhận
                            </button>
                            <button
                                className="btn btn-secondary btn-custom"
                                onClick={() => handleDenyFriendRequest(data.senderId, data.id)}
                            >
                                Xoá
                            </button>
                        </div>
                    )}
                    {data.typeId === 2 && (
                        <div className="item-invite d-flex">
                            <button
                                className="btn btn-primary mr-2 btn-custom"
                                onClick={() => openJoinRoomDialog(data.content.split('|||')[1])}
                            >
                                Tham gia
                            </button>
                            <button
                                className="btn btn-secondary btn-custom"
                                onClick={() => handleDeleteNotification(data.id)}
                            >
                                Xoá
                            </button>
                        </div>
                    )}
                </li>
            ))}
        </React.Fragment>
    );

    const [joinRoom, setJoinRoom] = useState(false);
    const [infos, setInfos] = useState(false);
    const [singleRoom, setRoom] = useState({});
    const [errMsg, setErrMsg] = useState({});

    const room = useSelector((state) => state.roomDataNoti.room);

    useEffect(() => {
        setRoom(room);
    }, [room]);

    const hideJoinRoomDialog = () => {
        setJoinRoom(false);
    };

    const openJoinRoomDialog = (roomId) => {
        // get room detail -- to do
        dispatch(getRoomsForChatSuccess(roomId?.trim()));
        setJoinRoom(true);
    };

    const hideInfoDialog = () => {
        setInfos(false);
    };

    const openInfoDialog = () => {
        setInfos(true);
    };

    const acceptJoinRoom = () => {
        hideJoinRoomDialog();
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

    const joinRoomDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" className="p-button-text" onClick={hideJoinRoomDialog} />
            {singleRoom && singleRoom !== null && singleRoom !== undefined && (
                <Link
                    to={{ pathname: '/meet', state: { singleRoom } }}
                    style={{
                        padding: '0.75rem 1.25rem',
                        fontSize: '1rem',
                        color: '#6366F1',
                        fontWeight: '650',
                        display: 'inline-flex',
                    }}
                >
                    Xác nhận
                </Link>
            )}
            {(!singleRoom ||
                singleRoom === undefined ||
                singleRoom === null ||
                singleRoom.countUserOnline === singleRoom.roomSize ||
                singleRoom.isLock) && <Button label="Xác nhận" className="p-button-text" onClick={acceptJoinRoom} />}
        </React.Fragment>
    );

    const infoDialogFooter = (
        <React.Fragment>
            <Button label="OK" icon="pi pi-check" className="p-button-text" onClick={hideInfoDialog} />
        </React.Fragment>
    );

    return (
        <React.Fragment>
            <Toast ref={toast} />
            <div className="header__action">
                <div className="notify">
                    <Button
                        onClick={() => openNotify()}
                        icon="pi pi-bell"
                        className="p-button-rounded p-button-warning p-button-outlined"
                        aria-label="Notification"
                    />
                    {allDeliveredNotifs.length !== 0 ? (
                        <span className="badge rounded-circle">{allDeliveredNotifs.length}</span>
                    ) : null}
                    {showNotify !== 0 ? (
                        <div
                            // style={
                            //     open.openNotify === false
                            //         ? { display: "none" }
                            //         : { display: "block" }
                            // }
                            ref={actionNotifyRef}
                            className="notify__content"
                            onScroll={handleScroll}
                        >
                            <header className="content-header separate-bottom">Thông báo</header>
                            <ul className="content-list-option h-50">{layoutNotification}</ul>
                        </div>
                    ) : null}
                </div>
                <div className="user d-flex align-items-center">
                    <div className="logo item-image" onClick={() => openUser()}>
                        {userAuth && (
                            <img
                                src={
                                    userAuth?.userDetails?.picture === null
                                        ? dfPicture
                                        : userAuth?.userDetails?.picture || require('../../../assets/image/user.png')
                                }
                                alt="Ảnh đại diện"
                            />
                        )}
                    </div>
                    {showUser !== 0 ? (
                        <ul
                            ref={actionUserRef}
                            // style={
                            //     open.openUser === false
                            //         ? { display: "none" }
                            //         : { display: "block" }
                            // }
                            className="user__option-list"
                        >
                            {action.changePassWord === 'change'
                                ? layoutChangePassword
                                : action.changeNickName === 'change'
                                    ? layoutChangeNickname
                                    : action.changeName === 'change'
                                        ? layoutChangeName
                                        : layoutAction}
                        </ul>
                    ) : null}
                </div>
            </div>

            <Dialog
                visible={joinRoom}
                style={{ width: '450px', borderRadius: '0' }}
                dismissableMask
                header="Xác nhận"
                modal
                footer={joinRoomDialogFooter}
                onHide={hideJoinRoomDialog}
                contentStyle={{
                    fontSize: '1.2rem',
                    fontWeight: '500',
                }}
            >
                <div
                    className="confirmation-content"
                    style={{ fontFamily: 'Helvetica', fontSize: '1.2rem', paddingTop: '1.5rem' }}
                >
                    <span style={{ fontSize: '1.4rem' }}>
                        {singleRoom !== null ? (
                            <strong style={{ fontSize: '1.7rem' }}>{singleRoom.roomName}</strong>
                        ) : (
                            ' trong liên kết trên'
                        )}
                        . <br />
                        <br />
                        <label>Bạn có chắc chắn?</label>
                    </span>
                </div>
            </Dialog>

            <Dialog
                visible={infos}
                style={{ width: '450px', borderRadius: '0' }}
                dismissableMask
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

export default HeaderAction;
