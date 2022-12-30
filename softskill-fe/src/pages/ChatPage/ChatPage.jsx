import React, { useState, useEffect, useRef } from 'react';
import MenuBar from './../../components/MenuBar/MenuBar';
import Header from './../../components/Header/Header';
import Contacts from './component/Contact/Contact';
import ChatContainer from './component/ChatContainer/ChatContainer';
import './ChatPage.scss';
import ChatMessage from './component/ChatMessage/ChatMessage';
import MyMessage from './component/MyMessage/MyMessage';
import { Button } from 'primereact';
import { Dialog } from 'primereact/dialog';
import ChatInput from './component/ChatInput/ChatInput';
import loading from '../../assets/output-onlinegiftools.gif';
import { socket } from './../App/App';
import { useDispatch, useSelector } from 'react-redux';
import {
    getAllContacts,
    addNewContacts,
    outRoom,
    updateChat,
    kickMember,
    addMembers,
} from '../../redux/thunks/chat-thunks';
import { getWorldChat, deleteWorldChat, sendWorldChat } from './../../redux/thunks/chat-thunks';
import { addFriend, getFriendListEvenRequest } from '../../redux/thunks/friend-thunks';
import { useHistory } from 'react-router';
import { Link } from 'react-router-dom';
import { getRoomsForChat } from '../../redux/thunks/room-active-thunks';

function ChatPage() {
    const scrollRef = useRef();
    const dispatch = useDispatch();
    const history = useHistory();
    const [messages, setMessage] = useState([]);
    const [contacts, setContacts] = useState([]);
    const [newContactMember, setNewContactMember] = useState([]);

    const [arrivalMessage, setArrivalMessage] = useState(null);
    const [id, setId] = useState(0);

    const [currentChat, setCurrentChat] = useState({
        id: 1,
        name: '#General',
        roomCode: 'general',
        haveNew: false,
        master: false,
    });

    useEffect(() => {
        setMessage([]);
        dispatch(getWorldChat({ limit: 20, channelId: currentChat.id }));
    }, [currentChat]);

    useEffect(() => {
        // setTimeout(() => {
        dispatch(getAllContacts());
        // dispatch(getFriendList());
        // if (!userAuth.userDetails) {
        //     history.push('/login');
        // }
        // }, 1500);
        socket.emit('join-room-chat', { room: currentChat.roomCode });
    }, []);

    const chats = useSelector((state) => {
        return state.chatsData.chats;
    });
    const msgType = useSelector((state) => {
        return state.chatsData.msgType;
    });
    const userAuth = useSelector((state) => state.authData.user);

    // let error = useSelector((state) => state.chatsData.error);

    // useEffect(() => {
    //     if (error) {
    //         console.log(error);
    //         // history.push('/login');
    //     }
    // }, [error]);

    let contactInit = useSelector((state) => state.chatsData.contacts);
    let contactType = useSelector((state) => state.chatsData.contactType);
    useEffect(() => {
        dispatch(getFriendListEvenRequest());
    }, []);
    let friendInit = useSelector((state) => {
        // console.log(state.friends);
        return state.friends.friend.item;
    });

    useEffect(() => {
        if (contactInit) {
            if (contactType === 'load') {
                setContacts(contactInit);
                setCurrentChat(contactInit[0]);
                // for (let index = 0; index < contactInit.length; index++) {
                //     const element = contactInit[index];
                //     socket.emit('join-room-chat', { room: element.roomCode });
                // }
            } else if (contactType === 'add') {
                socket.emit('addNewContact', {
                    newContact: contactInit,
                    member: newContactMember,
                });
                let _contact = [...contacts];
                contactInit.master = true;
                _contact.push(contactInit);

                setContacts(_contact);
            }
        }
    }, [contactInit, contactType]);

    // useEffect(() => {
    //     if (friendInit) {
    //         setFriend(friendInit);
    //     }
    // }, [friendInit]);

    const handleChatChange = (chat) => {
        setCurrentChat(chat);
        socket.emit('join-room-chat', { room: chat.roomCode });

        // reset mini noti
        let indexs = -1;
        for (let index = 0; index < contacts.length; index++) {
            const element = contacts[index];
            if (element.id === chat.id) {
                indexs = index;
                break;
            }
        }
        if (indexs !== -1) {
            let _contacts = [...contacts];
            let _contact = { ...chat };
            _contact.haveNew = false;
            _contacts[indexs] = _contact;
            setContacts(_contacts);
        }
        // $('.messge-group-base-field').hide('medium').show('slow');
    };

    const updateHandle = (id, content) => {
        dispatch(
            updateChat({
                id: id,
                content: content,
            }),
        );
    };

    const handleAddNewcontact = (newContact) => {
        let _contact = {
            name: newContact.name,
            member: newContact.member,
        };
        setNewContactMember(newContact.member);
        dispatch(addNewContacts(_contact));
    };

    const handleOutRoom = (roomId, roomCode) => {
        if (currentChat.id === roomId) {
            setCurrentChat({
                id: 1,
                name: '#General',
                roomCode: 'general',
                haveNew: false,
                master: false,
            });
        }
        let _contacts = contacts.filter((val) => val.id !== roomId);
        try {
            contactInit = contactInit.filter((val) => val.id !== roomId);
        } catch (error) { }
        setContacts(_contacts);
        dispatch(
            outRoom({
                chatChannelId: roomId,
            }),
        );
        socket.emit('out-room', {
            roomCode: roomCode,
        });
    };

    const kickUsersHandle = (roomId, roomCode, userId) => {
        // dispatch(
        //     kickMember({
        //         roomId: roomId,
        //         userId: userId,
        //     }),
        // );
        socket.emit('kick-user', {
            userId: userId,
            roomId: roomId,
            roomCode: roomCode,
        });
    };

    const addMemberToChatHandle = (ids, idAction) => {
        dispatch(
            addMembers({
                member: ids,
                roomId: idAction.id,
                name: idAction.name,
            }),
        );
        socket.emit('add-members', {
            member: ids,
            contact: idAction,
        });
    };

    useEffect(() => {
        if (msgType === 'loadFirst') {
            setMessage(chats);
        } else if (msgType === 'send') {
            setArrivalMessage(chats);
            let _currentChat = { ...currentChat };
            _currentChat.haveNew = true;
            socket.emit('sendMessage', {
                chats: chats,
                currentChat: _currentChat,
            });
            socket.emit('send-mini-noti', {
                currentChat: _currentChat,
            });
        } else if (msgType === 'loadMore') {
            // setMessage((oldMsg) => [...chats, ...oldMsg]);
            messages.push(chats);
        } else if (msgType === 'delete') {
            const arr = messages.filter((item) => item.id !== chats.id);
            setMessage(arr);
            socket.emit('deleteMessage', {
                id: chats.id,
                currentChat: currentChat,
            });
        } else if (msgType === 'update') {
            let _messages = [...messages];
            let index = findIndexById(chats.id);

            _messages[index] = chats;
            setMessage(_messages);

            socket.emit('updateMessage', {
                chats: chats,
                currentChat: currentChat,
            });
        }
    }, [chats]);

    useEffect(() => {
        if (socket) {
            socket.once('msg-recieve', (data) => {
                let _chats = data.chats;
                if (findFriendIndex(_chats.createdBy) !== -1) {
                    _chats.status = 'isFriend';
                } else {
                    _chats.status = 'nothing';
                }
                setArrivalMessage(_chats);
            });

            socket.once('mini-noti', (contact) => {
                let indexs = -1;
                for (let index = 0; index < contacts.length; index++) {
                    const element = contacts[index];
                    if (element.id === contact.id) {
                        indexs = index;
                        break;
                    }
                }
                if (indexs !== -1) {
                    let _contacts = [...contacts];
                    _contacts[indexs] = contact;
                    setContacts(_contacts);
                }
            });

            socket.on('msg-recieve-delete', (id) => {
                setId(id);
            });

            socket.once('msg-recieve-update', (editChat) => {
                let _messages = [...messages];

                let index = findIndexById(editChat.id);
                // let index = -1;
                // for (let i = 0; i < _messages.length; i++) {
                //     if (_messages[i].id === id) {
                //         index = i;
                //         break;
                //     }
                // }

                // console.log('editchat');
                // console.log(editChat);

                _messages[index] = editChat;
                setMessage(_messages);
            });

            socket.on('contact-receive', (newContact) => {
                let _contact = [...contacts];
                _contact.push(newContact);

                setContacts(_contact);
            });

            socket.on('out-room-receive', (data) => {
                // let _contacts = contacts.filter((item) => item.id !== data.roomId);
                // setContacts(_contacts);
                // // if (currentChat.id === data.roomId) {
                // //     setCurrentChat({
                // //         id: 1,
                // //         name: '#General',
                // //         roomCode: 'general',
                // //         haveNew: false,
                // //         master: false,
                // //     });
                // // }
                // socket.emit('out-room', {
                //     roomCode: data.roomCode,
                // });
                // let _contacts = contacts.filter((item) => item.id !== data.roomId);
                handleOutRoom(data.roomId, data.roomCode);
            });

            socket.on('add-member-receive', (data) => {
                let _newContact = {
                    id: data.id,
                    name: data.name,
                    roomCode: data.roomCode,
                    master: false,
                    haveNew: false,
                };

                let _contacts = [...contacts];
                _contacts.push(_newContact);
                setContacts(_contacts);
                socket.emit('join-room-chat', {
                    room: data.roomCode,
                });
            });
        }
    }, [contacts, messages, handleOutRoom]);

    useEffect(() => {
        arrivalMessage && setMessage((prev) => [...prev, arrivalMessage]);
    }, [arrivalMessage]);

    useEffect(() => {
        if (id) {
            const arr = messages.filter((item) => item.id !== id);
            setMessage(arr);
        }
    }, [id]);

    useEffect(() => {
        scrollRef.current?.scrollIntoView({ behavior: 'smooth' });
    }, [messages]);

    const process = (data) => {

        if (data.roomId === currentChat.id) {
            setCurrentChat({
                id: 1,
                name: '#General',
                roomCode: 'general',
                haveNew: false,
                master: false,
            });
        }
    };

    const handleScroll = (e) => {
        if (e.target.scrollTop === 0) {
            setTimeout(() => {
                if (e.target.scrollTop === 0) {
                    document.getElementsByClassName('loading-base')[0].style.display = 'block';
                    getMoreData();
                    setTimeout(() => {
                        document.getElementsByClassName('loading-base')[0].style.display = 'none';
                    }, 1500);
                }
            }, 1000);
        }
    };

    const getMoreData = () => {
        const messageId = messages[0] === undefined ? null : messages[0].id;
        const body = {
            limit: 20,
            msgId: messageId,
            offset: 0,
            channelId: currentChat.id,
        };
        dispatch(getWorldChat(body));
    };

    const sendMsg = (msg) => {
        dispatch(sendWorldChat({ content: msg, channelId: currentChat.id }));
    };

    const remove = (i) => {
        // const arr = messages.filter((item) => item.id !== i);
        // setMessage(arr);
        dispatch(deleteWorldChat(i));
    };

    const addFriendHandle = (id, msgId) => {
        dispatch(
            addFriend({
                id: id,
            }),
        );
        let _messages = [...messages];

        for (let i = 0; i < _messages.length; i++) {
            const element = _messages[i];
            if (element.createdBy === id) {
                _messages[i].status = 'isFriend';
            }
        }

        // let index = findIndexById(msgId);

        // _messages[index].status = 'isFriend';
        setMessage(_messages);
        let nf = {
            id: id,
            name: msgId.createdName,
            nickName: msgId.createdNickName,
            picture: msgId.createdPicture,
            status: null,
        };
        friendInit.push(nf);
    };

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < messages.length; i++) {
            if (messages[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    function findFriendIndex(id) {
        let index = -1;

        if (friendInit) {
            for (let i = 0; i < friendInit.length; i++) {
                if (friendInit[i].id === id) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    const [joinRoom, setJoinRoom] = useState(false);
    const [info, setInfo] = useState(false);
    const [singleRoom, setRoom] = useState({});
    const [errMsg, setErrMsg] = useState({});

    const rooms = useSelector((state) => state.roomDataNoti.room);

    useEffect(() => {
        setRoom(rooms);
    }, [rooms]);

    const hideJoinRoomDialog = () => {
        setJoinRoom(false);
    };

    const openJoinRoomDialog = (roomId) => {
        // get room detail -- to do
        dispatch(getRoomsForChat(roomId.trim()));

        setJoinRoom(true);
    };

    const hideInfoDialog = () => {
        setInfo(false);
    };

    const openInfoDialog = () => {
        setInfo(true);
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
            <MenuBar activeMenu="message" />
            <div className="chat">
                <Header />
                <div className="chat-content">
                    <div className="chat-container">
                        {/* <Scrollbars style={{ width: '100%', height: '100%', backgroundColor: 'rgb(176 78 78 / 20%);' }}> */}
                        <div className="chat-messages" onScroll={handleScroll}>
                            <div className="loading-base">
                                <img src={loading} alt=""></img>
                            </div>
                            {messages.map((message, index) => {
                                let check = false;

                                if (index === 0 || index === message.length) {
                                } else {
                                    if (
                                        messages[index - 1].createdTime.split(',')[0] !==
                                        messages[index].createdTime.split(',')[0]
                                    ) {
                                        check = true;
                                    }
                                }
                                // console.log(check);
                                return (
                                    // <div ref={scrollRef} key={uuidv4()}>
                                    //     <div className={`message ${message.fromSelf ? 'sended' : 'recieved'}`}>
                                    //         <div className="content ">
                                    //             <p>{message.message}</p>
                                    //         </div>
                                    //     </div>
                                    // </div>
                                    <div ref={scrollRef} key={message.id} className="messge-group-base-field">
                                        {check === true && (
                                            <div className="babel">
                                                <div className="babel-left"></div>
                                                <div className="babel-time">{message.createdTime.split(',')[0]}</div>
                                                <div className="babel-right"></div>
                                            </div>
                                        )}
                                        {userAuth.userDetails &&
                                            userAuth.userDetails.mail &&
                                            message.createdMail === userAuth.userDetails.mail && (
                                                <div className={'message sended'}>
                                                    <MyMessage
                                                        message={message}
                                                        deleteMsg={remove}
                                                        currentChat={currentChat}
                                                        updateMsg={updateHandle}
                                                    />
                                                </div>
                                            )}
                                        {userAuth.userDetails &&
                                            userAuth.userDetails.mail &&
                                            message.createdMail !== userAuth.userDetails.mail && (
                                                <div className={'message recieved'}>
                                                    <ChatMessage
                                                        message={message}
                                                        deleteMsg={remove}
                                                        currentChat={currentChat}
                                                        addFriend={addFriendHandle}
                                                        joinRoom={openJoinRoomDialog}
                                                    />
                                                </div>
                                            )}
                                    </div>
                                );
                            })}
                        </div>
                        {/* </Scrollbars> */}
                        {/*<ChatInput handleSendMsg={handleSendMsg} /> */}
                        <ChatInput currentChat={currentChat} handleSendMsg={sendMsg} />
                    </div>
                    <Contacts
                        contacts={contacts}
                        changeChat={handleChatChange}
                        addNewcontact={handleAddNewcontact}
                        outRoom={handleOutRoom}
                        kickUsers={kickUsersHandle}
                        addMemberToChat={addMemberToChatHandle}
                    />
                </div>
            </div>

            <Dialog
                visible={joinRoom}
                style={{ width: '450px', borderRadius: '0' }}
                dismissableMask
                draggable={false}
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
                    {/* <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} /> */}
                    <span style={{ fontSize: '1.4rem' }}>
                        Liên kết này sẽ dẫn bạn đến Phòng họp mặt{' '}
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
                visible={info}
                style={{ width: '450px', borderRadius: '0' }}
                dismissableMask
                draggable={false}
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
}

export default ChatPage;
