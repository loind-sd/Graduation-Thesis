import { OverlayPanel } from 'primereact/overlaypanel';
import React from 'react';
import { UilSignOutAlt, UilUserPlus, UilEllipsisH, UilCommentAltMessage, UilBan } from '@iconscout/react-unicons';

function ContactItem({ i, op, contact, currentSelected, changeCurrentChat, setIdAction, openAddFriend, showDelete }) {
    return (
        <div
            key={i}
            className={`contact ${contact.id === currentSelected.id ? 'gr-selected' : ''}`}
            style={{ display: 'block', height: 'fit-content' }}
        >
            <div className="contact-detail d-flex justify-content-between">
                <div
                    className={`contact-up ml-5 ${contact.id === currentSelected.id ? 'icon-selected' : ''}`}
                    onClick={() => changeCurrentChat(i, contact)}
                >
                    <UilCommentAltMessage />
                    <div className={`your-text ${contact.id === currentSelected.id ? 'txt-selected' : ''}`}>
                        {contact.name}
                    </div>
                </div>
                <div>
                    {/* <Tippy
                    interactive
                    delay={[100, 100]}
                    offset={[-20, 0]}
                    placement="top"
                    render={renderMoreBtn}
                    hideOnClick="false"
                > */}
                    <button
                        className={`btnMore ${contact.id === currentSelected.id ? 'btnMore-selected' : ''}`}
                        onClick={(e) => {
                            op.current.toggle(e);
                            setIdAction(contact);
                        }}
                        aria-haspopup
                        aria-controls="overlay_panel"
                    >
                        <UilEllipsisH />
                    </button>
                    <OverlayPanel ref={op} id="overlay_panel" style={{ width: '150px', zIndex: '9999' }}>
                        <div className="btnAdd mb-1">
                            <button
                                className="d-flex "
                                style={{ background: 'transparent', alignItems: 'center' }}
                                onClick={openAddFriend}
                            >
                                <UilUserPlus />
                                <div className="ml-3">Thêm thành viên</div>
                            </button>
                        </div>
                        <div className="btnOut">
                            <button
                                className="d-flex "
                                style={{ background: 'transparent', alignItems: 'center' }}
                                onClick={showDelete}
                            >
                                <UilSignOutAlt />
                                <div className="ml-3">Rời khỏi phòng</div>
                            </button>
                        </div>
                    </OverlayPanel>
                    {/* </Tippy> */}
                </div>
            </div>
        </div>
    );
}

export default ContactItem;
