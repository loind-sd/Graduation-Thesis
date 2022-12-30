import React, { useState } from 'react';

import { UilMessage, UilSmile } from '@iconscout/react-unicons';
import EmojiPicker from 'emoji-picker-react';
import './ChatInput.scss';
import { useDispatch } from 'react-redux';

import { sendWorldChat } from './../../../../redux/thunks/chat-thunks';

function ChatInput({ currentChat, handleSendMsg }) {
    const [msg, setMsg] = useState('');
    const dispatch = useDispatch();
    const [showEmojiPicker, setShowEmojiPicker] = useState(false);
    const handleEmojiPickerhideShow = () => {
        setShowEmojiPicker(!showEmojiPicker);
    };

    const hidePicker = () => {
        setShowEmojiPicker(false);
    };

    // const showPicker = () => {
    //     setShowEmojiPicker(true);
    // };

    const onEmojiClick = (event, emojiObject) => {
        let message = msg;
        message += event.emoji;
        setMsg(message);
        hidePicker();
    };

    // const sendChat = (event) => {
    //     event.preventDefault();
    //     if (msg.length > 0) {
    //         handleSendMsg(msg);
    //         alert(msg);
    //         setMsg('');
    //     }
    // };

    const sendChat = () => {
        if (msg.trim().length > 0) {
            handleSendMsg(msg);
            setMsg('');
        }
    };

    const handleSubmit = (e) => {
        if (e.which === 13 && !e.shiftKey) {
            sendChat();
        }
    };

    const handleChange = (e) => {
        if (e.target.value === '\n') {
            return;
        }
        setMsg(e.target.value);
    };

    return (
        <div className="chat-babel">
            {currentChat.id !== 2 && (
                <div className="chat-input-container">
                    <div className="button-container">
                        <div className="emoji">
                            <UilSmile onClick={handleEmojiPickerhideShow} />
                            {showEmojiPicker && <EmojiPicker onEmojiClick={onEmojiClick} />}
                        </div>
                    </div>
                    <div className="input-container">
                        <textarea
                            placeholder="Aa"
                            onChange={(e) => handleChange(e)}
                            onKeyPress={handleSubmit}
                            autoFocus
                            value={msg}
                            rows="3"
                            className="submit_on_enter"
                        ></textarea>
                        <button onClick={sendChat} className="btnSubmit">
                            <UilMessage />
                        </button>
                    </div>
                </div>
            )}
            {currentChat.id === 2 && (
                <div className="chat-input-container">
                    <div className="button-container">
                        <div className="emoji">
                            <UilSmile />
                        </div>
                    </div>
                    <div className="input-container">
                        <textarea
                            placeholder="type your message here"
                            value="Bạn không được nhắn ở kênh này"
                            className="submit_on_enter"
                            readOnly
                        ></textarea>
                        <button type="submit">
                            <UilMessage />
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default ChatInput;
