import React, { useEffect, useState, useRef } from 'react';
import { UilCommentAltShare, UilCommentDots, UilPlus } from '@iconscout/react-unicons';
import { classNames } from 'primereact/utils';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Tooltip } from 'primereact/tooltip';
import { ListBox } from 'primereact/listbox';
import { getFriendList } from '../../../../redux/thunks/friend-thunks';
import { useDispatch, useSelector } from 'react-redux';
import { Badge } from 'primereact/badge';
import './Contact.scss';
import { getFriend, getMember } from '../../../../redux/thunks/chat-thunks';
import ContactItemKick from '../ContactItemKick/ContactItemKick';

function Contacts({ contacts, changeChat, addNewcontact, outRoom, kickUsers, socket, addMemberToChat }) {
    const dispatch = useDispatch();
    const op = useRef(null);
    const [outRoomDialog, setOutRoomDialog] = useState(false);
    const [currentSelected, setCurrentSelected] = useState({
        id: 1,
        name: '#General',
        roomCode: 'general',
    });
    const [friendSelected, setFriendSelected] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [keywordAdd, setKeywordAdd] = useState('');
    const [info, setInfo] = useState(false);
    const [kick, setKick] = useState(false);
    const [idKick, setIdKick] = useState(-1);
    const [confirmKick, setConfirmKick] = useState(false);
    const [idAction, setIdAction] = useState({});
    const [dialog, setDialog] = useState(false);
    const [addDialog, setAddDialog] = useState(false);
    const [submitted, setSubmitted] = useState(false);
    const [newContact, setNewcontact] = useState({
        name: '',
        member: [],
    });
    const changeCurrentChat = (index, contact) => {
        setCurrentSelected(contact);
        changeChat(contact);
    };

    const outRoomCurrent = () => {
        outRoom(idAction.id, idAction.roomCode);
        setIdAction({});
        setOutRoomDialog(false);
        if (currentSelected.id === idAction.id) {
            setCurrentSelected({
                id: 1,
                name: '#General',
                roomCode: 'general',
            });
        }
    };

    const kickUser = () => {
        kickUsers(idAction.id, idAction.roomCode, idKick);
        setIdKick(-1);
        hideDeleteUserDialog();
        let _allMembers = allMember.filter((val) => val.id !== idKick);
        setAllMember(_allMembers);
    };

    // contacts = [
    //     {
    //         id: 1,
    //         name: 'a',
    //         roomCode: 'a',
    //     },
    //     {
    //         id: 2,
    //         name: 'a',
    //         roomCode: 'a',
    //     },
    //     {
    //         id: 3,
    //         name: 'aasssssssssssssssssssssssssssssssssssssss',
    //         roomCode: 'a',
    //     },
    //     {
    //         id: 4,
    //         name: 'aassssssssssssssssssssss',
    //         roomCode: 'a',
    //     },
    // ];

    // const [se] = useState({
    //     id: -1,
    // });

    useEffect(() => {
        dispatch(getFriendList());
    }, [dispatch]);
    let friendInit = useSelector((state) => state.friends.friend.item);
    let friendToAdd = useSelector((state) => state.chatsData.friend);
    let memberInRoom = useSelector((state) => state.chatsData.member);

    const [friend, setFriend] = useState([]);
    const [addMember, setAddMember] = useState([]);
    const [allMember, setAllMember] = useState([]);
    const [addMemberSelected, setAddMemberSelected] = useState([]);

    useEffect(() => {
        if (friendInit) {
            setFriend(friendInit);
        }
    }, [friendInit]);

    useEffect(() => {
        if (friendToAdd) {
            setAddMember(friendToAdd);
        }
    }, [friendToAdd]);

    useEffect(() => {
        if (memberInRoom) {
            setAllMember(memberInRoom);
        }
    }, [memberInRoom]);

    let worldContact = [],
        groupContact = [];
    for (let index = 0; index < contacts.length; index++) {
        const element = contacts[index];
        if (element.id === 1 || element.id === 2) {
            worldContact.push(element);
        } else {
            groupContact.push(element);
        }
    }

    const filterTemplate = (options) => {
        return (
            <div className="flex flex-column gap-2">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText
                        id="add-friend"
                        value={keyword}
                        onChange={(e) => onSearchChange(e)}
                        placeholder="Nhập tên bạn bè"
                    />
                </span>
            </div>
        );
    };

    const filterTemplateToAdd = (options) => {
        return (
            <div className="flex flex-column gap-2">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText
                        id="add-friend"
                        value={keywordAdd}
                        onChange={(e) => onSearchChangeToAdd(e)}
                        placeholder="Nhập tên bạn bè"
                    />
                </span>
            </div>
        );
    };

    const addSelectedFriend = (option) => {
        let _selected = [...friendSelected];
        let _friend = friend.filter((val) => val.id !== option.id);

        if (findIndexById(option.id) === -1) {
            _selected.push(option);
        }

        setFriendSelected(_selected);
        setFriend(_friend);
    };

    const addSelectedFriendToAdd = (option) => {
        let _selected = [...addMemberSelected];
        let _friend = addMember.filter((val) => val.id !== option.id);

        if (findIndexByIdtoAdd(option.id) === -1) {
            _selected.push(option);
        }

        setAddMemberSelected(_selected);
        setAddMember(_friend);
    };

    const removeSelectedFriend = (option) => {
        let _friend = [...friend];
        let _selected = friendSelected.filter((val) => val.id !== option.id);

        _friend.push(option);
        _friend.sort((a, b) => (a.id > b.id ? 1 : -1));

        setFriendSelected(_selected);
        setFriend(_friend);
    };

    const removeSelectedFriendToAdd = (option) => {
        let _addMember = [...addMember];
        let _selected = addMemberSelected.filter((val) => val.id !== option.id);

        _addMember.push(option);
        _addMember.sort((a, b) => (a.id > b.id ? 1 : -1));

        setAddMemberSelected(_selected);
        setAddMember(_addMember);
    };

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < friendSelected.length; i++) {
            if (friendSelected[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    const findIndexByIdtoAdd = (id) => {
        let index = -1;
        for (let i = 0; i < addMemberSelected.length; i++) {
            if (addMemberSelected[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    const openAdd = () => {
        setDialog(true);
        setKeyword('');
        setNewcontact({
            name: '',
            member: [],
        });
        setFriendSelected([]);
        setFriend(friendInit);
    };

    const openAddFriend = () => {
        setAddDialog(true);
        setKeywordAdd('');
        // setIdAddFriend(contact);
        // setFriendSelected([]);
        // setFriend(friendInit);
        setAddMemberSelected([]);
        dispatch(
            getFriend({
                roomId: idAction.id,
            }),
        );
    };

    const hideDialog = () => {
        setDialog(false);
        setSubmitted(false);
    };

    const hideAddDialog = () => {
        setAddDialog(false);
    };

    const onInputChange = (e) => {
        const val = (e.target && e.target.value) || '';
        let _product = { ...newContact };
        _product.name = val;

        setNewcontact(_product);
    };

    const onSearchChange = (e) => {
        const val = (e.target && e.target.value) || '';
        setKeyword(val);
        let _friend = friendInit.filter((val) => !friendSelected.includes(val));
        if (val !== '') {
            let _friends = [];

            for (let index = 0; index < _friend.length; index++) {
                const element = _friend[index];
                if (element.name.includes(val)) {
                    _friends.push(element);
                }
            }
            setFriend(_friends);
        } else {
            setFriend(_friend);
        }
    };

    const onSearchChangeToAdd = (e) => {
        const val = (e.target && e.target.value) || '';
        setKeywordAdd(val);
        let _friend = friendToAdd.filter((val) => !addMemberSelected.includes(val));
        if (val !== '') {
            let _friends = [];

            for (let index = 0; index < _friend.length; index++) {
                const element = _friend[index];
                if (element.name.includes(val)) {
                    _friends.push(element);
                }
            }
            setAddMember(_friends);
        } else {
            setAddMember(_friend);
        }
    };

    const itemTemplate = (option) => {
        return (
            <div className="info-user">
                <ul className="info-user-list">
                    <li className="content-item">
                        <div className="item-image">
                            <img
                                src={
                                    option.picture === null
                                        ? require('./../../../../assets/image/user.png')
                                        : option.picture
                                }
                                onError={(e) =>
                                (e.target.src =
                                    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                }
                                alt="Hình ảnh"
                            />
                        </div>
                        <div className="item-content">
                            <span className="item-name">{option.name}</span>
                            <span className="item-nick-name">{option.nickname}</span>
                        </div>
                        <div>
                            <button className="btn btn-success" onClick={() => addSelectedFriend(option)}>
                                <i className="pi pi-plus"></i>
                            </button>
                        </div>
                    </li>
                </ul>
            </div>
        );
    };

    const itemTemplateToAdd = (option) => {
        return (
            <div className="info-user">
                <ul className="info-user-list">
                    <li className="content-item">
                        <div className="item-image">
                            <img
                                src={
                                    option.picture === null
                                        ? require('./../../../../assets/image/user.png')
                                        : option.picture
                                }
                                onError={(e) =>
                                (e.target.src =
                                    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                }
                                alt="Hình ảnh"
                            />
                        </div>
                        <div className="item-content">
                            <span className="item-name">{option.name}</span>
                            <span className="item-nick-name">{option.nickname}</span>
                        </div>
                        <div>
                            <button className="btn btn-success" onClick={() => addSelectedFriendToAdd(option)}>
                                <i className="pi pi-plus"></i>
                            </button>
                        </div>
                    </li>
                </ul>
            </div>
        );
    };

    const itemTemplateToKick = (option) => {
        return (
            <div
                className="info-user"
                onClick={() => {
                    showConfirmKick(option.id);
                }}
            >
                <ul className="info-user-list">
                    <li className="content-item">
                        <div className="item-image">
                            <img
                                src={
                                    option.picture === null
                                        ? require('./../../../../assets/image/user.png')
                                        : option.picture
                                }
                                onError={(e) =>
                                (e.target.src =
                                    'https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=2000')
                                }
                                alt="Hình ảnh"
                            />
                        </div>
                        <div className="item-content">
                            <span className="item-name">{option.name}</span>
                            <span className="item-nick-name">{option.nickname}</span>
                        </div>
                    </li>
                </ul>
            </div>
        );
    };

    const showDelete = () => {
        setOutRoomDialog(true);
        // setIdDelete(roomId);
    };

    const showKick = () => {
        if (idAction.master === false) {
            showInfoDialog();
        } else {
            showKickDialog();
        }
    };

    const hideDeleteAccountsDialog = () => {
        setOutRoomDialog(false);
        op.current.hide();
    };

    const hideDeleteUserDialog = () => {
        setConfirmKick(false);
    };

    const showConfirmKick = (id) => {
        setIdKick(id);
        setConfirmKick(true);
    };

    const hideInfoDialog = () => {
        setInfo(false);
    };

    const showInfoDialog = () => {
        setInfo(true);
    };

    const showKickDialog = () => {
        setKick(true);
        dispatch(
            getMember({
                roomId: idAction.id,
            }),
        );
    };

    const hideKickDialog = () => {
        setKick(false);
    };

    const deleteAccountsDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteAccountsDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={outRoomCurrent} />
        </React.Fragment>
    );

    const deleteUserDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteUserDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={kickUser} />
        </React.Fragment>
    );

    const infoDialogFooter = (
        <React.Fragment>
            <Button label="OK" icon="pi pi-check" className="p-button-text" onClick={hideInfoDialog} />
        </React.Fragment>
    );

    const kickDialogFooter = (
        <React.Fragment>
            <Button label="OK" icon="pi pi-check" className="p-button-text" onClick={hideKickDialog} />
        </React.Fragment>
    );

    const addContact = () => {
        setSubmitted(true);
        if (newContact.name && friendSelected.length > 0) {
            let _newcontact = { ...newContact };
            let ids = [];

            for (let index = 0; index < friendSelected.length; index++) {
                const element = friendSelected[index];
                ids.push(element.id);
            }
            _newcontact.member = ids;
            setNewcontact(_newcontact);

            hideDialog();
            addNewcontact(_newcontact);
        }
    };

    const addFriend = () => {
        if (addMemberSelected.length > 0) {
            let ids = [];

            for (let index = 0; index < addMemberSelected.length; index++) {
                const element = addMemberSelected[index];
                ids.push(element.id);
            }
            hideAddDialog();
            addMemberToChat(ids, idAction);
            setIdAction({});
        }
    };

    const productDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Tạo Phòng" icon="pi pi-check" className="p-button-text" onClick={addContact} />
        </React.Fragment>
    );

    const addDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideAddDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={addFriend} />
        </React.Fragment>
    );

    return (
        <div className="contact-container">
            <Tooltip target=".contact-up" showDelay={1000} position="left" style={{}} />
            {/* <div className="brand">
                <img src={Logo} alt="logo" />
                <h3>LVLBH</h3>
            </div> */}
            <div className="contacts">
                {/* <Accordion multiple style={{ width: '92%', marginLeft: '10px' }}>
                    <AccordionTab
                        headerTemplate={<div className="channel-base">Kênh Chung</div>}
                        headerStyle={{ borderRadius: '0px' }}
                        style={{ borderRadius: '0px', marginBottom: '0px' }}
                        contentStyle={{ borderRadius: '0px', padding: '0px' }}
                    > */}
                <div className="channel-base">Kênh Chung</div>
                <div className="contacts-base ">
                    {worldContact.map((contact, index) => {
                        return (
                            <div
                                key={index}
                                className={`contact ${contact.id === currentSelected.id ? 'selected' : ''}`}
                                onClick={() => changeCurrentChat(index, contact)}
                            >
                                <div className="contact-detail">
                                    {contact.id === 1 && (
                                        <div className="">
                                            <div className="contact-up" data-pr-tooltip={contact.name}>
                                                <span>
                                                    <UilCommentDots />
                                                    {contact.haveNew === true && (
                                                        <Badge severity="danger" value=" "></Badge>
                                                    )}
                                                </span>
                                                <span className="text">{contact.name}</span>
                                            </div>
                                            <div className="description">
                                            Bạn cùng mọi người trong cộng đồng có thể nói chuyện ở đây
                                            </div>
                                        </div>
                                    )}
                                    {contact.id === 2 && (
                                        <div className="">
                                            <div className="contact-up" data-pr-tooltip={contact.name}>
                                                <UilCommentAltShare />
                                                <span className="text">{contact.name}</span>
                                                {contact.haveNew === true && (
                                                    <Badge severity="danger" value="o"></Badge>
                                                )}
                                            </div>
                                            <div className="description">
                                            Hãy tham gia vào 1 phòng bất kỳ để luyện tập kỹ năng mềm cùng mọi người
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>
                        );
                    })}
                </div>
                {/* </AccordionTab>
                    <AccordionTab
                        header="Kênh Rieng"
                        headerTemplate={
                            <div className="channel-base d-flex">
                                <div>Kênh Rieng</div>
                                <button className="btn btn-success" style={{ marginRight: '20px' }} onClick={openAdd}>
                                    <i className="pi pi-plus"></i>
                                </button>
                            </div>
                        }
                    > */}
                <div className="channel-base d-flex">
                    <div>Kênh Riêng</div>
                    <button className="btnPlus" onClick={openAdd}>
                        <UilPlus />
                    </button>
                </div>
                <div
                    className="contacts-base non-general-contacts"
                    style={{
                        overflowY: 'auto',
                        overflowX: 'hidden',
                    }}
                >
                    {groupContact.map((contact, index) => {
                        let i = index + worldContact.length;
                        return (
                            <div key={i}>
                                <ContactItemKick
                                    i={i}
                                    op={op}
                                    contact={contact}
                                    currentSelected={currentSelected}
                                    changeCurrentChat={changeCurrentChat}
                                    setIdAction={setIdAction}
                                    openAddFriend={openAddFriend}
                                    showDelete={showDelete}
                                    showKick={showKick}
                                ></ContactItemKick>
                                {/* ) : (
                                    <ContactItem
                                        i={i}
                                        op={op}
                                        contact={contact}
                                        currentSelected={currentSelected}
                                        changeCurrentChat={changeCurrentChat}
                                        setIdAction={setIdAction}
                                        openAddFriend={openAddFriend}
                                        showDelete={showDelete}
                                    ></ContactItem>
                                )} */}
                            </div>
                        );
                    })}
                </div>
                {/* </AccordionTab>
                </Accordion> */}
            </div>
            <Dialog
                visible={dialog}
                style={{ width: '450px' }}
                header="Tạo Phòng Chat"
                modal
                closeOnEscape
                className="p-fluid"
                footer={productDialogFooter}
                onHide={hideDialog}
            >
                <div className="field">
                    <label htmlFor="group-name">Tên phòng</label>
                    <span className="p-input-icon-left">
                        <i className="pi pi-hashtag" />
                        <InputText
                            id="group-name"
                            value={newContact.name}
                            onChange={(e) => onInputChange(e)}
                            maxLength={50}
                            required
                            autoFocus
                            placeholder="Nhập tên phòng của bạn"
                            className={classNames({ 'p-invalid': submitted && !newContact.name })}
                        />
                    </span>
                    {submitted && !newContact.name && <small className="p-error">Tên phòng không được để trống</small>}
                </div>
                <div className="field mt-5">
                    <label htmlFor="add-frined">Thêm bạn</label>
                    <div className="friend-select-base">
                        {friendSelected.map((friend, index) => {
                            return (
                                <div className="friend-select-item mb-2" key={index}>
                                    <div className="friend-select-name">{friend.name}</div>
                                    <button style={{ paddingTop: '2px' }}>
                                        <i
                                            className="pi pi-times-circle"
                                            onClick={() => removeSelectedFriend(friend)}
                                        ></i>
                                    </button>
                                </div>
                            );
                        })}
                    </div>
                    {submitted && friendSelected.length === 0 && (
                        <small className="p-error">Cần thêm ít nhất 1 người bạn để tạo nhóm</small>
                    )}

                    <ListBox
                        options={friend}
                        itemTemplate={itemTemplate}
                        filter
                        filterTemplate={filterTemplate}
                        listStyle={{ height: '240px', overflowY: 'auto' }}
                    />
                </div>
            </Dialog>

            <Dialog
                visible={addDialog}
                style={{ width: '450px' }}
                header="Thêm thành viên"
                modal
                closeOnEscape
                className="p-fluid"
                footer={addDialogFooter}
                onHide={hideAddDialog}
            >
                <div className="field mt-5">
                    {/* <label htmlFor="add-frined">Thêm bạn</label> */}
                    <div className="friend-select-base">
                        {addMemberSelected.map((friend, index) => {
                            return (
                                <div className="friend-select-item mb-2" key={index}>
                                    <div className="friend-select-name">{friend.name}</div>
                                    <button style={{ paddingTop: '2px' }}>
                                        <i
                                            className="pi pi-times-circle"
                                            onClick={() => removeSelectedFriendToAdd(friend)}
                                        ></i>
                                    </button>
                                </div>
                            );
                        })}
                    </div>

                    <ListBox
                        options={addMember}
                        itemTemplate={itemTemplateToAdd}
                        filter
                        filterTemplate={filterTemplateToAdd}
                        listStyle={{ height: '240px', overflowY: 'auto' }}
                    />
                </div>
            </Dialog>
            <Dialog
                visible={outRoomDialog}
                style={{ width: '450px', borderRadius: '0' }}
                header="Rời phòng?"
                dismissableMask
                modal
                footer={deleteAccountsDialogFooter}
                onHide={hideDeleteAccountsDialog}
                contentStyle={{ borderRadius: '0px' }}
            >
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    <span>Bạn có chắc chắn muốn rời khỏi phòng chat này không?</span>
                </div>
            </Dialog>

            <Dialog
                visible={confirmKick}
                style={{ width: '450px', borderRadius: '0' }}
                header="Xóa thành viên?"
                dismissableMask
                modal
                footer={deleteUserDialogFooter}
                onHide={hideDeleteUserDialog}
                contentStyle={{ borderRadius: '0px' }}
            >
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    <span>Bạn có chắc chắn muốn xóa thành viên này khỏi phòng chat không?</span>
                </div>
            </Dialog>

            <Dialog
                visible={info}
                style={{ width: '450px', borderRadius: '0' }}
                dismissableMask
                header="Thông báo"
                modal
                footer={infoDialogFooter}
                onHide={hideInfoDialog}
                contentStyle={{ borderRadius: '0px' }}
            >
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    <span>Chỉ có chủ phòng mới được thực hiện thao tác này</span>
                </div>
            </Dialog>

            <Dialog
                visible={kick}
                style={{ width: '450px', borderRadius: '0' }}
                modal
                header="Xóa thành viên"
                footer={kickDialogFooter}
                onHide={hideKickDialog}
                contentStyle={{ borderRadius: '0px' }}
            >
                <ListBox
                    options={allMember}
                    itemTemplate={itemTemplateToKick}
                    listStyle={{ height: '240px', overflowY: 'auto' }}
                />
            </Dialog>
        </div>
    );
}

export default Contacts;
