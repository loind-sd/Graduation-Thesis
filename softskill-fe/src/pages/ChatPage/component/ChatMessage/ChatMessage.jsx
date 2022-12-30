import React, { useState } from 'react';
import AccountPreview from '../AccountPreview/AccountPreview';

import Tippy from '@tippyjs/react/headless';
import { Button } from 'primereact';
import imgDefault from './../../../../assets/image/user.png';
import './ChatMessage.scss';

function ChatMessage({ message, deleteMsg, currentChat, addFriend, joinRoom }) {
    const renderPreview = () => {
        return (
            <div tabIndex="-1">
                <AccountPreview message={message} addFriend={addFriend} />
            </div>
        );
    };

    const joinRooms = () => {
        joinRoom(message.content.split('|')[1]);
    }

    return (
        <div>
            <div className="messge-group-base">
                <div className="messge-group">
                    <div className="message-base">
                        <div className="message-header">
                            <div>
                                <Tippy
                                    interactive
                                    delay={[800, 0]}
                                    offset={[-20, 0]}
                                    placement="bottom"
                                    render={renderPreview}
                                    hideOnClick="false"
                                >
                                    <span className="sender-info">
                                        {/* <img
                                            className="user-avatar"
                                            src={message.createdPicture}
                                            // src="https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000"
                                            alt=""
                                        /> */}
                                        <img
                                            className="user-avatar"
                                            src={message.createdPicture === null ? imgDefault : message.createdPicture}
                                            onError={(e) =>
                                                (e.target.src =
                                                    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                            }
                                            alt="img"
                                        />
                                        <span className="user-name">{message.createdName}</span>
                                    </span>
                                </Tippy>
                            </div>
                            <time className="sender-time" dateTime={message.createdTime}>
                                {message.createdTime.split(',')[0]}
                            </time>
                        </div>

                        {/* <div className="message-content">
                            {edit === true ? (
                                <input
                                    type={'text'}
                                    value={msg}
                                    onChange={(e) => setMsg(e.target.value)}
                                    onBlur={() => alert(11)}
                                />
                            ) : (
                                <span className="chat-message">{message.content}</span>
                            )}
                        </div> */}
                        <div className="message-content">
                            {currentChat.id !== 2 && <div className="chat-message">{message.content}</div>}
                            {currentChat.id === 2 && (
                                <div className="chat-message">
                                    {message.content.split('|')[0]}
                                    <br />
                                    {/* <a href={message.content.split('|')[1]} target="_blank" rel="noreferrer">
                                        {message.content.split('|')[1]}
                                    </a> */}
                                    <Button
                                        // icon="pi pi-check"
                                        className={`p-button-text mr-1`}
                                        style={{
                                            fontSize: '1.3rem',
                                            paddingLeft: 0}}
                                        onClick={joinRooms}
                                    >Tham gia ngay</Button>
                                </div>
                            )}
                            <div className="time-send">
                                {message.createdTime.split(',')[1]} {message.isFix}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ChatMessage;
