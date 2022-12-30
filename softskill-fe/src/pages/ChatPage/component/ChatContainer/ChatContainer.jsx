import React, { useState, useEffect, useRef } from 'react';
import ChatInput from '../ChatInput/ChatInput';
import { useDispatch, useSelector } from 'react-redux';
import './ChatContainer.scss';
import ChatMessage from '../ChatMessage/ChatMessage';
import MyMessage from '../MyMessage/MyMessage';
import loading from '../../../../assets/output-onlinegiftools.gif';
import { getWorldChat, deleteWorldChat, sendWorldChat } from './../../../../redux/thunks/chat-thunks';
// import requestService from './../../../../utils/request-service';

function ChatContainer({ currentChat, messagess, socket }) {
    const scrollRef = useRef();
    const dispatch = useDispatch();
    const [arrivalMessage, setArrivalMessage] = useState(null);
    const [messages, setMessage] = useState(messagess);
    const [id, setId] = useState(0);

    // useEffect(() => {
    //     if (currentChat.id === 1) {
    //         dispatch(getWorldChat({ limit: 10 }));
    //     }
    // }, []);

    const chats = useSelector((state) => {
        return state.chatsData.chats;
    });
    const msgType = useSelector((state) => {
        return state.chatsData.msgType;
    });

    useEffect(() => {
        if (currentChat.id === 1) {
            if (msgType === 'send') {
                const msgs = [...messages];
                msgs.push(chats);
                setMessage(msgs);
                socket.emit('sendMessage', {
                    chats: chats,
                });
            } else if (msgType === 'load' && messages.length > 0) {
                setMessage((oldMsg) => [...chats, ...oldMsg]);
            } else if (msgType === 'delete') {
                const arr = messages.filter((item) => item.id !== chats.id);
                setMessage(arr);
                socket.emit('deleteMessage', {
                    id: chats.id,
                });
            }
        }
    }, [chats, msgType]);

    useEffect(() => {
        if (socket) {
            socket.on('msg-recieve', (msg) => {
                setArrivalMessage(msg);
            });

            socket.on('msg-recieve-delete', (id) => {
                setId(id);
            });
        }
    }, []);

    useEffect(() => {
        arrivalMessage && setMessage((prev) => [...prev, arrivalMessage]);
    }, [arrivalMessage]);

    useEffect(() => {
        if (id) {
            const arr = messages.filter((item) => item.id !== id);
            setMessage(arr);
        }
    }, [id]);

    // useEffect(() => {
    //     const response = requestService.get('/world-chat?limit=10', false);
    //     response.then((res) => {
    //         setMessage(res.data.item);
    //     });
    // }, []);

    useEffect(() => {
        scrollRef.current?.scrollIntoView({ behavior: 'smooth' });
    }, [messages]);

    const handleScroll = (e) => {
        if (e.target.scrollTop === 0) {
            document.getElementsByClassName('loading-base')[0].style.display = 'block';
            getMoreData();
            setTimeout(() => {
                document.getElementsByClassName('loading-base')[0].style.display = 'none';
            }, 1500);
        }
    };

    const getMoreData = () => {
        const body = {
            limit: 10,
            msgId: messages[0].id,
            offset: 0,
        };
        dispatch(getWorldChat(body));
    };

    const sendMsg = (msg) => {
        dispatch(sendWorldChat({ content: msg }));
    };

    const remove = (i) => {
        // const arr = messages.filter((item) => item.id !== i);
        // setMessage(arr);
        dispatch(deleteWorldChat(i));
    };

    return (
        <div className="chat-container">
            {/* <div className="chat-header">
                <div className="user-details">
                    <div className="avatar">
                        <img src={`data:image/svg+xml;base64,${currentChat.avatarImage}`} alt="" />
                    </div>
                    <div className="chat-name">
                        <h3>{currentChat.name}</h3>
                    </div>
                    <div className="member">
                        <span>#3000 thanh vien</span>
                    </div>
                </div>
                <Logout />
            </div> */}
            {/* <Scrollbars style={{ width: '100%', height: '100%', backgroundColor: 'rgb(176 78 78 / 20%);' }}> */}
            <div className="chat-messages" onScroll={handleScroll}>
                <div className="loading-base">
                    <img src={loading} alt=""></img>
                </div>
                {messages.map((message, index) => {
                    // if (currentDay !== '' && message.createdTime.split(',')[0] !== currentDay) {
                    // setCurrentDay(message.createdTime.split(',')[0]);
                    let check = false;
                    // if (message.createdTime.split(',')[0] !== currentDay) {
                    //     check = true;
                    //     setCurrentDay(message.createdTime.split(',')[0]);
                    // }
                    // console.log(messages[currentDay]);

                    if (index === 0 || index === message.length) {
                    } else {
                        if (
                            messages[index - 1].createdTime.split(',')[0] !== messages[index].createdTime.split(',')[0]
                        ) {
                            check = true;
                        }
                    }
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
                            {message.createdMail === localStorage.getItem('mail') && (
                                <div className={'message sended'}>
                                    <MyMessage message={message} deleteMsg={remove} />
                                </div>
                            )}
                            {message.createdMail !== localStorage.getItem('mail') && (
                                <div className={'message recieved'}>
                                    <ChatMessage message={message} deleteMsg={remove} />
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
    );
}

export default ChatContainer;
