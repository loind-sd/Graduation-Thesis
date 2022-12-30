import { OverlayPanel } from 'primereact/overlaypanel';
import React from 'react';
import { Badge } from 'primereact/badge';
import { UilSignOutAlt, UilUserPlus, UilEllipsisH, UilCommentAltMessage, UilBan } from '@iconscout/react-unicons';

function ContactItemKick({
    i,
    op,
    contact,
    currentSelected,
    changeCurrentChat,
    setIdAction,
    openAddFriend,
    showDelete,
    showKick,
}) {
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
                    data-pr-tooltip={contact.name}
                >
                    <span style={{ width: '40px', display: 'inline-flex' }}>
                        <UilCommentAltMessage />
                        {contact.haveNew === true && <Badge severity="danger" value=" "></Badge>}
                    </span>
                    <div className={`your-text ${contact.id === currentSelected.id ? 'txt-selected' : ''}`}>
                        {contact.name}
                    </div>
                </div>
                <div>
                    <button
                        className={`btnMore ${contact.id === currentSelected.id ? 'btnMore-selected' : ''}`}
                        onClick={(e) => {
                            op.current.toggle(e);
                            setIdAction(contact);
                        }}
                        aria-haspopup
                        aria-controls="overlay_panel_2"
                    >
                        <UilEllipsisH />
                    </button>
                    <OverlayPanel ref={op} id="overlay_panel_2" style={{ width: '150px', zIndex: '9999' }}>
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
                        <div className="btnAdd mb-1">
                            <button
                                className="d-flex "
                                style={{ background: 'transparent', alignItems: 'center' }}
                                onClick={showKick}
                            >
                                <UilBan />
                                <div className="ml-3">Xóa thành viên</div>
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

export default ContactItemKick;
