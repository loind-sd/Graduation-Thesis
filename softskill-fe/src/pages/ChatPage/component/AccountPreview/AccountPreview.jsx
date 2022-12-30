import React from 'react';
import imgDefault from './../../../../assets/image/user.png';
import './AccountPreview.scss';

function AccountPreview({ message, addFriend }) {
    const addFriendHandle = () => {
        addFriend(message.createdBy, message);
    };

    return (
        <div className="pre-wrapper">
            <div className="pre-header">
                <img
                    className="pre-avatar"
                    src={message.createdPicture === null ? imgDefault : message.createdPicture}
                    onError={(e) =>
                        (e.target.src =
                            'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                    }
                    alt="img"
                />
                <p className="pre-name">{message.createdName}</p>
            </div>
            <div className="pre-body">
                <p className="pre-nickname">
                    <strong>{message.createdNickName}</strong>
                </p>
                {message.status === 'nothing' && (
                    <button className="add-friend" onClick={addFriendHandle}>
                        Thêm bạn bè
                    </button>
                )}
            </div>
        </div>
    );
}

export default AccountPreview;
