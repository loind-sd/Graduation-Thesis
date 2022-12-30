import React, { useState } from 'react';
import AccountPreview from '../AccountPreview/AccountPreview';
import imgDefault from './../../../../assets/image/user.png';
import { SpeedDial } from 'primereact/speeddial';
import { Dialog } from 'primereact/dialog';

import './MyMessage.scss';
import { Button } from 'primereact';
import $ from 'jquery';

function ChatMessage({ message, deleteMsg, currentChat, updateMsg }) {
    const [state, setState] = useState(false);
    const original = message.content;

    const items = [
        {
            label: 'Edit',
            icon: 'pi pi-pencil',
            command: () => {
                if (document.getElementsByTagName('input').length === 0) {
                    var text = $(`#chat-message-${message.id}`).text();
                    $(`#chat-message-${message.id}`).parent().parent().addClass('max-to-edit');
                    $(`#chat-message-${message.id}`).parent().addClass('max-to-edit-1');
                    $(`#chat-message-${message.id}`).replaceWith(
                        '<textarea' +
                            ` id='msg-${message.id}'` +
                            " class='edit-input' value='" +
                            text +
                            "' >" +
                            text +
                            '</textarea>' +
                            ` <input id='msg-id'` +
                            " type='hidden' value='" +
                            message.id +
                            "' />",
                    );
                    $(`.btnCheck-${message.id}`).show();
                    $(`.btnCancel-${message.id}`).show();
                }
            },
        },
        {
            label: 'Delete',
            icon: 'pi pi-trash',
            command: () => {
                preDeleteHandle();
            },
        },
    ];

    const returnLabelAfterUpdate = () => {
        var text = document.getElementById(`msg-${message.id}`).value;
        $(`#msg-${message.id}`).replaceWith(`<div id='chat-message-${message.id}'>` + text + '</div>');
        $(`#chat-message-${message.id}`).parent().parent().removeClass('max-to-edit');
        $(`#chat-message-${message.id}`).parent().removeClass('max-to-edit-1');
        $(`.btnCheck-${message.id}`).hide();
        $(`.btnCancel-${message.id}`).hide();
        $('#msg-id').replaceWith('<i hidden>alo</i>');
    };

    const returnLabel = () => {
        $(`#msg-${message.id}`).replaceWith(`<div id='chat-message-${message.id}'>` + original + '</div>');
        $(`#chat-message-${message.id}`).parent().parent().removeClass('max-to-edit');
        $(`#chat-message-${message.id}`).parent().removeClass('max-to-edit-1');
        $(`.btnCheck-${message.id}`).hide();
        $(`.btnCancel-${message.id}`).hide();
        $('#msg-id').replaceWith('<i hidden>alo</i>');
    };

    const cancel = () => {
        setState(false);
    };

    const deleteHandle = () => {
        deleteMsg(message.id);
        setState(false);
    };

    const updateHandle = () => {
        var content = document.getElementById(`msg-${message.id}`).value;
        updateMsg(message.id, content);
        returnLabelAfterUpdate();
    };

    const preDeleteHandle = () => {
        setState(true);
    };

    return (
        <div className="base">
            <div className="messge-group-base">
                <div className="messge-group">
                    <div className="message-base">
                        <div className="message-header">
                            <div>
                                <time className="sender-time" dateTime={message.createdTime}>
                                    {message.createdTime.split(',')[0]}
                                </time>
                                <span className="sender-info">
                                    <span className="user-name">{message.createdName}</span>
                                    <img
                                        className="user-avatar"
                                        src={message.createdPicture === null ? imgDefault : message.createdPicture}
                                        onError={(e) =>
                                            (e.target.src =
                                                'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                        }
                                        alt="img"
                                    />
                                </span>
                            </div>
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
                        <div className="more-content">
                            <div className="message-content">
                                {currentChat.id !== 2 && (
                                    <div className="chat-message">
                                        <div id={`chat-message-${message.id}`}>{message.content}</div>
                                        <div>
                                            <Button
                                                icon="pi pi-check"
                                                id="btnCheck"
                                                className={`p-button-success mr-1 btnCheck-${message.id}`}
                                                onClick={updateHandle}
                                            ></Button>
                                            <Button
                                                icon="pi pi-times"
                                                id="btnCancel"
                                                className={`p-button-danger mt-1 btnCancel-${message.id}`}
                                                onClick={returnLabel}
                                            ></Button>
                                        </div>
                                    </div>
                                )}
                                {currentChat.id === 2 && (
                                    <div className="chat-message">
                                        {message.content.split('|')[0]}
                                        {/* <br />
                                        <a href={message.content.split('|')[1]} target="_blank" rel="noreferrer">
                                            {message.content.split('|')[1]}
                                        </a> */}
                                    </div>
                                )}
                                <div className="time-send">
                                    {message.createdTime.split(',')[1]} {message.isFix}
                                </div>
                            </div>
                            {currentChat.id !== 2 && (
                                <div className="more-btn">
                                    {/* <Tippy
                                    interactive
                                    delay={[100, 100]}
                                    offset={[-20, 0]}
                                    placement="left-end"
                                    render={renderMoreBtn}
                                    hideOnClick="false"
                                > */}
                                    <div className="delete-base speeddial-tooltip-demo">
                                        {/* <button className="icon-delete">
                                            <UilEllipsisV />
                                        </button>
                                        <button
                                            className="icon-delete"
                                            style={{ visibility: 'hidden', height: '22px' }}
                                        >
                                            <UilEllipsisV />
                                        </button> */}
                                        {/* <Tooltip
                                        target=".speeddial-tooltip-demo .speeddial-right .p-speeddial-action"
                                        position="bottom"
                                        style={{ boxShadow: 'none' }}
                                    /> */}
                                        <SpeedDial
                                            model={items}
                                            direction="left"
                                            hideOnClickOutside
                                            showIcon="pi pi-ellipsis-v"
                                            style={{ position: 'inherit' }}
                                            className="speeddial-right"
                                            buttonStyle={{
                                                background: 'transparent',
                                                borderColor: 'transparent',
                                                color: 'red',
                                                outline: 'none',
                                                transform: 'none',
                                                boxShadow: 'none',
                                            }}
                                        />
                                    </div>
                                    {/* </Tippy> */}
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <Dialog
                visible={state}
                onHide={() => setState(false)}
                header="Xoá tin nhắn"
                headerClassName="font-weiht-bold text-danger"
            >
                <h4 className="dialog-msg" style={{ marginBottom: 10 + 'px' }}>
                    Bạn có chắc chắn muốn xóa tin nhắn này?
                </h4>
                <div className="messge-group-base">
                    <div className="messge-group">
                        <div className="message-base">
                            <div
                                className="dialog-chat-message"
                                style={{
                                    color: 'rgba(0, 0, 0, 0.827)',
                                    whiteSpace: 'nowrap',
                                    fontSize: 16 + 'px',
                                    overflow: 'hidden',
                                    textOverflow: 'ellipsis',
                                    wordWrap: 'break-word',
                                    width: 300 + 'px',
                                    margin: 0,
                                    maxHeight: 100 + 'px',
                                    wordBreak: 'keep-all',
                                    // borderTop: 'solid black 1px',
                                    // borderBottom: 'solid black 1px',
                                    marginBottom: '5px',
                                    background: '#F3F9FF',
                                    borderRadius: '10px',
                                    padding: '10px',
                                }}
                            >
                                {currentChat.id !== 2 && <div className="chat-message">{message.content}</div>}
                                {currentChat.id === 2 && (
                                    <div className="chat-message">
                                        {message.content.split('|')[0]}
                                        <br />
                                        <span>{message.content.split('|')[1]}</span>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="dialog-option" style={{ float: 'right', marginRight: 14 + 'px' }}>
                    <Button
                        label="Hủy bỏ"
                        className="p-button p-component p-button-link"
                        style={{ fontSize: 14 + 'px' }}
                        onClick={cancel}
                    />
                    <Button
                        label="Xóa"
                        className="p-button p-component p-button-raised p-button-danger"
                        style={{ fontSize: 14 + 'px', width: 80 + 'px' }}
                        onClick={deleteHandle}
                    />
                </div>
            </Dialog>
        </div>
    );
}

export default ChatMessage;
